package de.rafadev.glowcloud.master.network.server;

//------------------------------
//
// This class was developed by Rafael K.
// On 08.06.2020 at 16:04
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.network.ChannelConnection;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.protocol.ProtocolSender;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketManager;
import de.rafadev.glowcloud.lib.network.utils.CloudUtils;
import de.rafadev.glowcloud.master.event.events.NetworkChannelConnectEvent;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.network.auth.GlowCloudClientAuth;
import de.rafadev.glowcloud.master.server.classes.OnlineCloudServer;
import de.rafadev.glowcloud.master.wrapper.classes.CloudWrapper;
import de.rafadev.glowcloud.master.wrapper.classes.ConnectedCloudWrapper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.List;
import java.util.stream.Collectors;

public class NetworkServer implements IGlowCloudObject {

    private NetworkAddress networkAddress;
    private EventLoopGroup eventLoopGroup = CloudUtils.eventLoopGroup();

    private PacketManager packetManager;

    public NetworkServer(NetworkAddress networkAddress, CloudLogger cloudLogger) {
        this.networkAddress = networkAddress;
        this.packetManager = new PacketManager(cloudLogger);
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

                                        GlowCloud.getGlowCloud().getLogger().debug("§bClient§8[§7" + channel.remoteAddress().toString() + "§8] §7--> §bChannelInitializer");

                                        if(GlowCloud.getGlowCloud().isStarted()) {
                                            GlowCloud.getGlowCloud().getLogger().info("A new connection§8[§e" + channel.remoteAddress().toString() + "§8] §7is §eestablished§8...");

                                            NetworkChannelConnectEvent event = new NetworkChannelConnectEvent(channel);

                                            GlowCloud.getGlowCloud().getModuleManager().getEventManager().callEvent(event);

                                            if(!event.isCancelled()) {
                                                channel.pipeline().addLast("GlowCloudAuthHandler", new GlowCloudClientAuth());
                                            } else {
                                                channel.close();
                                            }
                                        } else {
                                            channel.close();
                                        }
                                    }

                                    @Override
                                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    }
                                })
                                .option(ChannelOption.SO_BACKLOG, 128)
                                .option(ChannelOption.SO_RCVBUF, 1220)
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

                                            GlowCloud.getGlowCloud().getLogger().info("The NetworkServer is started on the port " + networkAddress.getPort());
                                            GlowCloud.getGlowCloud().getNetworkManager().getNetworkLogger().log("--- SERVER STARTED ---");
                                        } else {

                                            GlowCloud.getGlowCloud().getLogger().error("Can`t start the NetworkServer on the port " + networkAddress.getPort());
                                            GlowCloud.getGlowCloud().shutdown();
                                            GlowCloud.getGlowCloud().getNetworkManager().getNetworkLogger().log("--- SERVER START FAILED [" + channelFuture.cause().getMessage() + "] ---");

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

    public boolean shutdown() {
        if(eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
            GlowCloud.getGlowCloud().getNetworkManager().getNetworkLogger().log("--- SERVER STOPPED ---");
            return true;
        } else {
            return false;
        }
    }

    public ProtocolSender getProtocolSender(ChannelConnection channelConnection) {

        List<CloudWrapper> wrappers = GlowCloud.getGlowCloud().getWrapperManager().getWrappers().stream().filter(item -> (item instanceof ConnectedCloudWrapper) && ((ConnectedCloudWrapper) item).getChannelConnection().getChannel() == channelConnection.getChannel()).collect(Collectors.toList());
        if(wrappers.size() > 0) {
            return wrappers.get(0);
        }

        List<CloudServer> serverList = GlowCloud.getGlowCloud().getServerManager().getServers().stream().filter(item -> (item instanceof OnlineCloudServer) && (((OnlineCloudServer) item).getChannelConnection() != null) && ((OnlineCloudServer) item).getChannelConnection() == channelConnection).collect(Collectors.toList());
        if(serverList.size() > 0) {
            return serverList.get(0);
        }

        return null;

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
