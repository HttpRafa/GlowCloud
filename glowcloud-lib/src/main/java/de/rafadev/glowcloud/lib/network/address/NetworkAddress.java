package de.rafadev.glowcloud.lib.network.address;

//------------------------------
//
// This class was developed by RafaDev
// On 17.05.2020 at 19:33
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;

import java.net.InetSocketAddress;

public class NetworkAddress implements IGlowCloudObject {

    private String host;
    private int port;

    public NetworkAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public NetworkAddress(InetSocketAddress inetSocketAddress) {
        this.host = inetSocketAddress.getAddress().getHostAddress();
        this.port = inetSocketAddress.getPort();
    }

    /**
     *
     * @return The saved host
     */
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     *
     * @return The saved port
     */
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
