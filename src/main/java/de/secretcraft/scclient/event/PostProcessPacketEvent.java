package de.secretcraft.scclient.event;

import net.minecraft.network.Packet;

public class PostProcessPacketEvent extends BaseEvent {

	private final Packet packet;
	private final boolean isAsync;
	
	public PostProcessPacketEvent(Packet packet, boolean isAsync) {
		this.packet = packet;
		this.isAsync = isAsync;
	}
	
	public Packet getPacket() {
		return packet;
	}
	
	public boolean isAsync() {
		return isAsync;
	}
}
