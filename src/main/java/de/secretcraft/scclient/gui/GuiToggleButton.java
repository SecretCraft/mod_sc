package de.secretcraft.scclient.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

public class GuiToggleButton extends GuiButton {
	private boolean state = true;
	private String baseString;
	private boolean isColored = true;

	public GuiToggleButton(int par1, int par2, int par3, String par4Str) {
		super(par1, par2, par3, par4Str);
		this.setDisplayString(par4Str);
	}

	public GuiToggleButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
		this.setDisplayString(par6Str);
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
		this.setDisplayString(baseString);
	}

	public boolean isColored() {
		return isColored;
	}

	public void setColored(boolean isColored) {
		this.isColored = isColored;
		this.setDisplayString(baseString);
	}
	
	public void setDisplayString(String str) {
		baseString = str;
		displayString = (isColored ? (state ? EnumChatFormatting.DARK_GREEN
				: EnumChatFormatting.DARK_RED) : (state ? "" : EnumChatFormatting.STRIKETHROUGH))
				+ baseString;
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int par2, int par3) {
		if(super.mousePressed(mc, par2, par3)) {
			this.setState(!this.getState());
			return true;
		}
		return false;
	}
}
