package de.rafadev.glowcloud.wrapper.network.manager;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.06.2020 at 19:07
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.network.NetworkConnection;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;

public class NetworkManager {

    private NetworkConnection networkConnection;

    public NetworkManager(NetworkAddress networkAddress) {
        networkConnection = new NetworkConnection(networkAddress);
    }

    public NetworkConnection getNetworkConnection() {
        return networkConnection;
    }
}
