package me.pajic.classic_bars_fabric.impl.overlays.vanilla;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pajic.classic_bars_fabric.config.ModConfig;
import me.pajic.classic_bars_fabric.impl.BarOverlayImpl;
import me.pajic.classic_bars_fabric.util.Color;
import me.pajic.classic_bars_fabric.util.ColorUtils;
import me.pajic.classic_bars_fabric.util.HealthEffect;
import me.pajic.classic_bars_fabric.util.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class Health extends BarOverlayImpl {

  private double playerHealth = 0;
  private long healthUpdateCounter = 0;
  private double lastPlayerHealth = 0;

  public Health() {
    super("health");
  }

  @Override
  public void renderBar(GuiGraphics graphics, Player player, int screenWidth, int screenHeight, int vOffset) {
    int updateCounter = ModUtils.mc.gui.getGuiTicks();

    double health = player.getHealth();
    double barWidth = getBarWidth(player);
    boolean highlight = healthUpdateCounter > (long) updateCounter && (healthUpdateCounter - (long) updateCounter) / 3 % 2 == 1;

    //player is damaged and resistant
    if (health < playerHealth && player.invulnerableTime > 0) {
      healthUpdateCounter = updateCounter + 20;
      lastPlayerHealth = playerHealth;
    } else if (health > playerHealth && player.invulnerableTime > 0) {
      healthUpdateCounter = updateCounter + 10;
      /* lastPlayerHealth = playerHealth;*/
    }
    playerHealth = health;
    double displayHealth = health + (lastPlayerHealth - health) * ((double) player.invulnerableTime / player.invulnerableDuration);

    int xStart = screenWidth / 2 + getHOffset();
    int yStart = screenHeight - vOffset;
    double maxHealth = player.getMaxHealth();

    HealthEffect effect = getHealthEffect(player);

    int i4 = (highlight) ? 18 : 0;

    Color.reset();
    //Bar background
    ModUtils.drawTexturedModalRect(graphics,xStart, yStart, 0, i4, WIDTH + 4, 9);

    double f = xStart + (rightHandSide() ? WIDTH - barWidth : 0);

    //is the bar changing
    //Pass 1, draw bar portion
    //interpolate the bar
    if (displayHealth != health) {
      //reset to white
      if (displayHealth > health) {
        Color.reset();
        //draw interpolation
        double w = ModUtils.getWidth(displayHealth, maxHealth);
        double off = rightHandSide() ? w - barWidth : 0;
        //draw interpolation
        renderPartialBar(graphics,f + 2 - off, yStart + 2,w);
        //Health is increasing, IDK what to do here
      } else {/*
                  f = xStart + getWidth(health, maxHealth);
                  drawTexturedModalRect(f, yStart + 1, 1, 10, getWidth(health - displayHealth, maxHealth), 7, general.style, true, true);*/
      }
    }
    //calculate bar color
    Color primary = getPrimaryBarColor(0,player);
    primary.color2Gl();
    //draw portion of bar based on health remaining
    renderPartialBar(graphics,f + 2, yStart + 2, barWidth);
    if (effect == HealthEffect.POISON) {
      //draw poison overlay
      RenderSystem.setShaderColor(0, .5f, 0, .5f);
      ModUtils.drawTexturedModalRect(graphics,f + 1, yStart + 1, 1, 36, barWidth, 7);
    }
    Color.reset();
  }

  @Override
  public boolean shouldRenderText() {
    return ModConfig.healthText;
  }

  @Override
  public Color getPrimaryBarColor(int index, Player player) {
    double health = player.getHealth();
    double maxHealth = player.getMaxHealth();
    HealthEffect effect = getHealthEffect(player);
    return ColorUtils.calculateScaledColor(health,maxHealth,effect);
  }

  @Override
  public ResourceLocation getIconRL() {
    return GUI_ICONS_LOCATION;
  }

  @Override
  public double getBarWidth(Player player) {
    double health = player.getHealth();
    double maxHealth = player.getMaxHealth();
    return WIDTH * health / maxHealth;
  }

  @Override
  public void renderText(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
    double health = player.getHealth();
    int xStart = width / 2 + getIconOffset();
    int yStart = height - vOffset;
    textHelper(graphics,xStart,yStart,health,getPrimaryBarColor(0,player).colorToText());
  }

  @Override
  public void renderIcon(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
    HealthEffect effect = getHealthEffect(player);

    int xStart = width / 2 + getIconOffset();
    int yStart = height - vOffset;
    boolean isHardcore = player.level().getLevelData().isHardcore();
    ResourceLocation rl = switch (effect) {
          case NONE ->
                  isHardcore ? ResourceLocation.withDefaultNamespace("hud/heart/hardcore_full") : ResourceLocation.withDefaultNamespace("hud/heart/full");
          case POISON ->
                  isHardcore ? ResourceLocation.withDefaultNamespace("hud/heart/poisoned_hardcore_full") : ResourceLocation.withDefaultNamespace("hud/heart/poisoned_full");
          case WITHER ->
                  isHardcore ? ResourceLocation.withDefaultNamespace("hud/heart/withered_hardcore_full") : ResourceLocation.withDefaultNamespace("hud/heart/withered_full");
          case FROZEN ->
                  isHardcore ? ResourceLocation.withDefaultNamespace("hud/heart/frozen_hardcore_full") : ResourceLocation.withDefaultNamespace("hud/heart/frozen_full");
    };
    //Draw health icon
    //heart background
    Color.reset();
    //ModUtils.drawTexturedModalRect(graphics,xStart, yStart, 16, 9 * i5, 9, 9);
    graphics.blitSprite(ResourceLocation.withDefaultNamespace("hud/heart/container"), xStart, yStart, 9, 9);
    //heart
    //ModUtils.drawTexturedModalRect(graphics,xStart, yStart, 36 + effect.i, 9 * i5, 9, 9);
    graphics.blitSprite(rl, xStart, yStart, 9, 9);
  }
}