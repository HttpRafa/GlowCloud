package de.rafadev.glowcloud.lib.network;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 13:24
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import io.netty.channel.Channel;

public class ChannelConnection implements IGlowCloudObject {

    private Channel channel;

    public ChannelConnection(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
