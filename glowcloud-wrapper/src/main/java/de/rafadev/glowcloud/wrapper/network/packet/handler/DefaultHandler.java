package de.rafadev.glowcloud.wrapper.network.packet.handler;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.06.2020 at 19:12
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.master.CloudMaster;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DefaultHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {

        CloudMaster cloudMaster = new CloudMaster();

        GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getPacketManager().handle(msg, cloudMaster);

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {

        GlowCloudWrapper.getGlowCloud().getLogger().warning("§cThe connection with the master was interrupted§8.");
        GlowCloudWrapper.getGlowCloud().reset();

    }
}
