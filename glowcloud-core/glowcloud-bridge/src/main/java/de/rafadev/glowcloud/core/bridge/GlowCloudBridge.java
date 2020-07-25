package de.rafadev.glowcloud.core.bridge;

//------------------------------
//
// This class was developed by Rafael K.
// On 23.07.2020 at 13:20
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.core.bridge.authentication.BridgeAuthentication;
import de.rafadev.glowcloud.core.bridge.network.CloudNetworkBridge;
import de.rafadev.glowcloud.core.bridge.server.CloudServerBridge;

public class GlowCloudBridge {

    private static GlowCloudBridge bridge;

    private BridgeAuthentication bridgeAuthentication;
    private CloudNetworkBridge cloudNetworkBridge;
    private CloudServerBridge cloudServerBridge;

    public GlowCloudBridge(BridgeAuthentication bridgeAuthentication) {
        bridge = this;
        this.bridgeAuthentication = bridgeAuthentication;
        this.cloudNetworkBridge = new CloudNetworkBridge();
        this.cloudServerBridge = new CloudServerBridge();
    }

    public String getServiceID() {
        return bridgeAuthentication.getServiceID();
    }

    public BridgeAuthentication getBridgeAuthentication() {
        return bridgeAuthentication;
    }

    public CloudNetworkBridge getNetworkBridge() {
        return cloudNetworkBridge;
    }

    public CloudServerBridge getServerBridge() {
        return cloudServerBridge;
    }

    public static GlowCloudBridge getBridge() {
        return bridge;
    }
}
