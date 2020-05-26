package de.rafadev.glowcloud.lib.network.protocol.packet;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 09:49
// In the project GlowCloud
//
//------------------------------

import java.util.LinkedList;
import java.util.List;

public class PacketManager {

    private List<PacketHandlerElement> handlers = new LinkedList<>();

    public void registerHandler(long id, PacketInHandler handler) {
        PacketHandlerElement packetHandlerElement = new PacketHandlerElement(id, handler);
        handlers.add(packetHandlerElement);
    }

}
