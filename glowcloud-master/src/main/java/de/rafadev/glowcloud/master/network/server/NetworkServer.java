package de.rafadev.glowcloud.master.network.server;

//------------------------------
//
// This class was developed by Rafael K.
// On 08.06.2020 at 16:04
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketManager;
import de.rafadev.glowcloud.lib.network.utils.NetworkUtils;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.network.auth.GlowCloudClientAuth;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NetworkServer implements IGlowCloudObject {

    private NetworkAddress networkAddress;
    private EventLoopGroup eventLoopGroup = NetworkUtils.eventLoopGroup();

    private PacketManager packetManager = new PacketManager();

    public NetworkServer(NetworkAddress networkAddress) {
        this.networkAddress = networkAddress;
    }

    public void start() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    try {
                        ServerBootstrap b = new ServerBootstrap();
                        b.group(eventLoopGroup)
                                .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                                .childHandler(new ChannelInitializer<Channel>() {

                                    /*
                                    Channel connecting
                                     */
                                    @Override
                                    public void initChannel(Channel channel) throws Exception {
                                        GlowCloud.getGlowCloud().getLogger().info("A new connection§8[§e" + channel.remoteAddress().toString() + "§8] §7is §eestablished§8...");
                                        channel.pipeline().addLast("GlowCloudAuthHandler", new GlowCloudClientAuth());
                                    }

                                })
                                .option(ChannelOption.SO_BACKLOG, 128)
                                .childOption(ChannelOption.SO_KEEPALIVE, true);

                        // Bind and start to accept incoming connections.

                        GlowCloud.getGlowCloud().getLogger().info("Try to bind the NetworkServer to the port "+ networkAddress.getPort());
                        GlowCloud.getGlowCloud().getLogger().info("The cloud is using the " + (Epoll.isAvailable() ? "EpollServerSocketChannel" : "NioServerSocketChannel"));

                        ChannelFuture f = b.bind(networkAddress.getPort()).sync().addListener(
                                new ChannelFutureListener() {
                                    @Override
                                    public void operationComplete(ChannelFuture channelFuture) throws Exception {

                                        /*
                                        Start result
                                         */
                                        if (channelFuture.isSuccess()) {

                                            GlowCloud.getGlowCloud().getLogger().info("The NetworkServer is started on the " + networkAddress.getPort());

                                        } else {

                                            GlowCloud.getGlowCloud().getLogger().error("Can`t start the NetworkServer on the port " + networkAddress.getPort());
                                            GlowCloud.getGlowCloud().shutdown();

                                        }
                                    }
                                }).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);

                        // Wait until the server socket is closed.
                        // In this example, this does not happen, but you can do that to gracefully
                        // shut down your server.
                        f.channel().closeFuture().sync();
                    } finally {
                        eventLoopGroup.shutdownGracefully();
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void shutdown() {
        if(eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public NetworkAddress getNetworkAddress() {
        return networkAddress;
    }

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }
}
