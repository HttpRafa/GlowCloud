package de.rafadev.glowcloud.lib.network.protocol.packet.request;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.07.2020 at 11:02
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.document.Document;

import java.util.UUID;

public class Result {

    private UUID uniqueId;
    private long time;
    private Document data;

    public Result(UUID uniqueId, long time, Document data) {
        this.uniqueId = uniqueId;
        this.time = time;
        this.data = data;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Document getData() {
        return data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
