package de.rafadev.glowcloud.lib.network.protocol.packet;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 09:43
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Packet implements IGlowCloudObject {

    protected long id;
    protected UUID uuid;
    protected Document document;

    public Packet(long id, UUID uuid, Document document) {
        this.id = id;
        this.uuid = uuid;
        this.document = document;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public byte[] toBytes() {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", id);
        jsonObject.addProperty("uuid", uuid.toString());
        jsonObject.add("document", document.getData());

        return new Gson().toJson(jsonObject).getBytes(StandardCharsets.UTF_8);
    }

}
