package me.pajic.classic_bars_fabric.api;

import me.pajic.classic_bars_fabric.impl.BarOverlayImpl;
import me.pajic.classic_bars_fabric.util.Color;
import me.pajic.classic_bars_fabric.util.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public interface BarOverlay {

    boolean rightHandSide();
    BarOverlay setSide(boolean right);

    void render(GuiGraphics graphics, Player player, int screenWidth, int screenHeight, int vOffset);

    ResourceLocation getIconRL();
    default void bindIconTexture() {
        ModUtils.CURRENT_TEXTURE = getIconRL();
    }

    default void bindBarTexture() {
        ModUtils.CURRENT_TEXTURE = BarOverlayImpl.ICON_BAR;
    }

    double getBarWidth(Player player);

    Color getPrimaryBarColor(int index, Player player);

    Color getSecondaryBarColor(int index,Player player);

    boolean isFitted();

    String name();
}