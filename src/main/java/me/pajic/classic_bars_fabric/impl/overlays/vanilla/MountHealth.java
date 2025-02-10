package me.pajic.classic_bars_fabric.impl.overlays.vanilla;

import me.pajic.classic_bars_fabric.config.ModConfig;
import me.pajic.classic_bars_fabric.impl.BarOverlayImpl;
import me.pajic.classic_bars_fabric.util.Color;
import me.pajic.classic_bars_fabric.util.ColorUtils;
import me.pajic.classic_bars_fabric.util.HealthEffect;
import me.pajic.classic_bars_fabric.util.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MountHealth extends BarOverlayImpl {

  private long healthUpdateCounter = 0;

  private double mountHealth = 0;

  public MountHealth() {
    super("health_mount");
  }

  @Override
  public boolean shouldRender(Player player) {
    return player.getVehicle() instanceof LivingEntity;
  }

  @Override
  public void renderBar(GuiGraphics graphics, Player player, int screenWidth, int screenHeight, int vOffset) {
    //Push to avoid lasting changes
    int updateCounter = ModUtils.mc.gui.getGuiTicks();

    LivingEntity mount = (LivingEntity) player.getVehicle();
    if (!mount.isAlive()) return;
    double mountHealth = mount.getHealth();
    double barWidth = getBarWidth(player);

    boolean highlight = healthUpdateCounter > (long) updateCounter && (healthUpdateCounter - (long) updateCounter) / 3L % 2L == 1L;

    if (mountHealth < this.mountHealth && player.invulnerableTime > 0) {
      healthUpdateCounter = updateCounter + 20;
    } else if (mountHealth > this.mountHealth && player.invulnerableTime > 0) {
      healthUpdateCounter = updateCounter + 10;
    }

    this.mountHealth = mountHealth;
    int xStart = screenWidth / 2 + getHOffset();
    int yStart = screenHeight - vOffset;
    double maxHealth = mount.getMaxHealth();
    int i4 = (highlight) ? 18 : 0;
    //Bar background
    ModUtils.drawTexturedModalRect(graphics,xStart, yStart, 0, i4, 81, 9);
    //is the bar changing
    //Pass 1, draw bar portion
    //calculate bar color
    ColorUtils.calculateScaledColor(mountHealth, maxHealth, HealthEffect.NONE).color2Gl();
    double f = xStart + (rightHandSide() ? BarOverlayImpl.WIDTH - barWidth : 0);
    //draw portion of bar based on mountHealth remaining
    renderPartialBar(graphics,f + 2, yStart + 2, barWidth);
    Color.reset();
  }

  @Override
  public boolean shouldRenderText() {
    return ModConfig.mountHealthText;
  }

  @Override
  public ResourceLocation getIconRL() {
    return GUI_ICONS_LOCATION;
  }

  @Override
  public double getBarWidth(Player player) {
    LivingEntity mount = (LivingEntity) player.getVehicle();
    double mounthHealth = mount.getHealth();
    double maxHealth = mount.getMaxHealth();
    return (int) Math.ceil(BarOverlayImpl.WIDTH * Math.min(maxHealth,mounthHealth) / mounthHealth);
  }

  @Override
  public void renderText(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
    int xStart = width / 2 + getIconOffset();
    int yStart = height - vOffset;
    LivingEntity mount = (LivingEntity) player.getVehicle();
    double maxHealth = mount.getMaxHealth();
    textHelper(graphics,xStart,yStart,mountHealth, ColorUtils.calculateScaledColor(mountHealth, maxHealth, HealthEffect.NONE).colorToText());
  }

  @Override
  public void renderIcon(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
    int xStart = width / 2 + getIconOffset();
    int yStart = height - vOffset;
    //heart background
    //ModUtils.drawTexturedModalRect(graphics,xStart, yStart, 16, 0, 9, 9);
    //heart
    //ModUtils.drawTexturedModalRect(graphics,xStart, yStart, 88, 9, 9, 9);
    graphics.blitSprite(ResourceLocation.withDefaultNamespace("hud/vehicle_full"), xStart, yStart, 9, 9);
  }
}