package de.rafadev.glowcloud.core.bridge.network.handler;

//------------------------------
//
// This class was developed by Rafael K.
// On 23.07.2020 at 14:01
// In the project GlowCloud
//
//------------------------------

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DefaultNetworkHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {

    }

}
