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
import de.rafadev.glowcloud.lib.network.auth.NetworkAuthentication;
import de.rafadev.glowcloud.lib.scheduler.GlowScheduler;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.network.packet.handler.DefaultHandler;

public class NetworkManager {

    private NetworkConnection networkConnection;

    private int TaskID = 0;
    private boolean checkConnection = true;

    public NetworkManager(NetworkAddress networkAddress) {
        networkConnection = new NetworkConnection(networkAddress);
    }

    public void start(NetworkAuthentication networkAuthentication, GlowScheduler scheduler) {
        this.getNetworkConnection().tryConnect(networkAuthentication, new DefaultHandler(), GlowCloudWrapper.getGlowCloud().getLogger());

        TaskID = scheduler.runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                if(!networkConnection.isActive() && checkConnection) {
                    networkConnection.tryConnect(networkAuthentication, new DefaultHandler(), GlowCloudWrapper.getGlowCloud().getLogger());
                }
            }
        }, 1000, 5000);

    }

    public void setCheckConnection(boolean checkConnection) {
        this.checkConnection = checkConnection;
    }

    public void shutdown() {
        setCheckConnection(false);
        GlowCloudWrapper.getGlowCloud().getScheduler().cancel(TaskID);
    }

    public boolean isCheckConnection() {
        return checkConnection;
    }

    public int getTaskID() {
        return TaskID;
    }

    public NetworkConnection getNetworkConnection() {
        return networkConnection;
    }
}
