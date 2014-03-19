package de.secretcraft.scclient.event;

import net.minecraft.network.play.client.C01PacketChatMessage;

public class EventSpecifier implements EventListener {
	public EventSpecifier() {
		
	}
	
	public void onPrePacketSendEvent(PreSendPacketEvent event) {
		if(event.getPacket() instanceof C01PacketChatMessage) {
			String msg = ((C01PacketChatMessage)event.getPacket()).func_149439_c();
			SendChatMessageEvent e = new SendChatMessageEvent((C01PacketChatMessage)event.getPacket());
			EventManager.fireEvent(e);
			if(e.isDisabled()) {
				event.setDisabled(true);
			} else if(e.isModified()) {
				event.setDisabled(true);
				EventManager.sendPacketWithoutEvent(e.getPacket());
			}
		}
	}
	
	public void onSendChatMessage(SendChatMessageEvent event) {
		if(event.getMessage().startsWith("/")) {
			System.out.println("Send Command?: " + event.getMessage());
			CommandEvent e = new CommandEvent(event.getMessage().substring(1).split(" "));
			EventManager.fireEvent(e);
			event.setDisabled(e.isDisabled());
		}
	}

	@Override
	public boolean isDestroyed() {
		return false;
	}
}
