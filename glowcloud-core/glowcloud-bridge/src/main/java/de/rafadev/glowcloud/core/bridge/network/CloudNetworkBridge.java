package de.rafadev.glowcloud.core.bridge.network;

//------------------------------
//
// This class was developed by Rafael K.
// On 23.07.2020 at 13:53
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonObject;
import de.rafadev.glowcloud.core.bridge.GlowCloudBridge;
import de.rafadev.glowcloud.core.bridge.network.handler.DefaultNetworkHandler;
import de.rafadev.glowcloud.core.bridge.network.result.ConnectTryResult;
import de.rafadev.glowcloud.lib.network.NetworkConnection;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.auth.NetworkAuthentication;
import de.rafadev.glowcloud.lib.network.auth.types.AuthServiceType;
import de.rafadev.glowcloud.lib.network.utils.CloudUtils;

public class CloudNetworkBridge {

    private NetworkConnection networkConnection;

    public ConnectTryResult connectToMaster(NetworkAddress networkAddress) {
        networkConnection = new NetworkConnection(networkAddress, null);

        /*
        Create a auth
         */
        NetworkAuthentication networkAuthentication = new NetworkAuthentication(GlowCloudBridge.getBridge().getServiceID(), AuthServiceType.BUKKIT_OR_BUNGEECORD, GlowCloudBridge.getBridge().getBridgeAuthentication().getAuthKey());

        networkConnection.tryConnect(networkAuthentication, new DefaultNetworkHandler(), null, new JsonObject());
        CloudUtils.sleep(2000);

        if(networkConnection.isActive()) {
            return new ConnectTryResult(networkAddress, 1);
        } else {
            return new ConnectTryResult(networkAddress, 0);
        }

    }

    public NetworkConnection getNetworkConnection() {
        return networkConnection;
    }
}
