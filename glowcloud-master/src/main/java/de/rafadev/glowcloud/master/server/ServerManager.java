package de.rafadev.glowcloud.master.server;

//------------------------------
//
// This class was developed by Rafael K.
// On 28.06.2020 at 19:38
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.selector.Selector;
import de.rafadev.glowcloud.master.event.events.CloudServerAddToQueueEvent;
import de.rafadev.glowcloud.master.event.events.CloudServerStartEvent;
import de.rafadev.glowcloud.master.event.events.CloudServerStopEvent;
import de.rafadev.glowcloud.master.group.bukkit.CloudBukkitGroup;
import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.master.group.proxy.CloudProxyGroup;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.network.packet.out.PacketOutStartCloudServer;
import de.rafadev.glowcloud.master.network.packet.out.PacketOutStopCloudServer;
import de.rafadev.glowcloud.master.server.classes.OnlineCloudServer;
import de.rafadev.glowcloud.master.server.classes.QueueCloudServer;
import de.rafadev.glowcloud.master.server.seletor.CloudServerGroupSelector;
import de.rafadev.glowcloud.master.server.seletor.CloudServerSelector;
import de.rafadev.glowcloud.lib.classes.selector.selectors.CloudStringSelector;
import de.rafadev.glowcloud.master.server.seletor.CloudWrapperSelector;
import de.rafadev.glowcloud.master.wrapper.classes.ConnectedCloudWrapper;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServerManager {

    private List<CloudServer> servers = new LinkedList<>();

    public ServerManager() {



    }

    public void registerServer(CloudServer cloudServer) {

        CloudServerStartEvent event = new CloudServerStartEvent(cloudServer, GlowCloud.getGlowCloud().getWrapperManager().search(cloudServer.getCloudServerGroup().getWrapperID()));

        GlowCloud.getGlowCloud().getModuleManager().getEventManager().callEvent(event);

        if(!event.isCancelled()) {

            CloudServer queueCloudServer = get(cloudServer.getServiceID());
            servers.remove(queueCloudServer);

            OnlineCloudServer onlineCloudServer = new OnlineCloudServer(cloudServer.getServiceID(), cloudServer.getUUID(), cloudServer.getCloudServerGroup(), queueCloudServer.getTemplate());
            servers.add(onlineCloudServer);

            GlowCloud.getGlowCloud().getLogger().info("The server §8[§e" + cloudServer.getServiceID() + "§8@§6" + cloudServer.getCloudServerGroup().getWrapperID() + "§8/§e" + cloudServer.getPort() + "§8] §7is registered §7on §eGlow§6Cloud");

        }

    }

    public void unRegisterServer(CloudServer cloudServer) {

        GlowCloud.getGlowCloud().getModuleManager().getEventManager().callEvent(new CloudServerStopEvent(cloudServer));

        List<CloudServer> server = servers.stream().filter(item -> item.getServiceID().equals(cloudServer.getServiceID())).collect(Collectors.toList());
        servers.removeAll(server);

        GlowCloud.getGlowCloud().getLogger().info("The server §8[§e" + cloudServer.getServiceID() + "§8] §7is unregistered §7on §eGlow§6Cloud");

    }

    public List<CloudServer> search(Selector selector) {

        if(selector instanceof CloudWrapperSelector) {

            return servers.stream().filter(item -> item.getCloudServerGroup().getWrapperID().equals(((CloudWrapperSelector) selector).getSelected().getId())).collect(Collectors.toList());

        } else if(selector instanceof CloudServerGroupSelector) {

            return servers.stream().filter(item -> item.getCloudServerGroup().getName().equals(((CloudServerGroupSelector) selector).getSelected().getName())).collect(Collectors.toList());

        } else {

            return new LinkedList<>();

        }

    }

    public void checkGroup(CloudServerGroup cloudServerGroup) {

        GlowCloud.getGlowCloud().getLogger().debug("§7Checking the group §b" + cloudServerGroup.getName() + "§8...");

        if(search(new CloudServerGroupSelector(cloudServerGroup)).size() < cloudServerGroup.getMinServerCount()) {

            startListedServer(cloudServerGroup, cloudServerGroup.getMinServerCount() - search(new CloudServerGroupSelector(cloudServerGroup)).size());

        }

    }

    public void startListedServer(CloudServerGroup cloudServerGroup, int amount) {

        GlowCloud.getGlowCloud().getLogger().debug("Starting §b" + amount + " §7servers for the group §b" + cloudServerGroup.getName() + "§8.");

        String spliter = "-";

        int min = search(new CloudServerGroupSelector(cloudServerGroup)).size() + amount;
        int count = 0;

        for (int i = 1; count < min; i++) {

            String name = cloudServerGroup.getName() + spliter + i;

            if (get(name) != null) {
                count++;
            } else {
                startServer(name, UUID.randomUUID(), cloudServerGroup);
                count++;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void startServer(String serviceID, UUID uuid, CloudServerGroup cloudServerGroup) {

        QueueCloudServer cloudServer = new QueueCloudServer(serviceID, uuid, cloudServerGroup.toSimple(), cloudServerGroup instanceof CloudBukkitGroup ? ((CloudBukkitGroup) cloudServerGroup).getCloudTemplateSystem().getSelectedTemplate() : ((CloudProxyGroup) cloudServerGroup).getCloudTemplateSystem().getSelectedTemplate());

        CloudServerAddToQueueEvent event = new CloudServerAddToQueueEvent(cloudServer);

        GlowCloud.getGlowCloud().getModuleManager().getEventManager().callEvent(event);

        if(!event.isCancelled()) {

            ConnectedCloudWrapper cloudWrapper = (ConnectedCloudWrapper) GlowCloud.getGlowCloud().getWrapperManager().search(cloudServerGroup.getWrapperID());

            GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getPacketManager().writePacket(cloudWrapper.getChannelConnection(), new PacketOutStartCloudServer(cloudServer));
            GlowCloud.getGlowCloud().getLogger().info("The server §e" + cloudServer.getServiceID() + " §7is now in the queue of the wrapper §e" + cloudWrapper.getId() + "§8.");

            servers.add(cloudServer);

            GlowCloud.getGlowCloud().getLogger().debug(cloudServer.getTemplate().getName());

        }

    }

    public void stopServer(Selector selector) {

        if (selector instanceof CloudServerSelector) {

            CloudServerSelector cloudServerSelector = (CloudServerSelector) selector;

            if (GlowCloud.getGlowCloud().getWrapperManager().search(cloudServerSelector.getSelected().getCloudServerGroup().getWrapperID()) instanceof ConnectedCloudWrapper) {

                ConnectedCloudWrapper cloudWrapper = (ConnectedCloudWrapper) GlowCloud.getGlowCloud().getWrapperManager().search(cloudServerSelector.getSelected().getCloudServerGroup().getWrapperID());

                GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getPacketManager().writePacket(cloudWrapper.getChannelConnection(), new PacketOutStopCloudServer(cloudServerSelector.getSelected()));

            }

            GlowCloud.getGlowCloud().getScheduler().runWaitTask(new Runnable() {
                @Override
                public void run() {
                    GlowCloud.getGlowCloud().getServerManager().checkGroup(GlowCloud.getGlowCloud().getGroupManager().get(cloudServerSelector.getSelected().getCloudServerGroup().getName()));
                }
            }, 1000);

        } else if (selector instanceof CloudStringSelector) {

            CloudStringSelector cloudStringSelector = (CloudStringSelector) selector;

            CloudServer cloudServer = GlowCloud.getGlowCloud().getServerManager().get(cloudStringSelector.getSelected());

            if (GlowCloud.getGlowCloud().getWrapperManager().search(cloudServer.getCloudServerGroup().getWrapperID()) instanceof ConnectedCloudWrapper) {

                ConnectedCloudWrapper cloudWrapper = (ConnectedCloudWrapper) GlowCloud.getGlowCloud().getWrapperManager().search(cloudServer.getCloudServerGroup().getWrapperID());

                GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getPacketManager().writePacket(cloudWrapper.getChannelConnection(), new PacketOutStopCloudServer(cloudServer));

            }

            GlowCloud.getGlowCloud().getScheduler().runWaitTask(new Runnable() {
                @Override
                public void run() {
                    GlowCloud.getGlowCloud().getServerManager().checkGroup(GlowCloud.getGlowCloud().getGroupManager().get(cloudServer.getCloudServerGroup().getName()));
                }
            }, 1000);

        } else if (selector instanceof CloudServerGroupSelector) {

            CloudServerGroupSelector server = (CloudServerGroupSelector) selector;

            List<CloudServer> serversList = search(selector);

            for (CloudServer cloudServer : serversList) {
                if (cloudServer instanceof OnlineCloudServer) {

                    if (GlowCloud.getGlowCloud().getWrapperManager().search(cloudServer.getCloudServerGroup().getWrapperID()) instanceof ConnectedCloudWrapper) {

                        ConnectedCloudWrapper cloudWrapper = (ConnectedCloudWrapper) GlowCloud.getGlowCloud().getWrapperManager().search(cloudServer.getCloudServerGroup().getWrapperID());

                        GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getPacketManager().writePacket(cloudWrapper.getChannelConnection(), new PacketOutStopCloudServer(cloudServer));

                    }

                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            GlowCloud.getGlowCloud().getScheduler().runWaitTask(new Runnable() {
                @Override
                public void run() {
                    GlowCloud.getGlowCloud().getServerManager().checkGroup(server.getSelected());
                }
            }, 1000);

        }

    }

    public List<CloudServer> getServers() {
        return servers;
    }

    public CloudServer get(String name) {

        List<CloudServer> cloudServerList = servers.stream().filter(item -> item.getServiceID().equals(name)).collect(Collectors.toList());

        return cloudServerList.size() > 0 ? cloudServerList.get(0) : null;

    }


}
