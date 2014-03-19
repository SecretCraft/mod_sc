package de.secretcraft.scclient.event;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class SendChatMessageEvent extends DisableEvent {
	private C01PacketChatMessage packet;
	private boolean isModified = false;
	
	public SendChatMessageEvent(C01PacketChatMessage packet) {
		this.packet = packet;
	}
	
	public String getMessage() {
		return packet.func_149439_c();
	}
	
	public void setMessage(String msg) {
		packet = new C01PacketChatMessage(msg);
		isModified = true;
	}
	
	public boolean isModified() {
		return isModified;
	}
	
	public Packet getPacket() {
		return packet;
	}
}
