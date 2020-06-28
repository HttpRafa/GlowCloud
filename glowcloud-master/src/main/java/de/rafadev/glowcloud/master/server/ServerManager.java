package de.rafadev.glowcloud.master.server;

//------------------------------
//
// This class was developed by Rafael K.
// On 28.06.2020 at 19:38
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.network.packet.out.PacketOutStartCloudServer;
import de.rafadev.glowcloud.master.server.classes.QueueCloudServer;
import de.rafadev.glowcloud.master.wrapper.classes.ConnectedCloudWrapper;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServerManager {

    private List<CloudServer> servers = new LinkedList<>();

    public ServerManager() {

    }

    public void checkGroup(CloudServerGroup cloudServerGroup) {

        GlowCloud.getGlowCloud().getLogger().debug("§7Checking the group §b" + cloudServerGroup.getName() + "§8...");

        if(search(cloudServerGroup.getName()).size() < cloudServerGroup.getMinServerCount()) {

            startListedServer(cloudServerGroup, cloudServerGroup.getMinServerCount() - search(cloudServerGroup.getName()).size());

        }

    }

    public void startListedServer(CloudServerGroup cloudServerGroup, int amount) {

        GlowCloud.getGlowCloud().getLogger().debug("Starting §b" + amount + " §7servers for the group §b" + cloudServerGroup.getName() + "§8.");

        String spliter = "-";

        int min = search(cloudServerGroup.getName()).size() + amount;
        int count = 0;

        for(int i = 1; count < min; i++) {

            String name = cloudServerGroup.getName() + spliter + i;

            if(get(name) != null) {
                count++;
            } else {
                startServer(name, UUID.randomUUID(), cloudServerGroup);
                count++;
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void startServer(String serviceID, UUID uuid, CloudServerGroup cloudServerGroup) {

        QueueCloudServer cloudServer = new QueueCloudServer(serviceID, uuid, cloudServerGroup.toSimple());

        ConnectedCloudWrapper cloudWrapper = (ConnectedCloudWrapper) GlowCloud.getGlowCloud().getWrapperManager().search(cloudServerGroup.getWrapperID());

        GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getPacketManager().writePacket(cloudWrapper.getChannelConnection(), new PacketOutStartCloudServer(cloudServer));
        GlowCloud.getGlowCloud().getLogger().info("The server §e" + cloudServer.getServiceID() + " §7is now in the queue of the wrapper §e" + cloudWrapper.getId() + "§8.");

        servers.add(cloudServer);

    }

    public CloudServer get(String name) {

        List<CloudServer> cloudServerList = servers.stream().filter(item -> item.getServiceID().equals(name)).collect(Collectors.toList());

        return cloudServerList.size() > 0 ? cloudServerList.get(0) : null;

    }

    public List<CloudServer> search(String groupName) {

        return servers.stream().filter(item -> item.getCloudServerGroup().getName().equals(groupName)).collect(Collectors.toList());

    }

}
