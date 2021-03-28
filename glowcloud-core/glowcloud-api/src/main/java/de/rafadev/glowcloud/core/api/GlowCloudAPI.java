package de.rafadev.glowcloud.core.api;

//------------------------------
//
// This class was developed by Rafael K.
// On 23.07.2020 at 13:26
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.core.bridge.GlowCloudBridge;
import de.rafadev.glowcloud.core.bridge.server.classes.CloudServerSnapshot;

import java.util.Collection;

public class GlowCloudAPI {

    private static GlowCloudAPI api;

    public GlowCloudAPI() {
        api = this;
    }

    public Collection<CloudServerSnapshot> getServers(String group) {
        return GlowCloudBridge.getBridge().getServerBridge().getServersByGroup(group);
    }

    public CloudServerSnapshot getServer(String identifier) {

        Collection<CloudServerSnapshot> servers = GlowCloudBridge.getBridge().getServerBridge().getServers();

        for (CloudServerSnapshot server : servers) {if(server.getServiceID().equals(identifier)) return server; }

        return null;

    }

    public static GlowCloudAPI getAPI() {
        return api;
    }

}
