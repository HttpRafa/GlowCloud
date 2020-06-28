package de.rafadev.glowcloud.master.network.auth;

//------------------------------
//
// This class was developed by Rafael K.
// On 25.06.2020 at 11:50
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.network.ChannelConnection;
import de.rafadev.glowcloud.lib.network.auth.NetworkAuthentication;
import de.rafadev.glowcloud.lib.network.auth.types.AuthServiceType;
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
            ChannelConnection channelConnection = new ChannelConnection(ctx.channel());

            GlowCloud.getGlowCloud().getLogger().debug("§bPacket§8[§7" + ctx.channel().remoteAddress().toString() + "§8/§7" + packet.getId() + "§8] §7--> §bSimpleChannelInboundHandler§8[§7GlowCloudAuthHandler§8]");

            if(packet.getId() == PacketRC.MAIN - 1) {
                // Is a auth packet

                ctx.channel().pipeline().remove("GlowCloudAuthHandler");
                NetworkAuthentication authentication = new Gson().fromJson(new Gson().toJson(packet.getDocument().get("auth").getAsJsonObject()), NetworkAuthentication.class);

                GlowCloud.getGlowCloud().getLogger().info("A new verified connection§8[§e" + ctx.channel().remoteAddress().toString() + "§8] §7is §eestablished§8...");

                if(authentication.getAuthServiceType() == AuthServiceType.WRAPPER) {
                    if(GlowCloud.getGlowCloud().getKeyManager().getConnectionKey().is(authentication.getAuthKey())) {
                        if(GlowCloud.getGlowCloud().getWrapperManager().isRegistered(authentication.getServiceID())) {
                            if(GlowCloud.getGlowCloud().getWrapperManager().connectWrapper(authentication.getServiceID(), channelConnection)) {
                                GlowCloud.getGlowCloud().getLogger().info("The Wrapper §e" + authentication.getServiceID() + " §7is now §aconnected§8.");
                            }
                        } else {

                            GlowCloud.getGlowCloud().getLogger().info("Register Wrapper §e" + authentication.getServiceID() + "§8@§6" + channelConnection.getChannel().remoteAddress().toString().split(":")[0].replaceAll("/", "") + " §7with §e" + packet.getDocument().get("extra").getAsJsonObject().get("heap").getAsInt() + " §7MB");
                            GlowCloud.getGlowCloud().getWrapperManager().registerWrapper(authentication.getServiceID(), channelConnection.getChannel().remoteAddress().toString().split(":")[0].replaceAll("/", ""), packet.getDocument().get("extra").getAsJsonObject().get("heap").getAsInt());
                            if(GlowCloud.getGlowCloud().getWrapperManager().connectWrapper(authentication.getServiceID(), channelConnection)) {
                                GlowCloud.getGlowCloud().getLogger().info("The Wrapper §e" + authentication.getServiceID() + " §7is now §aconnected§8.");
                            }
                        }
                    } else {
                        throw new IllegalStateException("The Key ist wrong!");
                    }
                } else if(authentication.getAuthServiceType() == AuthServiceType.BUKKIT_OR_BUNGEECORD) {



                }

            }

        } catch (Exception exception) {
            GlowCloud.getGlowCloud().getLogger().error("Can`t auth the connection§8[§4" + ctx.channel().remoteAddress().toString() + "§8]...");
            GlowCloud.getGlowCloud().getLogger().error("Reason §8| §4" + exception.getMessage());
            ctx.channel().close();
            exception.printStackTrace();
        }

    }

}
