package de.rafadev.glowcloud.master.event.events;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 12:27
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.event.CloudEvent;
import io.netty.channel.Channel;

public class NetworkChannelConnectEvent extends CloudEvent {

    private Channel channel;

    public NetworkChannelConnectEvent(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

}
