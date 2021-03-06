package de.rafadev.glowcloud.lib.network.protocol.packet;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 09:59
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.network.protocol.ProtocolSender;
import de.rafadev.glowcloud.lib.network.protocol.packet.Packet;

public class PacketSender extends ProtocolSender {

    public void writePacket(Packet packet) {
    }

    public void writePacket(Packet... packets) {
        for (Packet packet : packets) {
            writePacket(packet);
        }
    }

}
