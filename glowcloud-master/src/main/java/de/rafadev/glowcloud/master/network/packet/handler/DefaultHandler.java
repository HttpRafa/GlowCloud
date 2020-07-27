package de.rafadev.glowcloud.master.network.packet.handler;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 22:44
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.color.ConsoleColor;
import de.rafadev.glowcloud.lib.network.ChannelConnection;
import de.rafadev.glowcloud.lib.network.protocol.ProtocolSender;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketSender;
import de.rafadev.glowcloud.master.main.GlowCloud;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DefaultHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {

        GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getPacketManager().handle(msg, (PacketSender) GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getProtocolSender(new ChannelConnection(ctx.channel())));

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {

        GlowCloud.getGlowCloud().getLogger().info("The connection§8[§e" + ctx.channel().remoteAddress().toString() + "§8] §7is §cdisconnected§8...");

        try {
            ProtocolSender protocolSender = GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getProtocolSender(new ChannelConnection(ctx.channel()));

            GlowCloud.getGlowCloud().getNetworkManager().handleDisconnect(protocolSender);
        } catch (Exception exception) {
            GlowCloud.getGlowCloud().getLogger().handleException(exception);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        GlowCloud.getGlowCloud().getNetworkManager().getNetworkLogger().log(getClass().getSimpleName() + ": (" + cause.getClass().getSimpleName() + ") " + cause.getMessage());
    }
}
