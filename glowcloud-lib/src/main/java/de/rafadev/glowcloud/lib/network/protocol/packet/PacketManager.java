package de.rafadev.glowcloud.lib.network.protocol.packet;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 09:49
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.rafadev.glowcloud.lib.CloudValue;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.exception.NoConnectionFoundException;
import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.network.ChannelConnection;
import de.rafadev.glowcloud.lib.network.encryption.EncryptionHandler;
import de.rafadev.glowcloud.lib.network.protocol.packet.request.Result;
import de.rafadev.glowcloud.lib.network.utils.CloudUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class PacketManager {

    private HashMap<UUID, CloudValue<Result>> queryRequests = new HashMap<>();

    private List<PacketHandlerElement> handlers = new LinkedList<>();
    private CloudLogger cloudLogger;
    private final EncryptionHandler encryptionHandler = new EncryptionHandler();

    public PacketManager(CloudLogger cloudLogger) {
        this.cloudLogger = cloudLogger;
    }

    public void registerHandler(long id, PacketInHandler handler) {
        PacketHandlerElement packetHandlerElement = new PacketHandlerElement(id, handler);
        handlers.add(packetHandlerElement);
    }

    public Packet toPacket(ByteBuf byteBuf) {
        try {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);

            String packetString = new String(bytes, StandardCharsets.UTF_8);
            cloudLogger.debug(packetString);

            JsonReader jsonReader = new JsonReader(new StringReader(packetString));
            jsonReader.setLenient(true);

            JsonObject jsonObject = new JsonParser().parse(jsonReader).getAsJsonObject();

            return new Packet(jsonObject.get("id").getAsLong(), UUID.fromString(jsonObject.get("uuid").getAsString()), new Document(jsonObject.get("document").getAsJsonObject()));
        } catch (Exception exception) {
            if(cloudLogger != null) cloudLogger.handleException(exception);
        }

        return new Packet(PacketRC.MAIN - 99, UUID.randomUUID(), new Document());
    }

    public void handle(ByteBuf byteBuf, PacketSender packetSender) {

        try {
            Packet packet = toPacket(byteBuf);

            if(cloudLogger != null) {
                if(packetSender.getChannel() != null) {
                    cloudLogger.debug("§bConnection§8[§b" + packetSender.getChannel().remoteAddress().toString() + "§8] §7<-- §bPacket§8[§b" + packet.getId() + "§8/§b" + packet.getUuid().toString() + "§8]");
                }
            }

            if(!queryRequests.containsKey(packet.getUuid())) {
                List<PacketHandlerElement> elements = handlers.stream().filter(item -> item.getId() == packet.getId()).collect(Collectors.toList());

                for (PacketHandlerElement element : elements) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            element.getHandler().handle(packet.getDocument(), packetSender);
                        }
                    }, "PacketHandler").start();
                }
            } else {
                queryRequests.get(packet.getUuid()).override(new Result(packet.getUuid(), -1, packet.getDocument()));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Result writeRequest(ChannelConnection channelConnection, Packet packet) {
        if(channelConnection != null && channelConnection.getChannel() != null) {

            /*
                Creating a request
            */
            UUID uniqueId = UUID.randomUUID();
            packet.setUuid(uniqueId);

            CloudValue<Result> value = new CloudValue<>(null);
            queryRequests.put(uniqueId, value);

            CloudUtils.inNewThread(new Runnable() {
                @Override
                public void run() {
                    writePacket(channelConnection, packet);
                }
            });

            int time=0;
            while(queryRequests.get(uniqueId).get() == null && time < 4000) {
                time++;

                /*
                    Sleep
                 */
                CloudUtils.sleep(0, 1000 * 100 * 50);
            }

            if(time > 4000) {
                queryRequests.get(uniqueId).override(new Result(uniqueId, time, new Document()));
            } else {
                Result result = queryRequests.get(uniqueId).get();
                result.setTime(time);
                queryRequests.get(uniqueId).override(result);
            }

            CloudValue<Result> resultSet = queryRequests.get(uniqueId);
            queryRequests.remove(uniqueId);
            return resultSet.get();

        } else {
            if(cloudLogger != null) cloudLogger.handleException(new NoConnectionFoundException());
        }

        return null;

    }


    public void writePacket(ChannelConnection channelConnection, Packet packet) {

        if(channelConnection != null && channelConnection.getChannel() != null) {

            String packetString = new String(packet.toBytes(), StandardCharsets.UTF_8);
            byte[] bytes = encryptionHandler.encode(packetString).getBytes(StandardCharsets.UTF_8);

            ByteBuf byteBuf = Unpooled.buffer(bytes.length);
            byteBuf.writeBytes(bytes);
            channelConnection.getChannel().writeAndFlush(byteBuf);

            if (cloudLogger != null) cloudLogger.debug("§bPacket§8[§b" + packet.getId() + "§8/§b" + packet.getUuid() + "§8] §7--> §bConnection§8[§b" + channelConnection.getChannel().remoteAddress().toString() + "§8]");

        } else {

            if(cloudLogger != null) cloudLogger.handleException(new NoConnectionFoundException());

        }

    }

}
