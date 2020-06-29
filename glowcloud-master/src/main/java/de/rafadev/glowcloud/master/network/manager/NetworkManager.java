package de.rafadev.glowcloud.master.network.manager;

//------------------------------
//
// This class was developed by Rafael K.
// On 08.06.2020 at 16:16
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketRC;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.network.packet.in.PacketInRegisterServer;
import de.rafadev.glowcloud.master.network.packet.in.PacketInUnRegisterServer;
import de.rafadev.glowcloud.master.network.server.NetworkServer;

public class NetworkManager {

    private NetworkServer networkServer;

    public NetworkManager() {

    }

    public void startNetworkServer(NetworkAddress networkAddress) {
        networkServer = new NetworkServer(networkAddress, GlowCloud.getGlowCloud().getLogger());

        networkServer.getPacketManager().registerHandler(PacketRC.MAIN + 11, new PacketInRegisterServer());
        networkServer.getPacketManager().registerHandler(PacketRC.MAIN + 12, new PacketInUnRegisterServer());

        networkServer.start();
    }

    public NetworkServer getNetworkServer() {
        return networkServer;
    }
}
