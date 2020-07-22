package de.rafadev.glowcloud.master.event.events;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 12:24
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.event.CloudEvent;
import de.rafadev.glowcloud.master.server.classes.QueueCloudServer;

public class CloudServerAddToQueueEvent extends CloudEvent {

    private QueueCloudServer queueCloudServer;

    public CloudServerAddToQueueEvent(QueueCloudServer queueCloudServer) {
        this.queueCloudServer = queueCloudServer;
    }

    public QueueCloudServer getQueueCloudServer() {
        return queueCloudServer;
    }
}
