package de.rafadev.glowcloud.wrapper.server.classes;

//------------------------------
//
// This class was developed by Rafael K.
// On 28.06.2020 at 19:43
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;

import java.util.UUID;

public class QueueCloudServer extends CloudServer {

    public QueueCloudServer(String serviceID, UUID uuid, SimpleCloudServerGroup cloudServerGroup) {
        super(serviceID, uuid, cloudServerGroup);
    }
}
