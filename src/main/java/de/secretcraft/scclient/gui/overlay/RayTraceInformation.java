package de.secretcraft.scclient.gui.overlay;

import java.util.ArrayList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

import de.secretcraft.scclient.BlockWrapper;
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
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class RayTraceInformation extends BasicGuiOverlay implements EventListener {
	private ArrayList<String> displayText;
	
	public RayTraceInformation() {
		displayText = new ArrayList();
		
		EventManager.registerEvents(this);
	}
	
	public void onLoadConfig(LoadConfigEvent event) {
		Config cfg = event.config;
		cfg.setDefault("RayTraceInformation.isVisible", false);
		
		setVisible(cfg.getBoolean("RayTraceInformation.isVisible"));
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if(Minecraft.getMinecraft().theWorld == null || !isVisible) {
			return;
		}
		MovingObjectPosition rayTrace = Minecraft.getMinecraft().objectMouseOver;
		
		if(rayTrace == null) {
			return;
		}

		displayText.clear();
		if(rayTrace.typeOfHit == MovingObjectType.BLOCK) {
			BlockWrapper block = new BlockWrapper(rayTrace.blockX, rayTrace.blockY, rayTrace.blockZ);
			try {
				displayText.add("Name: " + (new ItemStack(Item.getItemFromBlock(block.getBlock()), 1, block.getMetadata())).getDisplayName());
			} catch(NullPointerException npe) {
				displayText.add("Name: " + block.getBlock().getLocalizedName());
			}
			displayText.add("NamedID: " + Block.blockRegistry.getNameForObject(block.getBlock()).replaceFirst("minecraft:", ""));
			displayText.add("Metadata: " + block.getMetadata());
			displayText.add("Position: " + block);
		} else if(rayTrace.typeOfHit == MovingObjectType.ENTITY) {
			Entity entity = rayTrace.entityHit;
			displayText.add("Name: " + EntityList.getEntityString(entity));
			if(entity instanceof EntityLiving) {
				EntityLiving e = (EntityLiving)entity;
				displayText.add("Health: " + ((int)e.getHealth()) + "/" + ((int)e.getMaxHealth()));
				displayText.add("Armor: " + e.getTotalArmorValue());
				if(entity instanceof EntityAnimal) {
					EntityAnimal animal = (EntityAnimal)entity;
					displayText.add("IsInLove: " + animal.isInLove());
				}
			}
		}
		
		width = 0;
		for(String s : displayText) {
			width = Math.max(width, fontRenderer.getStringWidth(s));
		}
		width += 8;
		height = (fontRenderer.FONT_HEIGHT + 3) * displayText.size() + 3;
	}

	@Override
	public String getName() {
		return "Ray Trace Information";
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
