package de.rafadev.glowcloud.wrapper.network.packet.handler;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.06.2020 at 19:12
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.color.ConsoleColor;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.master.CloudMaster;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.rmi.server.ExportException;

public class DefaultHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {

        try {

            CloudMaster cloudMaster = new CloudMaster();
            cloudMaster.setChannel(ctx.channel());

            GlowCloudWrapper.getGlowCloud().getNetworkManager().getNetworkConnection().getPacketManager().handle(msg, cloudMaster);

        } catch (Exception exception) {
            GlowCloudWrapper.getGlowCloud().getLogger().handleException(exception);
        }

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {

        try {

            GlowCloudWrapper.getGlowCloud().getLogger().warning("§cThe connection with the master was interrupted§8.");
            GlowCloudWrapper.getGlowCloud().reset();

        } catch (Exception exception) {
            GlowCloudWrapper.getGlowCloud().getLogger().handleException(exception);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        GlowCloudWrapper.getGlowCloud().getLogger().error(cause.getMessage());
    }
}
