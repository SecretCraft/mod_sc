package de.secretcraft.scclient.gui;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public abstract class BasicGuiScreen extends GuiScreen {

	protected int guiTop;

	public BasicGuiScreen() {

	}
	
	public void drawString(String str, int posX, int posY) {
		drawString(str, posX, posY, 16777215);
	}
	
	public void drawString(String str, int posX, int posY, int color) {
		drawString(fontRendererObj, str, posX, posY, color);
	}
	
	protected void drawTooltip(List<String> list, int x, int y)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        if (!list.isEmpty())
        {
            int width = 0;
            int posX;
            int posY;

            for (posX = 0; posX < list.size(); ++posX)
            {
                posY = this.fontRendererObj.getStringWidth(list.get(posX));

                if (posY > width)
                {
                    width = posY;
                }
            }

            posX = x + 3;
            posY = y - 7;
            int height = 8;

            if (list.size() > 1)
            {
                height += 2 + (list.size() - 1) * 10;
            }

            if (this.guiTop + posY + height + 6 > this.height)
            {
                posY = this.height - height - this.guiTop - 6;
            }

            this.zLevel = 300.0F;
            int var10 = -267386864;
            this.drawGradientRect(posX - 3, posY - 4, posX + width + 3, posY - 3, var10, var10);
            this.drawGradientRect(posX - 3, posY + height + 3, posX + width + 3, posY + height + 4, var10, var10);
            this.drawGradientRect(posX - 3, posY - 3, posX + width + 3, posY + height + 3, var10, var10);
            this.drawGradientRect(posX - 4, posY - 3, posX - 3, posY + height + 3, var10, var10);
            this.drawGradientRect(posX + width + 3, posY - 3, posX + width + 4, posY + height + 3, var10, var10);
            int var11 = 1347420415;
            int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
            this.drawGradientRect(posX - 3, posY - 3 + 1, posX - 3 + 1, posY + height + 3 - 1, var11, var12);
            this.drawGradientRect(posX + width + 2, posY - 3 + 1, posX + width + 3, posY + height + 3 - 1, var11, var12);
            this.drawGradientRect(posX - 3, posY - 3, posX + width + 3, posY - 3 + 1, var11, var11);
            this.drawGradientRect(posX - 3, posY + height + 2, posX + width + 3, posY + height + 3, var12, var12);

            for (int var13 = 0; var13 < list.size(); ++var13)
            {
                String var14 = (String)list.get(var13);

                this.fontRendererObj.drawStringWithShadow(var14, posX, posY, -1);

                if (var13 == 0)
                {
                    posY += 2;
                }

                posY += 10;
            }

            this.zLevel = 0.0F;
        }
    }
}
