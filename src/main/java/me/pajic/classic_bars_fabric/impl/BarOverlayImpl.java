package me.pajic.classic_bars_fabric.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pajic.classic_bars_fabric.gui.GuiHandler;
import me.pajic.classic_bars_fabric.Main;
import me.pajic.classic_bars_fabric.api.BarOverlay;
import me.pajic.classic_bars_fabric.config.ModConfig;
import me.pajic.classic_bars_fabric.util.Color;
import me.pajic.classic_bars_fabric.util.HealthEffect;
import me.pajic.classic_bars_fabric.util.ModUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public abstract class BarOverlayImpl implements BarOverlay {

    //maximum width the bar can be
    public static final int WIDTH = 77;
    public static final int HEIGHT = 5;
    public static final int BAR_U = 2;
    public static final int BAR_V = 11;
    public static final ResourceLocation ICON_BAR = ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/gui/health.png");

    public static final ResourceLocation GUI_ICONS_LOCATION = ResourceLocation.withDefaultNamespace("textures/gui/icons.png");
    protected String name;
    protected boolean side;

    public BarOverlayImpl(String name) {
        this.name = name;
    }

    public boolean shouldRender(Player player) {
        return true;
    }

    @Override
    public final boolean rightHandSide() {
        return side;
    }

    @Override
    public final BarOverlay setSide(boolean right) {
        side = right;
        return this;
    }

    @Override
    public void render(GuiGraphics graphics, DeltaTracker deltaTracker, Player player, int screenWidth, int screenHeight, int vOffset) {
        if (shouldRender(player)) {
            setupOverlayRenderState(true, false);
            bindBarTexture();
            renderBar(graphics, deltaTracker, player, screenWidth, screenHeight, vOffset);
            if (shouldRenderText()) {
                renderText(graphics, player, screenWidth, screenHeight, vOffset);
            }
            if (ModConfig.displayIcons) {
                bindIconTexture();
                renderIcon(graphics, player, screenWidth, screenHeight, vOffset);
            }
            GuiHandler.increment(rightHandSide(), 10);
        }
    }

    public void setupOverlayRenderState(boolean blend, boolean depthTest) {
        if (blend) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        } else {
            RenderSystem.disableBlend();
        }

        if (depthTest) {
            RenderSystem.enableDepthTest();
        } else {
            RenderSystem.disableDepthTest();
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
    }

    public abstract void renderBar(GuiGraphics graphics, DeltaTracker deltaTracker, Player player, int screenWidth, int screenHeight, int vOffset);

    protected boolean shouldFlash(Player player) {
        return false;
    }

    public abstract boolean shouldRenderText();

    public abstract void renderText(GuiGraphics graphics, Player player, int width, int height, int vOffset);

    public abstract void renderIcon(GuiGraphics graphics, Player player, int width, int height, int vOffset);

    public int getHOffset() {
        return rightHandSide() ? 10 : -91;
    }

    public int getIconOffset() {
        return rightHandSide() ? 92 : -101;
    }

    protected HealthEffect getHealthEffect(Player player) {
        HealthEffect effects = HealthEffect.NONE;//16
        if (player.hasEffect(MobEffects.POISON)) effects = HealthEffect.POISON;//evaluates to 52
        else if (player.hasEffect(MobEffects.WITHER)) effects = HealthEffect.WITHER;//evaluates to 88
        else if (player.isFullyFrozen()) effects = HealthEffect.FROZEN;
        return effects;
    }

    public void renderBarBackground(GuiGraphics graphics, Player player, int screenWidth, int screenHeight, int vOffset) {
        double barWidth = getBarWidth(player);
        int xStart = screenWidth / 2 + getHOffset();
        if (isFitted() && rightHandSide()) {
            xStart += (int) (WIDTH - barWidth);
        }
        int yStart = screenHeight - vOffset;

        if (isFitted()) {
            drawScaledBarBackground(graphics, barWidth, xStart, yStart + 1);
        } else renderFullBarBackground(graphics, xStart, yStart);
    }
    public void drawScaledBarBackground(GuiGraphics stack, double barWidth, int x, int y) {
        if (rightHandSide()) {
            ModUtils.drawTexturedModalRect(stack,x, y - 1, 0, 0, barWidth + 2, 9);
            ModUtils.drawTexturedModalRect(stack,x + barWidth + 2, y-1, WIDTH + 2, 0, 2, 9);
        } else {
            ModUtils.drawTexturedModalRect(stack,x, y - 1, 0, 0, (int) (barWidth + 2), 9);
            ModUtils.drawTexturedModalRect(stack, (int) (x + barWidth + 2), y - 1, WIDTH + 2, 0, 2, 9);
        }
    }
    public void textHelper(GuiGraphics graphics,int xStart,int yStart,double stat, int color) {
        int i1 = (int) Math.floor(stat);
        int i2 = ModConfig.displayIcons ? 1 : 0;

        if (rightHandSide()) {
            ModUtils.drawStringOnHUD(graphics, i1 + "", xStart + 9 * i2, yStart - 1, color);
        } else {
            int i3 = ModUtils.getStringLength(i1 + "");
            ModUtils.drawStringOnHUD(graphics, i1 + "", xStart - 9 * i2 - i3 + 5, yStart - 1, color);
        }
    }
    public void renderFullBarBackground(GuiGraphics matrices, int xStart, int yStart) {
        ModUtils.drawTexturedModalRect(matrices, xStart, yStart, 0, 0, WIDTH + 4, 9);
    }
    public void renderFullBar(GuiGraphics matrices, int xStart, int yStart) {
        renderPartialBar(matrices,xStart,yStart,WIDTH);
    }
    public void renderPartialBar(GuiGraphics matrices, double xStart, int yStart,double barWidth) {
        ModUtils.drawTexturedModalRect(matrices, xStart, yStart, BAR_U, BAR_V, barWidth, HEIGHT);
    }
    @Override
    public Color getPrimaryBarColor(int index, Player player) {
        return Color.BLACK;
    }
    @Override
    public Color getSecondaryBarColor(int index, Player player) {
        return Color.BLACK;
    }
    @Override
    public boolean isFitted() {
        return false;
    }
    @Override
    public final String name() {
        return name;
    }
}
