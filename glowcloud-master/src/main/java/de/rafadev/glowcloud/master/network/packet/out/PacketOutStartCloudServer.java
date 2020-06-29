package de.rafadev.glowcloud.master.network.packet.out;

//------------------------------
//
// This class was developed by Rafael K.
// On 28.06.2020 at 22:49
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.network.protocol.packet.Packet;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketRC;
import de.rafadev.glowcloud.master.server.classes.QueueCloudServer;

import java.util.UUID;

public class PacketOutStartCloudServer extends Packet {

    public PacketOutStartCloudServer(QueueCloudServer cloudServer) {
        super(PacketRC.MAIN + 10, UUID.randomUUID(), new Document().append("cloudServer", new JsonParser().parse(new Gson().toJson(cloudServer))));
    }

}
