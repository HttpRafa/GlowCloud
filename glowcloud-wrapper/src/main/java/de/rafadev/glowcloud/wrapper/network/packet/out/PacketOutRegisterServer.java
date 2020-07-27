package de.rafadev.glowcloud.wrapper.network.packet.out;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 21:50
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.network.protocol.packet.Packet;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketRC;

import java.util.UUID;

public class PacketOutRegisterServer extends Packet {

    public PacketOutRegisterServer(CloudServer cloudServer) {
        super(PacketRC.MAIN + 11, UUID.randomUUID(), new Document().append("cloudServer", JsonParser.parseString(new Gson().toJson(cloudServer))));
    }

}
