package de.rafadev.glowcloud.wrapper.queue;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 17:03
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.server.classes.QueueCloudServer;

import java.util.LinkedList;
import java.util.List;

public class CloudServerQueue implements IGlowCloudObject {

    private List<QueueCloudServer> queue = new LinkedList<>();

    public void addToQueue(QueueCloudServer cloudServer) {

        GlowCloudWrapper.getGlowCloud().getLogger().info("§7The server §e" + cloudServer.getServiceID() + " §7is now in the wrapper queue§8. [§e" + queue.size() + "§8]");
        queue.add(cloudServer);

        GlowCloudWrapper.getGlowCloud().getServerManager().block();

    }

    public void removeFromQueue(QueueCloudServer cloudServer) {
        queue.remove(cloudServer);
    }

    public QueueCloudServer getAndRemove() {

        if(queue.size() == 0) {
            return null;
        }
        QueueCloudServer server = queue.get(0);
        queue.remove(server);
        return server;

    }

    public QueueCloudServer get() {
        if(queue.size() == 0) {
            return null;
        }
        return queue.get(0);
    }

}
