package de.rafadev.glowcloud.lib.network.utils;

//------------------------------
//
// This class was developed by RafaDev
// On 17.05.2020 at 19:30
// In the project GlowCloud
//
//------------------------------

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class NetworkUtils {

    /**
     *
     * @return The Available EventLoopGroup
     */
    public static EventLoopGroup eventLoopGroup() {
        return Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
    }

}