package de.rafadev.glowcloud.master.wrapper;

//------------------------------
//
// This class was developed by Rafael K.
// On 27.06.2020 at 22:11
// In the project GlowCloud
//
//------------------------------

import com.google.gson.*;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.file.CloudReader;
import de.rafadev.glowcloud.lib.network.ChannelConnection;
import de.rafadev.glowcloud.master.event.events.CloudWrapperConnectEvent;
import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.network.packet.handler.DefaultHandler;
import de.rafadev.glowcloud.master.network.packet.out.PacketOutShutdownWrapper;
import de.rafadev.glowcloud.master.server.seletor.CloudWrapperSelector;
import de.rafadev.glowcloud.master.wrapper.classes.CloudWrapper;
import de.rafadev.glowcloud.master.wrapper.classes.ConnectedCloudWrapper;
import de.rafadev.glowcloud.master.wrapper.classes.OfflineCloudWrapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class WrapperManager {

    private List<CloudWrapper> wrappers = new LinkedList<>();

    private File settingsFile = new File("settings.json");

    public void loadWrappers() {

        GlowCloud.getGlowCloud().getLogger().info("Loading all Wrappers...");

        CloudReader cloudReader = new CloudReader(settingsFile);

        try {
            JsonObject jsonObject = new JsonParser().parse(cloudReader.read()).getAsJsonObject();

            JsonArray wrappersArray = jsonObject.get("wrappers").getAsJsonArray();

            if(wrappersArray.size() > 0) {

                for (JsonElement element : wrappersArray) {
                    JsonObject wrapped = element.getAsJsonObject();

                    OfflineCloudWrapper cloudWrapper = new OfflineCloudWrapper(wrapped.get("id").getAsString(), wrapped.get("host").getAsString(), wrapped.get("heap").getAsInt());
                    inject(cloudWrapper);

                }

            } else {
                GlowCloud.getGlowCloud().getLogger().warning("The cloud could not load wrappers§8.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void registerWrapper(String identifier, String host, int heap) {

        OfflineCloudWrapper cloudWrapper = new OfflineCloudWrapper(identifier, host, heap);


        try {
            Document document = Document.load(settingsFile);

            JsonArray wrappersArray = document.get("wrappers").getAsJsonArray();

            JsonObject wrappersJson = new JsonObject();
            wrappersJson.addProperty("id", cloudWrapper.getId());
            wrappersJson.addProperty("host", cloudWrapper.getHost());
            wrappersJson.addProperty("heap", cloudWrapper.getHeap());

            wrappersArray.add(wrappersJson);

            document.append("wrappers", wrappersArray);

            document.save(settingsFile);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        inject(cloudWrapper);

    }

    public boolean connectWrapper(String identifier, ChannelConnection channelConnection) {

        CloudWrapper cloudWrapper = search(identifier);

        if(cloudWrapper instanceof OfflineCloudWrapper) {

            CloudWrapperConnectEvent cloudWrapperConnectEvent = new CloudWrapperConnectEvent((OfflineCloudWrapper) cloudWrapper, identifier, channelConnection);

            GlowCloud.getGlowCloud().getModuleManager().getEventManager().callEvent(cloudWrapperConnectEvent);

            if(!cloudWrapperConnectEvent.isCancelled()) {

                ConnectedCloudWrapper connectedCloudWrapper = new ConnectedCloudWrapper(cloudWrapper.getId(), cloudWrapper.getHost(), cloudWrapper.getHeap(), channelConnection);
                connectedCloudWrapper.setChannel(channelConnection.getChannel());

                wrappers.remove(cloudWrapper);
                wrappers.add(connectedCloudWrapper);

                channelConnection.getChannel().pipeline().addLast(new DefaultHandler());

                return true;
            } else {
                channelConnection.close();
                return false;
            }
        } else {
            GlowCloud.getGlowCloud().getLogger().error("The wrapper with the id §8\"§4" + cloudWrapper.getId() + "§8@§c" + ((ConnectedCloudWrapper) cloudWrapper).getChannelConnection().getChannel().remoteAddress().toString() + "§8\" §cis already connected to the master§8!");
        }

        return false;

    }

    public boolean disconnectWrapper(String identifier) {

        CloudWrapper cloudWrapper = search(identifier);

        if(cloudWrapper instanceof ConnectedCloudWrapper) {

            OfflineCloudWrapper offlineCloudWrapper = new OfflineCloudWrapper(cloudWrapper.getId(), cloudWrapper.getHost(), cloudWrapper.getHeap());

            wrappers.remove(cloudWrapper);
            wrappers.add(offlineCloudWrapper);

            return true;
        } else {
            return false;
        }

    }

    public void handleDisconnect(OfflineCloudWrapper cloudWrapper) {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<CloudServer> servers = GlowCloud.getGlowCloud().getServerManager().search(new CloudWrapperSelector(cloudWrapper));
        for (CloudServer server : servers) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GlowCloud.getGlowCloud().getServerManager().unRegisterServer(server);
        }

    }

    public void handleConnection(ConnectedCloudWrapper cloudWrapper) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<CloudServerGroup> wrapperGroups = GlowCloud.getGlowCloud().getGroupManager().getGroups().stream().filter(item -> item.getWrapperID().equals(cloudWrapper.getId())).collect(Collectors.toList());

                GlowCloud.getGlowCloud().getLogger().debug("Starting all server for the wrapper §b" + cloudWrapper.getId() + "§8.");
                for (CloudServerGroup group : wrapperGroups) {
                    GlowCloud.getGlowCloud().getServerManager().checkGroup(group);
                }
            }
        }, "checkGroups").start();

    }

    public void requestShutdown(ConnectedCloudWrapper connectedCloudWrapper) {

        GlowCloud.getGlowCloud().getLogger().debug("Stopping the wrapper with the id " + connectedCloudWrapper.getId() + ".");
        GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getPacketManager().writePacket(connectedCloudWrapper.getAsChannelConnection(), new PacketOutShutdownWrapper(connectedCloudWrapper));

    }

    public void inject(CloudWrapper wrapper) {

        GlowCloud.getGlowCloud().getLogger().info("Loading Wrapper §e" + wrapper.getId() + "§8@§6" + wrapper.getHost() + " §7with §e" + wrapper.getHeap() + " §7MB");
        wrappers.add(wrapper);

    }

    public CloudWrapper search(String identifier) {

        List<CloudWrapper> result = wrappers.stream().filter(item -> item.getId().equals(identifier)).collect(Collectors.toList());

        if(result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public boolean isRegistered(String identifier) {
        return wrappers.stream().anyMatch(item -> item.getId().equals(identifier));
    }

    public List<CloudWrapper> getWrappers() {
        return wrappers;
    }
}
