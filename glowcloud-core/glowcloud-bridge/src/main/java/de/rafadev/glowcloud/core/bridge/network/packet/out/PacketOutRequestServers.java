package de.rafadev.glowcloud.core.bridge.network.packet.out;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.07.2020 at 12:06
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.classes.selector.Selector;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.network.protocol.packet.Packet;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketRC;

import java.util.UUID;

public class PacketOutRequestServers extends Packet {

    public PacketOutRequestServers(Selector selector) {
        super(PacketRC.SERVER_MANAGING + 1, UUID.randomUUID(), new Document().append("selector", new JsonParser().parse(new Gson().toJson(selector))));
    }
}
