package de.secretcraft.scclient.gui.overlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

import de.secretcraft.scclient.Config;
import de.secretcraft.scclient.event.EventListener;
import de.secretcraft.scclient.event.EventManager;
import de.secretcraft.scclient.event.LoadConfigEvent;
import de.secretcraft.scclient.gui.BasicGuiOverlay;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class EntityChunkCount extends BasicGuiOverlay implements EventListener {
	private ArrayList<String> displayText = new ArrayList();
	public static final Map<String,Integer> result = new TreeMap<String,Integer>();
	private Item boundItem = Items.golden_shovel;
	private long lastToggle = 0;
	
	public EntityChunkCount() {
		EventManager.registerEvents(this);
	}
	
	public void onLoadConfig(LoadConfigEvent event) {
		Config cfg = event.config;
		cfg.setDefault("EntityChunkCount.isVisible", true);
		cfg.setDefault("EntityChunkCount.toggleWithItem", "golden_shovel");
		
		setVisible(cfg.getBoolean("EntityChunkCount.isVisible"));
		boundItem = (Item)Item.itemRegistry.getObject(cfg.getString("EntityChunkCount.toggleWithItem"));
	}
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent event) {
		if(event.entityPlayer.getHeldItem() == null || !event.entityPlayer.getHeldItem().getItem().equals(boundItem)
				|| (System.currentTimeMillis() - lastToggle) < 100) {
			return;
		}
		lastToggle = System.currentTimeMillis();
		setVisible(!isVisible);
		event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		displayText.clear();
		result.clear();
		
		Minecraft mc = Minecraft.getMinecraft();
		
		if(mc.theWorld == null || !isVisible) {
			return;
		}
		
		Chunk c = mc.theWorld.getChunkFromChunkCoords(mc.thePlayer.chunkCoordX, mc.thePlayer.chunkCoordZ);
		int entityCount = 0;
		for(List l : c.entityLists) { for(Entity e : (List<Entity>)l) {
			addOne(result, (e instanceof EntityPlayer) ? "Player" : EntityList.getEntityString(e));
			++entityCount;
		}}
		displayText.add("Chunk(" + c.xPosition + "," + c.zPosition + ") has "
				+ entityCount + ((entityCount == 1) ? " entity" : " entities"));
		for(Entry<String, Integer> e : result.entrySet()) {
			displayText.add(e.getKey() + ": " + e.getValue());
		}
		
		width = 0;
		for(String s : displayText) {
			width = Math.max(width, fontRenderer.getStringWidth(s));
		}
		width += 8;
		height = (fontRenderer.FONT_HEIGHT + 3) * displayText.size() + 3;
	}
    
	private static void addOne(Map<String, Integer> map, String entity) {
		Integer val = map.get(entity);
		map.put(entity, val == null ? 1 : 1 + val);
	}

	@Override
	public String getName() {
		return "Entity Chunk Count";
	}

	@Override
	public void draw() {
		for(int i = 0; i < displayText.size(); ++i) {
			fontRenderer.drawString(displayText.get(i), 8, 8 + i * (fontRenderer.FONT_HEIGHT + 3), 0xFFFFFF, true);
		}
	}

	@Override
	public boolean isDestroyed() {
		return false;
	}
}
