package de.rafadev.glowcloud.lib.network.auth.packet.out;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.06.2020 at 18:47
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.network.auth.NetworkAuthentication;
import de.rafadev.glowcloud.lib.network.protocol.packet.Packet;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketRC;

import java.util.UUID;

public class PacketOutAuth extends Packet {

    public PacketOutAuth(NetworkAuthentication authentication) {
        super(PacketRC.MAIN - 1, UUID.randomUUID(), new Document().append("auth", new JsonParser().parse(new Gson().toJson(authentication))));
    }

    public PacketOutAuth(NetworkAuthentication authentication, JsonObject extraData) {
        super(PacketRC.MAIN - 1, UUID.randomUUID(), new Document().append("auth", new JsonParser().parse(new Gson().toJson(authentication))).append("extra", extraData));
    }

}
