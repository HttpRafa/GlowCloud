package de.rafadev.glowcloud.master.wrapper.classes;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 13:22
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.network.protocol.packet.PacketSender;

public class CloudWrapper extends PacketSender {

    private String id;
    private String host;
    private int heap;

    public CloudWrapper(String id, String host, int heap) {
        this.id = id;
        this.host = host;
        this.heap = heap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getHeap() {
        return heap;
    }

    public void setHeap(int heap) {
        this.heap = heap;
    }
}
