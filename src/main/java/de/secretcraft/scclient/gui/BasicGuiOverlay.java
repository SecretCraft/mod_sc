package de.secretcraft.scclient.gui;

import java.util.List;

public abstract class BasicGuiOverlay extends BasicGui {
	/*
	 * Need width/height and the position for the overlay manager to put them in the right corner.
	 */
	protected int width = 0;
	protected int height = 0;
	protected boolean isPersistent = false;
	protected boolean isVisible = false;

	public BasicGuiOverlay() {
		super();
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isPersistent() {
		return isPersistent;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public BasicGuiOverlay setVisible(boolean isVisible) {
		this.isVisible = isVisible;
		return this;
	}
	
	public abstract String getName();
}
