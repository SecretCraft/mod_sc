package de.secretcraft.scclient;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import de.secretcraft.scclient.event.EventListener;
import de.secretcraft.scclient.event.LoadConfigEvent;

public class FastSign implements EventListener {
	private boolean isEnabled = true;
	private Item boundItem = Items.stone_shovel;
	private long lastToggle = 0;
	private long lastPlace = 0;
	private String[] lastText = new String[]{"Line", "One", "Two", "Three"};
	private boolean sendFastText = false;
	private boolean isWaiting = false;
	private BlockWrapper targetSign = null;
	private Minecraft mc;
	
	public FastSign() {
		mc = Minecraft.getMinecraft();
	}
	
	public void onLoadConfig(LoadConfigEvent event) {
		Config cfg = event.config;
		cfg.setDefault("FastSign.isEnabled", true);
		cfg.setDefault("FastSign.toggleWithItem", "stone_shovel");
		
		isEnabled = cfg.getBoolean("FastSign.isEnabled");
		boundItem = (Item)Item.itemRegistry.getObject(cfg.getString("FastSign.toggleWithItem"));
	}
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent event) {
		if(event.entityPlayer.getHeldItem() == null || !event.entityPlayer.getHeldItem().getItem().equals(boundItem)
				|| (System.currentTimeMillis() - lastToggle) < 100) {
			return;
		}
		lastToggle = System.currentTimeMillis();
		isEnabled = !isEnabled;
		event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onRightClickWithSign(PlayerInteractEvent event) {
		if(event.entityPlayer.getHeldItem() == null || !event.entityPlayer.getHeldItem().getItem().equals(Items.sign)
				|| (System.currentTimeMillis() - lastPlace ) < 20 || !isEnabled) {
			return;
		}
		
		BlockWrapper bw = new BlockWrapper(event.x, event.y, event.z);
		
		if(mc.thePlayer.isSneaking() && !bw.isOneOf(Blocks.chest, Blocks.trapped_chest)) {
			sendFastText = true;
		} else {
			sendFastText = false;
		}
		
		targetSign = bw.getRelative(event.face);
		isWaiting = true;
		
		lastPlace = System.currentTimeMillis();
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if(!isWaiting || !isEnabled || mc.currentScreen == null || targetSign == null || targetSign.getSign() == null)
			return;
		
		if(mc.currentScreen instanceof GuiEditSign) {
			GuiEditSign gui = (GuiEditSign)mc.currentScreen;
			TileEntitySign sign = targetSign.getSign();
			for(int i = 0; i < 4; ++i)
				sign.signText[i] = lastText[i];
			if(sendFastText) {
				mc.displayGuiScreen(null);
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
			} else {
				
			}
			isWaiting = false;
		}
	}

	@Override
	public boolean isDestroyed() {
		return false;
	}
}
