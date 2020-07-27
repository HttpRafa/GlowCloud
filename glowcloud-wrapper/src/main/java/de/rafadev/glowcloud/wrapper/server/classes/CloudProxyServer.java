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
import de.rafadev.glowcloud.lib.template.SimpleCloudTemplate;

import java.util.UUID;

public class CloudProxyServer extends CloudServer {

    public CloudProxyServer(String serviceID, UUID uuid, SimpleCloudServerGroup cloudServerGroup, SimpleCloudTemplate cloudTemplate) {
        super(serviceID, uuid, cloudServerGroup, cloudTemplate);
    }

    public void kill() {

    }

    public boolean prepare() {
        return true;
    }

    public boolean startService() {
        return true;
    }

    public CloudServer toSimple() {
        return new CloudServer(getServiceID(), getUUID(), getPort(), getCloudServerGroup(), getTemplate());
    }

}
