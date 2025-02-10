package me.pajic.classic_bars_fabric.util;

import me.pajic.classic_bars_fabric.impl.BarOverlayImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ModUtils {
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static ResourceLocation CURRENT_TEXTURE = BarOverlayImpl.GUI_ICONS_LOCATION;

    public static void drawTexturedModalRect(GuiGraphics stack, double x, int y, int textureX, int textureY, double width, int height) {
        stack.blit(CURRENT_TEXTURE, (int) x, y, textureX, textureY, (int) width, height);
    }

    public static double getWidth(double d1, double d2) {
        double ratio = BarOverlayImpl.WIDTH * d1 / d2;
        return Math.ceil(ratio);
    }

    public static int getStringLength(String s) {
        return fontRenderer.width(s);
    }

    public static void drawStringOnHUD(GuiGraphics stack, String string, int xOffset, int yOffset, int color) {
        xOffset += 2;
        yOffset += 2;
        stack.drawString(Minecraft.getInstance().font,string, xOffset, yOffset, color,true);
    }
}