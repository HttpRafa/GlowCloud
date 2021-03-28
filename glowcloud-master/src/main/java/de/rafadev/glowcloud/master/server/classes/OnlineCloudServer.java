package de.rafadev.glowcloud.master.server.classes;

//------------------------------
//
// This class was developed by Rafael K.
// On 28.06.2020 at 19:46
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.network.ChannelConnection;
import de.rafadev.glowcloud.lib.template.SimpleCloudTemplate;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.lib.classes.selector.selectors.CloudStringSelector;

import java.util.UUID;

public class OnlineCloudServer extends CloudServer {

    private ChannelConnection channelConnection;

    public OnlineCloudServer(String serviceID, UUID uuid, SimpleCloudServerGroup cloudServerGroup, SimpleCloudTemplate cloudTemplate) {
        super(serviceID, uuid, cloudServerGroup, cloudTemplate);
    }

    public void setChannelConnection(ChannelConnection channelConnection) {
        this.channelConnection = channelConnection;
    }

    public ChannelConnection getChannelConnection() {
        return channelConnection;
    }

    public void shutdown() {
        GlowCloud.getGlowCloud().getServerManager().stopServer(new CloudStringSelector(getServiceID()));
    }

}
