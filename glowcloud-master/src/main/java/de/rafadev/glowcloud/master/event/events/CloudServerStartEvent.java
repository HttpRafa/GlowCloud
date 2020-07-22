package de.rafadev.glowcloud.master.event.events;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 12:26
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.master.event.CloudEvent;
import de.rafadev.glowcloud.master.wrapper.classes.CloudWrapper;

public class CloudServerStartEvent extends CloudEvent {

    private CloudServer cloudServer;
    private CloudWrapper cloudWrapper;

    public CloudServerStartEvent(CloudServer cloudServer, CloudWrapper cloudWrapper) {
        this.cloudServer = cloudServer;
        this.cloudWrapper = cloudWrapper;
    }

    public CloudServer getCloudServer() {
        return cloudServer;
    }

    public CloudWrapper getCloudWrapper() {
        return cloudWrapper;
    }

}
