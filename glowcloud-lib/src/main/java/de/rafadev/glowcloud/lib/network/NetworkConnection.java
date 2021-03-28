package de.rafadev.glowcloud.lib.network;

//------------------------------
//
// This class was developed by RafaDev
// On 17.05.2020 at 19:25
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonObject;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.auth.NetworkAuthentication;
import de.rafadev.glowcloud.lib.network.auth.packet.out.PacketOutAuth;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketManager;
import de.rafadev.glowcloud.lib.network.utils.CloudUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Timer;
import java.util.TimerTask;

public class NetworkConnection implements IGlowCloudObject {

    private EventLoopGroup eventLoopGroup;
    private ChannelConnection channelConnection;

    private PacketManager packetManager;

    private NetworkAddress networkAddress;
    private int trys;

    public NetworkConnection(NetworkAddress networkAddress, CloudLogger cloudLogger) {
        this.networkAddress = networkAddress;
        this.packetManager = new PacketManager(cloudLogger);
    }

    public void tryConnect(NetworkAuthentication networkAuthentication, SimpleChannelInboundHandler<?> handler, CloudLogger cloudLogger, JsonObject extraAuthData) {
        trys++;

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(cloudLogger != null) cloudLogger.info("Try to connect the host §8[§e" + networkAddress.toString() + "§8]...");

                eventLoopGroup = CloudUtils.eventLoopGroup();

                try {
                    Bootstrap b = new Bootstrap();
                    b.group(eventLoopGroup);
                    b.channel(CloudUtils.isEpollAvailable() ? EpollSocketChannel.class : NioSocketChannel.class);
                    b.option(ChannelOption.SO_KEEPALIVE, true);
                    b.handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                        }
                    });

                    try {
                        ChannelFuture f = b.connect(networkAddress.getHost(), networkAddress.getPort()).sync().addListener(
                                new ChannelFutureListener() {
                                    @Override
                                    public void operationComplete(ChannelFuture channelFuture) {
                                        try {
                                            if (channelFuture.isSuccess()) {

                                                channelFuture.channel().pipeline().addLast(handler);

                                                if(cloudLogger != null) cloudLogger.info("The connection to the host was successfully §acreated§8.");
                                                channelConnection = new ChannelConnection(channelFuture.channel());
                                                if(cloudLogger != null) cloudLogger.info("Try to auth the connection on the server§8.");
                                                packetManager.writePacket(channelConnection, extraAuthData == null ? new PacketOutAuth(networkAuthentication) : new PacketOutAuth(networkAuthentication, extraAuthData));

                                                Timer timer = new Timer();
                                                timer.schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        if (!channelFuture.channel().isActive()) {
                                                            if(cloudLogger != null) cloudLogger.error("§4The connection was killed§8.");
                                                        }
                                                    }
                                                }, 1000);

                                            } else {

                                                channelConnection = null;
                                                if (cloudLogger != null) {
                                                    cloudLogger.error("Can`t connect to the NetworkAddress§8[§c" + networkAddress.getHost() + ":" + networkAddress.getPort() + "§8]");
                                                    cloudLogger.error("This is the try §8» §4" + trys);
                                                }
                                                eventLoopGroup.shutdownGracefully();

                                            }
                                        } catch (Exception exception) {

                                        }
                                    }
                                });
                        f.channel().closeFuture().sync();
                    } catch (Exception e) {

                        channelConnection = null;
                        if(cloudLogger != null) {
                            cloudLogger.error("Can`t connect to the NetworkAddress§8[§c" + networkAddress.getHost() + ":" + networkAddress.getPort() + "§8]");
                            cloudLogger.error("This is the try §8» §4" + trys);
                        }

                        eventLoopGroup.shutdownGracefully();

                    }
                } finally {

                    channelConnection = null;

                    eventLoopGroup.shutdownGracefully();
                }
            }
        }).start();

    }

    public boolean shutdown() {

        if(eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
            return true;
        } else {
            return false;
        }

    }

    public boolean isActive() {
        return channelConnection != null;
    }

    public ChannelConnection getChannelConnection() {
        return channelConnection;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public NetworkAddress getNetworkAddress() {
        return networkAddress;
    }
}
