package de.rafadev.glowcloud.core.bridge.network.result;

//------------------------------
//
// This class was developed by Rafael K.
// On 23.07.2020 at 21:51
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.network.address.NetworkAddress;

public class ConnectTryResult {

    private NetworkAddress address;
    private int state;

    public ConnectTryResult(NetworkAddress address, int state) {
        this.address = address;
        this.state = state;
    }

    public NetworkAddress getAddress() {
        return address;
    }

    public int getState() {
        return state;
    }
}
