package de.rafadev.glowcloud.wrapper.network.manager;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.06.2020 at 19:07
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonObject;
import de.rafadev.glowcloud.lib.network.NetworkConnection;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.auth.NetworkAuthentication;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketRC;
import de.rafadev.glowcloud.lib.scheduler.GlowScheduler;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.network.packet.handler.DefaultHandler;
import de.rafadev.glowcloud.wrapper.network.packet.in.PacketInShutdownWrapper;
import de.rafadev.glowcloud.wrapper.network.packet.in.PacketInStartCloudServer;
import de.rafadev.glowcloud.wrapper.network.packet.in.PacketInStopCloudServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

public class NetworkManager {

    private NetworkConnection networkConnection;

    private int TaskID = 0;
    private boolean checkConnection = true;

    public NetworkManager(NetworkAddress networkAddress) {
        networkConnection = new NetworkConnection(networkAddress, GlowCloudWrapper.getGlowCloud().getLogger());

        networkConnection.getPacketManager().registerHandler(PacketRC.MAIN + 10, new PacketInStartCloudServer());
        networkConnection.getPacketManager().registerHandler(PacketRC.MAIN + 13, new PacketInStopCloudServer());
        networkConnection.getPacketManager().registerHandler(PacketRC.SERVER_MANAGING + 1, new PacketInShutdownWrapper());

    }

    public void start(NetworkAuthentication networkAuthentication, GlowScheduler scheduler) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("heap", GlowCloudWrapper.getGlowCloud().getFileManager().getConfig().getAsNumber("totalMemory"));

        this.getNetworkConnection().tryConnect(networkAuthentication, new DefaultHandler(), GlowCloudWrapper.getGlowCloud().getLogger(), jsonObject);

        TaskID = scheduler.runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                if(!networkConnection.isActive() && checkConnection) {
                    networkConnection.tryConnect(networkAuthentication, new DefaultHandler(), GlowCloudWrapper.getGlowCloud().getLogger(), jsonObject);
                }
            }
        }, 1000, 5000);

    }

    public int findFreePort(int start, int stop) {

        Random random = new Random();
        int port = -1;
        int difference = stop-start;

        int trys = 0;
        while (trys <= difference) {
            int tempPort = random.nextInt(difference) + start;

            try {
                ServerSocket serverSocket = new ServerSocket(tempPort);

                serverSocket.close();

                port = tempPort;

                break;
            } catch (IOException e) {
                trys++;
            }
        }

        return port;

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
