package de.rafadev.glowcloud.lib.network.protocol.packet;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 09:50
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.document.Document;

public abstract class PacketInHandler {

    public abstract void handle(Document document, PacketSender packetSender);

}
