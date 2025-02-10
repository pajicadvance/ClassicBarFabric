package me.pajic.classic_bars_fabric.impl.overlays.vanilla;

import me.pajic.classic_bars_fabric.config.ModConfig;
import me.pajic.classic_bars_fabric.impl.BarOverlayImpl;
import me.pajic.classic_bars_fabric.util.Color;
import me.pajic.classic_bars_fabric.util.ColorUtils;
import me.pajic.classic_bars_fabric.util.HealthEffect;
import me.pajic.classic_bars_fabric.util.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class Absorption extends BarOverlayImpl {

    public Absorption() {
        super("absorption");
    }

    @Override
    public boolean shouldRender(Player player) {
        return player.getAbsorptionAmount() > 0;
    }

    @Override
    public void renderBar(GuiGraphics graphics, Player player, int screenWidth, int screenHeight, int vOffset) {

        double absorb = player.getAbsorptionAmount();
        double barWidth = getBarWidth(player);

        int xStart = screenWidth / 2 + getHOffset();
        int yStart = screenHeight - vOffset;
        double maxHealth = player.getMaxHealth();

        if (rightHandSide()) {
            xStart += (int) (BarOverlayImpl.WIDTH - barWidth);
        }

        //draw absorption bar
        int index = Math.min((int) Math.ceil(absorb / maxHealth), ModConfig.absorptionColors.size()) - 1;
        Color primary = getPrimaryBarColor(index, player);
        Color.reset();
        //draw background bar
        renderBarBackground(graphics, player, screenWidth, screenHeight, vOffset);
        if (index == 0) {//no wrapping
            //background
            primary.color2Gl();
            //bar
            renderPartialBar(graphics, xStart + 2, yStart + 2, barWidth);
        } else {
            //we have wrapped, draw 2 bars
            //draw first full bar
            Color secondary = getSecondaryBarColor(index - 1, player);
            secondary.color2Gl();
            renderFullBar(graphics, xStart + 2, yStart + 2);
            //is it on the edge or capped already?
            if (absorb % maxHealth != 0 && index < ModConfig.absorptionColors.size() - 1) {
                //draw second partial bar
                primary.color2Gl();
                renderPartialBar(graphics, xStart + 2, yStart + 2, ModUtils.getWidth(absorb % maxHealth, maxHealth));
            }
        }
    }

    @Override
    public boolean shouldRenderText() {
        return ModConfig.absorptionText;
    }

    @Override
    public ResourceLocation getIconRL() {
        return GUI_ICONS_LOCATION;
    }

    public double getBarWidth(Player player) {
        double absorb = player.getAbsorptionAmount();
        double maxHealth = player.getMaxHealth();
        return (int) Math.ceil(BarOverlayImpl.WIDTH * Math.min(maxHealth, absorb) / maxHealth);
    }

    @Override
    public Color getPrimaryBarColor(int index, Player player) {
        HealthEffect effect = getHealthEffect(player);
        switch (effect) {
            case NONE -> {
                return ColorUtils.awt2Color(ModConfig.absorptionColors.get(index));
            }
            case POISON -> {
                return ColorUtils.awt2Color(ModConfig.absorptionPoisonColors.get(index));
            }
            case WITHER -> {
                return ColorUtils.awt2Color(ModConfig.absorptionWitherColors.get(index));
            }
        }
        return super.getPrimaryBarColor(index, player);
    }

    @Override
    public boolean isFitted() {
        return !ModConfig.fullAbsorptionBar;
    }

    @Override
    public void renderText(GuiGraphics graphics, Player player, int width, int height, int vOffset) {

        double absorb = player.getAbsorptionAmount();
        double maxHealth = player.getMaxHealth();
        int xStart = width / 2 + getIconOffset();
        int yStart = height - vOffset;
        // handle the text
        int index = Math.min((int) Math.ceil(absorb / maxHealth), ModConfig.absorptionColors.size()) - 1;
        Color c = getPrimaryBarColor(index, player);
        textHelper(graphics, xStart, yStart, absorb, c.colorToText());
    }

    @Override
    public void renderIcon(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
        int xStart = width / 2 + getIconOffset();
        int yStart = height - vOffset;
        boolean isHardcore = player.level().getLevelData().isHardcore();
        ResourceLocation rl = isHardcore ? ResourceLocation.withDefaultNamespace("hud/heart/absorbing_hardcore_full") : ResourceLocation.withDefaultNamespace("hud/heart/absorbing_full");
        //draw absorption icon
        //ModUtils.drawTexturedModalRect(graphics, xStart, yStart, 16, 9 * i5, 9, 9);
        //ModUtils.drawTexturedModalRect(graphics, xStart, yStart, 160, 0, 9, 9);
        graphics.blitSprite(rl, xStart, yStart, 9, 9);
    }
}