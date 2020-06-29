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
import de.rafadev.glowcloud.lib.network.utils.NetworkUtils;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.network.packet.out.PacketOutRegisterServer;
import de.rafadev.glowcloud.wrapper.network.packet.out.PacketOutUnRegisterServer;
import de.rafadev.glowcloud.wrapper.queue.CloudServerQueue;
import de.rafadev.glowcloud.wrapper.server.classes.CloudBukkitServer;
import de.rafadev.glowcloud.wrapper.server.classes.CloudProxyServer;
import de.rafadev.glowcloud.wrapper.server.classes.QueueCloudServer;

import java.util.LinkedList;
import java.util.List;

public class ServerManager {

    private List<CloudServer> servers = new LinkedList<>();

    private CloudServerQueue serverQueue = new CloudServerQueue();

    private long maxMemory;

    public ServerManager() {
        maxMemory = 15000;
    }

    public void startQueue() {

        GlowCloudWrapper.getGlowCloud().getScheduler().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                QueueCloudServer cloudServer = serverQueue.get();

                long used = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000;

                if(used < maxMemory) {
                    if(cloudServer != null) {
                        serverQueue.removeFromQueue(cloudServer);
                        startService(cloudServer);
                    }
                } else {
                    GlowCloudWrapper.getGlowCloud().getLogger().warning("The wrapper is out of memory§8!");
                    GlowCloudWrapper.getGlowCloud().getLogger().warning("§c" + used + " §8< §c" +  maxMemory);
                }
            }
        }, 0, 1500);

    }

    public void startService(QueueCloudServer cloudServer) {

        GlowCloudWrapper.getGlowCloud().getLogger().info("Starting the server §e" + cloudServer.getServiceID() + "§8#§6" + cloudServer.getUUID().toString() + "§8...");

        if(cloudServer.getCloudServerGroup().getServerType() == ServerType.BUKKIT) {
            CloudBukkitServer cloudBukkitServer = new CloudBukkitServer(cloudServer.getServiceID(), cloudServer.getUUID(), cloudServer.getCloudServerGroup());

            register(cloudBukkitServer);

            GlowCloudWrapper.getGlowCloud().getLogger().info("Start preparations for the server §e" + cloudServer.getServiceID() + "§8...");
            if(cloudBukkitServer.prepare()) {
                GlowCloudWrapper.getGlowCloud().getLogger().info("");
            } else {
                GlowCloudWrapper.getGlowCloud().getLogger().error("Can`t prepare the server §4" + cloudBukkitServer.getServiceID() + "§8!");
                serverQueue.addToQueue(cloudServer);
                unRegister(cloudBukkitServer);
            }

        } else if(cloudServer.getCloudServerGroup().getServerType() == ServerType.BUNGEECORD) {
            CloudProxyServer cloudProxyServer = new CloudProxyServer(cloudServer.getServiceID(), cloudServer.getUUID(), cloudServer.getCloudServerGroup());

            register(cloudProxyServer);

            cloudProxyServer.prepare();
        }

    }

    public void stopService(CloudServer cloudServer) {

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

            NetworkUtils.sleep(500);

        }


        for (CloudServer server : servers) {
            unRegister(server);

            NetworkUtils.sleep(200);

        }

    }

    public void resetAllServers() {

        int onlineCount = servers.size();

        GlowCloudWrapper.getGlowCloud().getLogger().overrideLine(1, "Stopping all running server... §8[§e0§8/§e" + onlineCount + "§8]");

        for (CloudServer item : servers) {

            if(item instanceof CloudBukkitServer) {

                CloudBukkitServer cloudBukkitServer = (CloudBukkitServer) item;

                cloudBukkitServer.kill();

            } else if(item instanceof CloudProxyServer) {

                CloudProxyServer cloudProxyServer = (CloudProxyServer) item;

                cloudProxyServer.kill();

            }

            NetworkUtils.sleep(500);

            GlowCloudWrapper.getGlowCloud().getLogger().overrideLine(1, "Stopping all running server... §8[§e" + (onlineCount - servers.size()) + "§8/§e" + onlineCount + "§8]");

        }

        GlowCloudWrapper.getGlowCloud().getLogger().nextLine();
        GlowCloudWrapper.getGlowCloud().getLogger().info("The cloud has stopped §e" + onlineCount + " §7servers§8.");

        GlowCloudWrapper.getGlowCloud().getLogger().info("All stopped servers are unregistered§8.");

        for (CloudServer server : servers) {
            unRegister(server);

            NetworkUtils.sleep(200);

        }

    }

    public CloudServerQueue getServerQueue() {
        return serverQueue;
    }

    public void register(CloudServer cloudServer) {

        servers.add(cloudServer);

        GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getPacketManager().writePacket(GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getChannelConnection(), new PacketOutRegisterServer(cloudServer));

        GlowCloudWrapper.getGlowCloud().getLogger().info("§7The server §e" + cloudServer.getServiceID() + " §7is now registered in §eGlow§6Cloud");

    }

    public void unRegister(CloudServer cloudServer) {

        servers.remove(cloudServer);

        GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getPacketManager().writePacket(GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getChannelConnection(), new PacketOutUnRegisterServer(cloudServer));

        GlowCloudWrapper.getGlowCloud().getLogger().info("§7The server §e" + cloudServer.getServiceID() + " §7is no longer registered in §eGlow§6Cloud");

    }

}
