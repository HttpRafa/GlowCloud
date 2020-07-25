package de.rafadev.glowcloud.core.bridge.server;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.07.2020 at 12:05
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import de.rafadev.glowcloud.core.bridge.GlowCloudBridge;
import de.rafadev.glowcloud.core.bridge.network.packet.out.PacketOutRequestServers;
import de.rafadev.glowcloud.core.bridge.server.classes.CloudServerSnapshot;
import de.rafadev.glowcloud.lib.classes.selector.selectors.CloudStringSelector;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.network.protocol.packet.request.Result;

import java.util.LinkedList;
import java.util.List;

public class CloudServerBridge {

    public List<CloudServerSnapshot> getServersByGroup(String group) {

        Result result = GlowCloudBridge.getBridge().getNetworkBridge().getNetworkConnection().getPacketManager().writeRequest(GlowCloudBridge.getBridge().getNetworkBridge().getNetworkConnection().getChannelConnection(), new PacketOutRequestServers(new CloudStringSelector(group)));

        List<CloudServerSnapshot> serverSnapshots = new LinkedList<>();

        JsonArray array = result.getData().get("servers").getAsJsonArray();
        for (JsonElement element : array) {
            CloudServer cloudServer = new Gson().fromJson(element, CloudServer.class);
            serverSnapshots.add((CloudServerSnapshot) cloudServer);
        }

        return serverSnapshots;

    }

}
