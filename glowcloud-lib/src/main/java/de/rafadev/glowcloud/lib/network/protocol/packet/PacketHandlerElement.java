package de.rafadev.glowcloud.lib.network.protocol.packet;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 10:01
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;

public class PacketHandlerElement implements IGlowCloudObject {

    private long id;
    private PacketInHandler handler;

    public PacketHandlerElement(long id, PacketInHandler handler) {
        this.id = id;
        this.handler = handler;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PacketInHandler getHandler() {
        return handler;
    }

    public void setHandler(PacketInHandler handler) {
        this.handler = handler;
    }
}
