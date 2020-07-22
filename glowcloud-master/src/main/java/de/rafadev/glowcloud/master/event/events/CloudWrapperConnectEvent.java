package de.rafadev.glowcloud.master.event.events;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 10:46
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.network.ChannelConnection;
import de.rafadev.glowcloud.master.event.CloudEvent;
import de.rafadev.glowcloud.master.wrapper.classes.OfflineCloudWrapper;

public class CloudWrapperConnectEvent extends CloudEvent {

    private OfflineCloudWrapper cloudWrapper;
    private String identifier;
    private ChannelConnection channelConnection;

    public CloudWrapperConnectEvent(OfflineCloudWrapper cloudWrapper, String identifier, ChannelConnection channelConnection) {
        this.cloudWrapper = cloudWrapper;
        this.identifier = identifier;
        this.channelConnection = channelConnection;
    }

    public OfflineCloudWrapper getCloudWrapper() {
        return cloudWrapper;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ChannelConnection getChannelConnection() {
        return channelConnection;
    }

}
