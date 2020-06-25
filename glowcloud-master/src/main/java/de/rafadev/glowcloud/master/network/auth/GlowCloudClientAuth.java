package de.rafadev.glowcloud.master.network.auth;

//------------------------------
//
// This class was developed by Rafael K.
// On 25.06.2020 at 11:50
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import de.rafadev.glowcloud.lib.network.auth.NetworkAuthentication;
import de.rafadev.glowcloud.lib.network.protocol.packet.Packet;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketRC;
import de.rafadev.glowcloud.master.main.GlowCloud;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GlowCloudClientAuth extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        try {

            Packet packet = GlowCloud.getGlowCloud().getNetworkManager().getNetworkServer().getPacketManager().toPacket(msg);
            if(packet.getId() == PacketRC.MAIN - 1) {
                // Is a auth packet

                ctx.channel().pipeline().remove("GlowCloudAuthHandler");
                NetworkAuthentication authentication = new Gson().fromJson(new Gson().toJson(packet.getDocument().get("auth").getAsJsonObject()), NetworkAuthentication.class);

                GlowCloud.getGlowCloud().getLogger().info("A new verified connection§8[§e" + ctx.channel().remoteAddress().toString() + "§8] §7is §eestablished§8...");

            }

        } catch (Exception exception) {
            GlowCloud.getGlowCloud().getLogger().error("Can`t auth the connection§8[§4" + ctx.channel().remoteAddress().toString() + "§8]...");
            GlowCloud.getGlowCloud().getLogger().error("Reason §8| §4" + exception.getMessage());
            ctx.channel().close();
            exception.printStackTrace();
        }

    }

}
