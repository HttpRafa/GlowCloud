package de.rafadev.glowcloud.master.network.manager;

//------------------------------
//
// This class was developed by Rafael K.
// On 08.06.2020 at 16:16
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.master.network.server.NetworkServer;

import java.net.NetworkInterface;

public class NetworkManager {

    private NetworkServer networkServer;

    public NetworkManager() {

    }

    public void startNetworkServer(NetworkAddress networkAddress) {
        networkServer = new NetworkServer(networkAddress);
        networkServer.start();
    }

    public NetworkServer getNetworkServer() {
        return networkServer;
    }
}
