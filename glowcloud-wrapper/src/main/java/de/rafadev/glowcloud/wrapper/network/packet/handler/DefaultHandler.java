package de.rafadev.glowcloud.wrapper.network.packet.handler;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.06.2020 at 19:12
// In the project GlowCloud
//
//------------------------------

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DefaultHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

    }

}
