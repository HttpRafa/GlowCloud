package de.rafadev.glowcloud.lib.network;

//------------------------------
//
// This class was developed by RafaDev
// On 17.05.2020 at 19:25
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.auth.NetworkAuthentication;
import de.rafadev.glowcloud.lib.network.auth.packet.out.PacketOutAuth;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketManager;
import de.rafadev.glowcloud.lib.network.utils.NetworkUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Timer;
import java.util.TimerTask;

public class NetworkConnection implements IGlowCloudObject {

    private EventLoopGroup eventLoopGroup;
    private ChannelConnection channelConnection;

    private PacketManager packetManager = new PacketManager();

    private NetworkAddress networkAddress;
    private int trys;

    public NetworkConnection(NetworkAddress networkAddress) {
        this.networkAddress = networkAddress;
    }

    public void tryConnect(NetworkAuthentication networkAuthentication, SimpleChannelInboundHandler<?> handler, CloudLogger cloudLogger) {
        trys++;

        new Thread(new Runnable() {
            @Override
            public void run() {
                cloudLogger.info("Try to connect the host §8[§e" + networkAddress.toString() + "§8]...");

                eventLoopGroup = NetworkUtils.eventLoopGroup();

                try {
                    Bootstrap b = new Bootstrap();
                    b.group(eventLoopGroup);
                    b.channel(NetworkUtils.isEpollAvailable() ? EpollSocketChannel.class : NioSocketChannel.class);
                    b.option(ChannelOption.SO_KEEPALIVE, true);
                    b.handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(handler);
                        }
                    });

                    try {
                        ChannelFuture f = b.connect(networkAddress.getHost(), networkAddress.getPort()).sync().addListener(
                                new ChannelFutureListener() {
                                    @Override
                                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                        if (channelFuture.isSuccess()) {

                                            cloudLogger.info("The connection to the host was successfully §acreated§8.");
                                            channelConnection = new ChannelConnection(channelFuture.channel());
                                            cloudLogger.info("Try to auth the connection on the server§8.");
                                            packetManager.writePacket(channelConnection, new PacketOutAuth(networkAuthentication));

                                            Timer timer = new Timer();
                                            timer.schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    if(!channelFuture.channel().isActive()) {
                                                        cloudLogger.error("§4The connection was killed§8.");
                                                    }
                                                }
                                            }, 1000);

                                        } else {

                                            channelConnection = null;
                                            if(cloudLogger != null) {
                                                cloudLogger.error("Can`t connect to the NetworkAddress§8[§c" + networkAddress.getHost() + ":" + networkAddress.getPort() + "§8]");
                                                cloudLogger.error("This is the try §8» §4" + trys);
                                            }
                                            eventLoopGroup.shutdownGracefully();

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
}
