package de.secretcraft.scclient;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import de.secretcraft.scclient.event.EventManager;
import de.secretcraft.scclient.gui.OverlayManager;

@Mod(modid = ModSC.MODID, version = ModSC.VERSION)
public class ModSC {
	public static final String MODID = "secretcraftclientmod";
    public static final String VERSION = "0.1";
    
	public static Minecraft mc = Minecraft.getMinecraft();
	public static final Config cfg = new Config(getModSCDir() + "/config.cfg");
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	mc = Minecraft.getMinecraft();
		File dir = new File(getModSCDir() + "/log");
		dir.mkdirs();
		EventManager.init();
		
		// ensure, that there is an instance of the OverlayManager so that the config reload will take effect.
		OverlayManager.getInstance();
    	
		cfg.reload();
    }

	static public String getModSCDir() {
		return getMCDir() + "/SecretcraftMod";
	}
	
	static public String getMCDir() {
		return mc.mcDataDir.getPath();
	}

	public static void addChatMessage(String string) {
		mc.thePlayer.addChatMessage(new ChatComponentText(string));
	}
}
