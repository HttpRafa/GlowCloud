package de.rafadev.glowcloud.wrapper.network.packet.in;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 16:59
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketInHandler;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketSender;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.server.classes.QueueCloudServer;

public class PacketInStartCloud extends PacketInHandler {

    @Override
    public void handle(Document document, PacketSender packetSender) {

        QueueCloudServer queueCloudServer = new Gson().fromJson(document.get("cloudServer").getAsJsonObject(), QueueCloudServer.class);
        GlowCloudWrapper.getGlowCloud().getServerManager().getServerQueue().addToQueue(queueCloudServer);

    }

}
