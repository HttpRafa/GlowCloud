package de.rafadev.glowcloud.lib.network.protocol.packet;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 09:49
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.network.ChannelConnection;
import de.rafadev.glowcloud.lib.network.encryption.EncryptionHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PacketManager {

    private List<PacketHandlerElement> handlers = new LinkedList<>();

    private final EncryptionHandler encryptionHandler = new EncryptionHandler();

    public void registerHandler(long id, PacketInHandler handler) {
        PacketHandlerElement packetHandlerElement = new PacketHandlerElement(id, handler);
        handlers.add(packetHandlerElement);
    }

    public Packet toPacket(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        String packetString = new String(bytes, StandardCharsets.UTF_8);
        JsonObject jsonObject = new JsonParser().parse(packetString).getAsJsonObject();

        return new Packet(jsonObject.get("id").getAsLong(), UUID.fromString(jsonObject.get("uuid").getAsString()), new Document(jsonObject.get("document").getAsJsonObject()));
    }

    public void handle(ByteBuf byteBuf, PacketSender packetSender) {

        try {
            Packet packet = toPacket(byteBuf);

            List<PacketHandlerElement> elements = handlers.stream().filter(item -> item.getId() == packet.id).collect(Collectors.toList());

            for (PacketHandlerElement element : elements) {
                element.getHandler().handle(packet.document, packetSender);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void writePacket(ChannelConnection channelConnection, Packet packet) {

        ByteBuf byteBuf = Unpooled.buffer();
        byte[] bytes = encryptionHandler.encode(new String(packet.toBytes(), StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
        byteBuf.writeBytes(bytes);
        channelConnection.getChannel().writeAndFlush(byteBuf);

    }

}
