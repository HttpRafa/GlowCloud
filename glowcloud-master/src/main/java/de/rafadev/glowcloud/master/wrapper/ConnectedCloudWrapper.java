package de.rafadev.glowcloud.master.wrapper;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 13:21
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.network.ChannelConnection;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketSender;
import de.rafadev.glowcloud.master.wrapper.classes.CloudWrapper;

public class ConnectedCloudWrapper extends CloudWrapper {

    private ChannelConnection channelConnection;

    public ConnectedCloudWrapper(String id, String host, int heap, ChannelConnection channelConnection) {
        super(id, host, heap);
        this.channelConnection = channelConnection;
    }

    public ChannelConnection getChannelConnection() {
        return channelConnection;
    }

    public void setChannelConnection(ChannelConnection channelConnection) {
        this.channelConnection = channelConnection;
    }
}
