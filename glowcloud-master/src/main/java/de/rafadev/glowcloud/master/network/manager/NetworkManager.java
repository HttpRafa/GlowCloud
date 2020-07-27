package de.rafadev.glowcloud.master.network.manager;

//------------------------------
//
// This class was developed by Rafael K.
// On 08.06.2020 at 16:16
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.protocol.ProtocolSender;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketRC;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.network.logging.NetworkLogger;
import de.rafadev.glowcloud.master.network.packet.in.PacketInRegisterServer;
import de.rafadev.glowcloud.master.network.packet.in.PacketInUnRegisterServer;
import de.rafadev.glowcloud.master.network.server.NetworkServer;
import de.rafadev.glowcloud.master.server.classes.OnlineCloudServer;
import de.rafadev.glowcloud.master.wrapper.classes.CloudWrapper;
import de.rafadev.glowcloud.master.wrapper.classes.OfflineCloudWrapper;

public class NetworkManager {

    private NetworkServer networkServer;
    private NetworkLogger networkLogger;

    public NetworkManager() {
        networkLogger = new NetworkLogger();
    }

    public void startNetworkServer(NetworkAddress networkAddress) {
        networkServer = new NetworkServer(networkAddress, GlowCloud.getGlowCloud().getLogger());

        networkServer.getPacketManager().registerHandler(PacketRC.MAIN + 11, new PacketInRegisterServer());
        networkServer.getPacketManager().registerHandler(PacketRC.MAIN + 12, new PacketInUnRegisterServer());

        networkServer.start();
    }

    public void handleDisconnect(ProtocolSender protocolSender) {

        GlowCloud.getGlowCloud().getLogger().debug("§cProcessing are disconnect from a registered connection§8.");
        if(protocolSender instanceof CloudWrapper) {

            if(GlowCloud.getGlowCloud().getWrapperManager().disconnectWrapper(((CloudWrapper) protocolSender).getId())) {
                GlowCloud.getGlowCloud().getLogger().info("The Wrapper §e" + ((CloudWrapper) protocolSender).getId() + " §7is now §cdisconnected§8.");
                GlowCloud.getGlowCloud().getWrapperManager().handleDisconnect((OfflineCloudWrapper) GlowCloud.getGlowCloud().getWrapperManager().search(((CloudWrapper) protocolSender).getId()));
            } else {

            }

        } else if(protocolSender instanceof CloudServer) {

        }

    }

    public NetworkServer getNetworkServer() {
        return networkServer;
    }

    public NetworkLogger getNetworkLogger() {
        return networkLogger;
    }
}
