package de.secretcraft.scclient.event;

import net.minecraft.network.Packet;

public class PreSendPacketEvent extends DisableEvent {
	private final Packet packet;
	
	public PreSendPacketEvent(Packet packet) {
		this.packet = packet;
	}
	
	public Packet getPacket() {
		return packet;
	}
}
