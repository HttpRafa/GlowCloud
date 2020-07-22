package de.rafadev.glowcloud.master.event.events;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 12:25
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.master.event.CloudEvent;

public class CloudServerStopEvent extends CloudEvent {

    private CloudServer cloudServer;
    private String id;

    public CloudServerStopEvent(CloudServer cloudServer) {
        this.cloudServer = cloudServer;
        this.id = cloudServer.getServiceID();
    }

    public CloudServer getCloudServer() {
        return cloudServer;
    }

    public String getId() {
        return id;
    }
}
