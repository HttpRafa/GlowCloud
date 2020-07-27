package de.rafadev.glowcloud.wrapper.server;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 11:43
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.enums.ServerType;
import de.rafadev.glowcloud.lib.network.utils.CloudUtils;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.network.packet.out.PacketOutRegisterServer;
import de.rafadev.glowcloud.wrapper.network.packet.out.PacketOutUnRegisterServer;
import de.rafadev.glowcloud.wrapper.queue.CloudServerQueue;
import de.rafadev.glowcloud.wrapper.server.classes.CloudBukkitServer;
import de.rafadev.glowcloud.wrapper.server.classes.CloudProxyServer;
import de.rafadev.glowcloud.wrapper.server.classes.QueueCloudServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ServerManager {

    private List<CloudServer> servers = new CopyOnWriteArrayList<>();

    private CloudServerQueue serverQueue = new CloudServerQueue();
    private boolean blocked = false;

    private long maxMemory;
    private long usedMemory;

    public ServerManager() {
        maxMemory = 15000;
    }

    public void startQueue() {

        GlowCloudWrapper.getGlowCloud().getScheduler().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                if(GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection() != null && GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().isActive()) {
                    if(!blocked) {

                        QueueCloudServer cloudServer = serverQueue.get();

                        if (usedMemory < maxMemory) {
                            if (cloudServer != null) {
                                serverQueue.removeFromQueue(cloudServer);
                                startService(cloudServer);
                            }
                        } else {
                            GlowCloudWrapper.getGlowCloud().getLogger().warning("The wrapper is out of memory§8!");
                            GlowCloudWrapper.getGlowCloud().getLogger().warning("§c" + usedMemory + " §8< §c" + maxMemory);
                        }

                    } else {
                        blocked = false;
                    }
                }
            }
        }, 0, 1000);

    }

    public void block() {
        blocked = true;
    }

    public void startService(QueueCloudServer cloudServer) {

        GlowCloudWrapper.getGlowCloud().getLogger().info("Starting the server §e" + cloudServer.getServiceID() + "§8#§6" + cloudServer.getUUID().toString() + "§8...");

        if(cloudServer.getCloudServerGroup().getServerType() == ServerType.BUKKIT) {
            CloudBukkitServer cloudBukkitServer = new CloudBukkitServer(cloudServer.getServiceID(), cloudServer.getUUID(), cloudServer.getCloudServerGroup(), cloudServer.getTemplate());

            GlowCloudWrapper.getGlowCloud().getLogger().info("Start preparations for the server §e" + cloudServer.getServiceID() + "§8...");

            if(cloudBukkitServer.prepare()) {
                register(cloudBukkitServer);
                cloudBukkitServer.startProcessing();
            } else {
                GlowCloudWrapper.getGlowCloud().getLogger().error("Can`t prepare the server §4" + cloudBukkitServer.getServiceID() + "§8!");
                serverQueue.addToQueue(cloudServer);
                unRegister(cloudBukkitServer);
            }

        } else if(cloudServer.getCloudServerGroup().getServerType() == ServerType.BUNGEECORD) {
            CloudProxyServer cloudProxyServer = new CloudProxyServer(cloudServer.getServiceID(), cloudServer.getUUID(), cloudServer.getCloudServerGroup(), cloudServer.getTemplate());

            register(cloudProxyServer);

            cloudProxyServer.prepare();
        }

    }

    public void stopService(CloudServer cloudServer) {

        GlowCloudWrapper.getGlowCloud().getLogger().info("Stopping the server §e" + cloudServer.getServiceID() + "§8#§6" + cloudServer.getUUID().toString() + "§8...");

        unRegister(cloudServer);

        if(cloudServer instanceof CloudBukkitServer) {
            ((CloudBukkitServer) cloudServer).stopService();
        }

    }

    public void stopAllServices() {

        for (CloudServer item : servers) {

            if(item instanceof CloudBukkitServer) {

                CloudBukkitServer cloudBukkitServer = (CloudBukkitServer) item;

                cloudBukkitServer.kill();

            } else if(item instanceof CloudProxyServer) {

                CloudProxyServer cloudProxyServer = (CloudProxyServer) item;

                cloudProxyServer.kill();

            }

            CloudUtils.sleep(500);

        }


        for (CloudServer server : servers) {
            unRegister(server);

            CloudUtils.sleep(200);

        }

    }

    public void resetAllServers() {

        int onlineCount = servers.size();
        List<CloudServer> serverList = new ArrayList<>(servers);

        GlowCloudWrapper.getGlowCloud().getLogger().overrideLine(1, "Stopping all running server... §8[§e0§8/§e" + onlineCount + "§8]");

        for (CloudServer item : servers) {

            if(item instanceof CloudBukkitServer) {

                CloudBukkitServer cloudBukkitServer = (CloudBukkitServer) item;

                cloudBukkitServer.kill();

            } else if(item instanceof CloudProxyServer) {

                CloudProxyServer cloudProxyServer = (CloudProxyServer) item;

                cloudProxyServer.kill();

            }

            serverList.remove(item);

            GlowCloudWrapper.getGlowCloud().getLogger().overrideLine(1, "Stopping all running server... §8[§e" + (onlineCount - serverList.size()) + "§8/§e" + onlineCount + "§8]");

            CloudUtils.sleep(500);

        }

        GlowCloudWrapper.getGlowCloud().getLogger().nextLine();

        for (CloudServer cloudServer : servers) {
            unRegister(cloudServer);
        }


        GlowCloudWrapper.getGlowCloud().getLogger().info("The cloud has stopped §e" + onlineCount + " §7servers§8.");

    }

    public CloudServerQueue getServerQueue() {
        return serverQueue;
    }

    public void register(CloudServer cloudServer) {

        servers.add(cloudServer);

        GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getPacketManager().writePacket(GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getChannelConnection(), new PacketOutRegisterServer(cloudServer));

        usedMemory = usedMemory + cloudServer.getCloudServerGroup().getDynamicMemory();

        GlowCloudWrapper.getGlowCloud().getLogger().info("§7The server §e" + cloudServer.getServiceID() + " §7is now registered in §eGlow§6Cloud");

    }

    public void unRegister(CloudServer cloudServer) {

        servers.remove(cloudServer);

        if(GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection() != null && GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getChannelConnection() != null) {
            GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getPacketManager().writePacket(GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getChannelConnection(), new PacketOutUnRegisterServer(cloudServer));
        }

        usedMemory = usedMemory - cloudServer.getCloudServerGroup().getDynamicMemory();

        GlowCloudWrapper.getGlowCloud().getLogger().info("§7The server §e" + cloudServer.getServiceID() + " §7is not longer registered in §eGlow§6Cloud");

    }

    public CloudServer search(String identifier) {

        List<CloudServer> result = servers.stream().filter(item -> item.getServiceID().equals(identifier)).collect(Collectors.toList());

        if(result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }

    }

}
