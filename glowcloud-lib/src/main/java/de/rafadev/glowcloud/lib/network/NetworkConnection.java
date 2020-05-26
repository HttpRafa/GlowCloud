package de.rafadev.glowcloud.lib.network;

//------------------------------
//
// This class was developed by RafaDev
// On 17.05.2020 at 19:25
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.utils.NetworkUtils;
import io.netty.channel.EventLoopGroup;

public class NetworkConnection implements IGlowCloudObject {

    private EventLoopGroup eventLoopGroup = NetworkUtils.eventLoopGroup();
    private ChannelConnection channelConnection;

    private NetworkAddress networkAddress;
    private int trys;

    public NetworkConnection(NetworkAddress networkAddress) {
        this.networkAddress = networkAddress;
    }

    public void tryConnect() {
        trys++;
    }

    public ChannelConnection getChannelConnection() {
        return channelConnection;
    }
}
