package me.pajic.classic_bars_fabric.impl.overlays.vanilla;

import me.pajic.classic_bars_fabric.config.ModConfig;
import me.pajic.classic_bars_fabric.impl.BarOverlayImpl;
import me.pajic.classic_bars_fabric.util.Color;
import me.pajic.classic_bars_fabric.util.ColorUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class Air extends BarOverlayImpl {

  public Air() {
    super("air");
  }

  @Override
  public boolean shouldRender(Player player) {
    return player.getAirSupply() < player.getMaxAirSupply();
  }
  @Override
  public void renderBar(GuiGraphics graphics, DeltaTracker deltaTracker, Player player, int screenWidth, int screenHeight, int vOffset) {
    int xStart = screenWidth / 2 + getHOffset();
    int yStart = screenHeight - vOffset;
    double barWidth = getBarWidth(player);
    Color.reset();
    //Bar background
    renderFullBarBackground(graphics,xStart, yStart);
    //draw portion of bar based on air amount
    double f = xStart + (rightHandSide() ? BarOverlayImpl.WIDTH - barWidth : 0);
    Color color = getPrimaryBarColor(0,player);
    color.color2Gl();
    renderPartialBar(graphics,f + 2, yStart + 2,barWidth);
  }

  @Override
  public boolean shouldRenderText() {
    return ModConfig.airText;
  }

  @Override
  public ResourceLocation getIconRL() {
    return GUI_ICONS_LOCATION;
  }

  @Override
  public double getBarWidth(Player player) {
    int air = player.getAirSupply();
    int maxAir = player.getMaxAirSupply();
    return Math.ceil((double) BarOverlayImpl.WIDTH * air/ maxAir);
  }
  @Override
  public Color getPrimaryBarColor(int index, Player player) {
    return ColorUtils.awt2Color(ModConfig.airBarColor);
  }
  @Override
  public void renderText(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
    //draw air amount
    int air = player.getAirSupply();
    int xStart = width / 2 + getIconOffset();
    int yStart = height - vOffset;
    Color color = getPrimaryBarColor(0,player);
    textHelper(graphics,xStart,yStart, (double) air /20,color.colorToText());
  }
  @Override
  public void renderIcon(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
    int xStart = width / 2 + getIconOffset();
    int yStart = height - vOffset;
    //Draw air icon
    //ModUtils.drawTexturedModalRect(graphics,xStart, yStart, 16, 18, 9, 9);
    graphics.blitSprite(ResourceLocation.withDefaultNamespace("hud/air"), xStart, yStart, 9, 9);
  }
}