package de.rafadev.glowcloud.lib.network.protocol;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 09:55
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.lib.network.ChannelConnection;
import io.netty.channel.Channel;

public class ProtocolSender implements IGlowCloudObject {

    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public ChannelConnection getAsChannelConnection() {
        return new ChannelConnection(channel);
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
