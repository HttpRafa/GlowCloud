package de.rafadev.glowcloud.wrapper.server.classes;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 11:47
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;

import java.util.UUID;

public class CloudBukkitServer extends CloudServer {

    public CloudBukkitServer(String serviceID, UUID uuid, SimpleCloudServerGroup cloudServerGroup) {
        super(serviceID, uuid, cloudServerGroup);
    }

    public void kill() {

    }

    public boolean prepare() {
        return false;
    }

    public boolean startService() {
        return true;
    }

}
