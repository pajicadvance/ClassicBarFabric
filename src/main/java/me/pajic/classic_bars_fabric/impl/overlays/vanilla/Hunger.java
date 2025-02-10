package me.pajic.classic_bars_fabric.impl.overlays.vanilla;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pajic.classic_bars_fabric.config.ModConfig;
import me.pajic.classic_bars_fabric.impl.BarOverlayImpl;
import me.pajic.classic_bars_fabric.util.Color;
import me.pajic.classic_bars_fabric.util.ColorUtils;
import me.pajic.classic_bars_fabric.util.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

public class Hunger extends BarOverlayImpl {

  public Hunger() {
    super("food");
  }

  @Override
  public void renderBar(GuiGraphics matrices, Player player, int screenWidth, int screenHeight, int vOffset) {
    double hunger = player.getFoodData().getFoodLevel();
    double maxHunger = 20;//HungerHelper.getMaxHunger(player);
    
    double barWidthH = getBarWidth(player);
    
    double currentSat = player.getFoodData().getSaturationLevel();
    double maxSat = maxHunger;
    double barWidthS = getSatBarWidth(player);
    float exhaustion = player.getFoodData().getExhaustionLevel();

    int xStart = screenWidth / 2 + getHOffset();
    int yStart = screenHeight - vOffset;

    //Bar background
    Color.reset();
    renderFullBarBackground(matrices,xStart,yStart);
    //draw portion of bar based on hunger amount
    double f = xStart + (rightHandSide() ? BarOverlayImpl.WIDTH - barWidthH : 0);

    Color hungerColor = getSecondaryBarColor(0,player);
    Color satColor = getPrimaryBarColor(0,player);

    hungerColor.color2Gl();
    renderPartialBar(matrices,f + 2, yStart + 2,  barWidthH);
    if (currentSat > 0 && ModConfig.showSaturationBar) {
      //draw saturation
      satColor.color2Gl();
      f = xStart + (rightHandSide() ? BarOverlayImpl.WIDTH - barWidthS : 0);
      renderPartialBar(matrices,f + 2, yStart + 2, barWidthS);
    }
    //render held hunger overlay
    if (ModConfig.showHeldFoodOverlay &&
            player.getMainHandItem().has(DataComponents.FOOD)) {
      ItemStack stack = player.getMainHandItem();
      double time = System.currentTimeMillis()/1000d * ModConfig.transitionSpeed;
      double foodAlpha = Math.sin(time)/2 + .5;

      FoodProperties food = stack.get(DataComponents.FOOD);
      double hungerOverlay = food.nutrition();
      double potentialSat = food.saturation();

      //Draw Potential hunger
      double hungerWidth = Math.min(maxHunger - hunger, hungerOverlay);
      //don't render the bar at all if hunger is full
      if (hunger < maxHunger) {
        double w = ModUtils.getWidth(hungerWidth + hunger, maxHunger);

        f = xStart + (rightHandSide() ? BarOverlayImpl.WIDTH - w : 0);
        hungerColor.color2Gla((float)foodAlpha);
        renderPartialBar(matrices,f + 2, yStart + 2, w);
      }

      //Draw Potential saturation
      if (ModConfig.showSaturationBar) {
        //maximum potential saturation cannot combine with current saturation to go over 20
        double saturationWidth = Math.min(potentialSat, maxSat - currentSat);

        //Potential Saturation cannot go over potential hunger + current hunger combined
        saturationWidth = Math.min(saturationWidth, hunger + hungerWidth);
        saturationWidth = Math.min(saturationWidth, hungerOverlay + hunger);
        if ((potentialSat + currentSat) > (hunger + hungerWidth)) {
          double diff = (potentialSat + currentSat) - (hunger + hungerWidth);
          saturationWidth = potentialSat - diff;
        }

        double w = ModUtils.getWidth(saturationWidth + currentSat, maxSat);

        //offset used to decide where to place the bar
        f = xStart + (rightHandSide() ? BarOverlayImpl.WIDTH - w : 0);
        satColor.color2Gla((float)foodAlpha);
        if (true)//currentSat > 0)
          renderPartialBar(matrices,f + 2, yStart + 2,w);
        else ;//drawTexturedModalRect(f, yStart+1, 1, 10, getWidthfloor(saturationWidth,20), 7);

      }
    }

    if (ModConfig.showExhaustionOverlay /*&& Message.presentOnServer*/) {
      exhaustion = Math.min(exhaustion, 4);
      f = xStart + (rightHandSide() ? BarOverlayImpl.WIDTH - ModUtils.getWidth(exhaustion, 4) : 0);
      //draw exhaustion
      RenderSystem.setShaderColor(1, 1, 1, .25f);
      ModUtils.drawTexturedModalRect(matrices,f + 2, yStart + 1, 1, 28, ModUtils.getWidth(exhaustion, 4f), 9);
      RenderSystem.setShaderColor(1, 1, 1, 1);
    }
    Color.reset();
  }

  @Override
  public boolean shouldRenderText() {
    return ModConfig.hungerText;
  }

  @Override
  public ResourceLocation getIconRL() {
    return GUI_ICONS_LOCATION;
  }

  @Override
  public double getBarWidth(Player player) {
    double hunger = player.getFoodData().getFoodLevel();
    double maxHunger = 20;
    return Math.ceil(BarOverlayImpl.WIDTH * hunger / maxHunger);
  }
  
  public int getSatBarWidth(Player player) {
    double saturation = player.getFoodData().getSaturationLevel();
    double maxSat = 20;
    return (int) Math.ceil(BarOverlayImpl.WIDTH * saturation / maxSat);
  }
  //saturation
  @Override
  public Color getPrimaryBarColor(int index, Player player) {
    boolean hunger = player.hasEffect(MobEffects.HUNGER);
    return hunger ? ColorUtils.awt2Color(ModConfig.saturationBarDebuffColor) : ColorUtils.awt2Color(ModConfig.saturationBarColor);
  }

  //hunger
  @Override
  public Color getSecondaryBarColor(int index, Player player) {
    boolean hunger = player.hasEffect(MobEffects.HUNGER);
    return hunger ? ColorUtils.awt2Color(ModConfig.hungerBarDebuffColor) : ColorUtils.awt2Color(ModConfig.hungerBarColor);
  }

  @Override
  public void renderText(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
    int xStart = width / 2 + getIconOffset();
    int yStart = height - vOffset;
    //draw hunger amount
    double hunger = player.getFoodData().getFoodLevel();
    int c = getSecondaryBarColor(0,player).colorToText();
    textHelper(graphics,xStart,yStart,hunger,c);
  }

  @Override
  public void renderIcon(GuiGraphics graphics, Player player, int width, int height, int vOffset) {

    int xStart = width / 2 + getIconOffset();
    int yStart = height - vOffset;
    boolean hungerActive = player.hasEffect(MobEffects.HUNGER);
    ResourceLocation rl = hungerActive ? ResourceLocation.withDefaultNamespace("hud/food_full_hunger") : ResourceLocation.withDefaultNamespace("hud/food_full");
    //Draw hunger icon
    //hunger background
    //ModUtils.drawTexturedModalRect(graphics,xStart, yStart, k6, 27, 9, 9);
    graphics.blitSprite(ResourceLocation.withDefaultNamespace("hud/food_empty"), xStart, yStart, 9, 9);
    //hunger
    //ModUtils.drawTexturedModalRect(graphics,xStart, yStart, k5, 27, 9, 9);
    graphics.blitSprite(rl, xStart, yStart, 9, 9);
  }
}