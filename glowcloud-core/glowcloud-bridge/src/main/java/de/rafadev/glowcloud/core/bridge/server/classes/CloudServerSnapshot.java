package de.rafadev.glowcloud.core.bridge.server.classes;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.07.2020 at 12:03
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.template.SimpleCloudTemplate;

import java.util.UUID;

public class CloudServerSnapshot extends CloudServer {

    public CloudServerSnapshot(String serviceID, UUID uuid, SimpleCloudServerGroup cloudServerGroup, SimpleCloudTemplate cloudTemplate) {
        super(serviceID, uuid, cloudServerGroup, cloudTemplate);
    }

}