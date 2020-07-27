package de.rafadev.glowcloud.wrapper.network.packet.out;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 21:50
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.network.protocol.packet.Packet;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketRC;
import de.rafadev.glowcloud.wrapper.server.classes.CloudBukkitServer;
import de.rafadev.glowcloud.wrapper.server.classes.CloudProxyServer;

import javax.lang.model.element.Modifier;
import java.util.UUID;

public class PacketOutUnRegisterServer extends Packet {

    public PacketOutUnRegisterServer(CloudServer cloudServer) {
        super(PacketRC.MAIN + 12, UUID.randomUUID(), new Document().append("cloudServer", cloudServer.toSimple()));
    }

}
