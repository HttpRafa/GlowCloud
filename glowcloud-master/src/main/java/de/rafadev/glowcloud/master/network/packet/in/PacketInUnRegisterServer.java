package de.rafadev.glowcloud.master.network.packet.in;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 22:38
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketInHandler;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketSender;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.wrapper.classes.ConnectedCloudWrapper;

public class PacketInUnRegisterServer extends PacketInHandler {

    @Override
    public void handle(Document document, PacketSender packetSender) {

        if(packetSender instanceof ConnectedCloudWrapper) {

            CloudServer cloudServer = new Gson().fromJson(document.get("cloudServer").getAsJsonObject(), CloudServer.class);
            GlowCloud.getGlowCloud().getServerManager().unRegisterServer(cloudServer);

        }

    }

}
