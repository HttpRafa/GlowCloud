package de.rafadev.glowcloud.master.network.packet.handler;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 22:44
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.main.GlowCloud;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DefaultHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {

        GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getPacketManager().handle(msg, null);

    }

}
