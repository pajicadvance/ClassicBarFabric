package me.pajic.classic_bars_fabric.impl.overlays.mod;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pajic.classic_bars_fabric.config.ModConfig;
import me.pajic.classic_bars_fabric.impl.BarOverlayImpl;
import me.pajic.classic_bars_fabric.util.Color;
import me.pajic.classic_bars_fabric.util.ColorUtils;
import me.pajic.classic_bars_fabric.util.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import toughasnails.api.potion.TANEffects;
import toughasnails.api.thirst.IThirst;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.init.ModTags;

public class Thirst extends BarOverlayImpl {

    public static final String NAME = "thirst_level";

    public static final double MAX_THIRST_LEVEL = 20;
    public static final double MAX_HYDRATION_LEVEL = 1.0;

    /**
     * Whether {@code drink} is tagged as {@link ModTags.Items#DRINKS} should be ensured via the context.
     */
    public static int getPotentialThirstLevel(ItemStack drink) {
        if (drink.is(ModTags.Items.ONE_THIRST_DRINKS)) {
            return  1;
        }

        if (drink.is(ModTags.Items.TWO_THIRST_DRINKS)) {
            return 2;
        }

        if (drink.is(ModTags.Items.THREE_THIRST_DRINKS)) {
            return 3;
        }

        if (drink.is(ModTags.Items.FOUR_THIRST_DRINKS)) {
            return 4;
        }

        if (drink.is(ModTags.Items.FIVE_THIRST_DRINKS)) {
            return 5;
        }

        if (drink.is(ModTags.Items.SIX_THIRST_DRINKS)) {
            return 6;
        }

        if (drink.is(ModTags.Items.SEVEN_THIRST_DRINKS)) {
            return 7;
        }

        if (drink.is(ModTags.Items.EIGHT_THIRST_DRINKS)) {
            return 8;
        }

        if (drink.is(ModTags.Items.NINE_THIRST_DRINKS)) {
            return 9;
        }

        if (drink.is(ModTags.Items.TEN_THIRST_DRINKS)) {
            return 10;
        }

        if (drink.is(ModTags.Items.ELEVEN_THIRST_DRINKS)) {
            return 11;
        }

        if (drink.is(ModTags.Items.TWELVE_THIRST_DRINKS)) {
            return 12;
        }

        if (drink.is(ModTags.Items.THIRTEEN_THIRST_DRINKS)) {
            return 13;
        }

        if (drink.is(ModTags.Items.FOURTEEN_THIRST_DRINKS)) {
            return 14;
        }

        if (drink.is(ModTags.Items.FIFTEEN_THIRST_DRINKS)) {
            return 15;
        }

        if (drink.is(ModTags.Items.SIXTEEN_THIRST_DRINKS)) {
            return 16;
        }

        if (drink.is(ModTags.Items.SEVENTEEN_THIRST_DRINKS)) {
            return 17;
        }

        if (drink.is(ModTags.Items.EIGHTEEN_THIRST_DRINKS)) {
            return 18;
        }

        if (drink.is(ModTags.Items.NINETEEN_THIRST_DRINKS)) {
            return 19;
        }

        if (drink.is(ModTags.Items.TWENTY_THIRST_DRINKS)) {
            return 20;
        }
        
        return 0;
    }

    /**
     * Whether {@code drink} is tagged as {@link ModTags.Items#DRINKS} should be ensured via the context.
     */
    public static float getPotentialHydrationLevel(ItemStack drink) {
        if (drink.is(ModTags.Items.TEN_HYDRATION_DRINKS)) {
            return 0.1F;
        }

        if (drink.is(ModTags.Items.TWENTY_HYDRATION_DRINKS)) {
            return 0.2F;
        }

        if (drink.is(ModTags.Items.THIRTY_HYDRATION_DRINKS)) {
            return 0.3F;
        }

        if (drink.is(ModTags.Items.FOURTY_HYDRATION_DRINKS)) {
            return 0.4F;
        }

        if (drink.is(ModTags.Items.FIFTY_HYDRATION_DRINKS)) {
            return 0.5F;
        }

        if (drink.is(ModTags.Items.SIXTY_HYDRATION_DRINKS)) {
            return 0.6F;
        }

        if (drink.is(ModTags.Items.SEVENTY_HYDRATION_DRINKS)) {
            return 0.7F;
        }

        if (drink.is(ModTags.Items.EIGHTY_HYDRATION_DRINKS)) {
            return 0.8F;
        }

        if (drink.is(ModTags.Items.NINETY_HYDRATION_DRINKS)) {
            return 0.9F;
        }

        if (drink.is(ModTags.Items.ONE_HUNDRED_HYDRATION_DRINKS)) {
            return 1.0F;
        }
        return 0.0F;
    }

    /**
     * Whether {@code drink} is tagged as {@link ModTags.Items#DRINKS} should be ensured via the context.
     */
    public static float getPotentialPotionChance(ItemStack drink) {
        if (drink.is(ModTags.Items.TWENTY_FIVE_POISON_CHANCE_DRINKS)) {
            return 0.25F;
        }

        if (drink.is(ModTags.Items.FIFTY_POISON_CHANCE_DRINKS)) {
            return 0.5F;
        }

        if (drink.is(ModTags.Items.SEVENTY_FIVE_POISON_CHANCE_DRINKS)) {
            return 0.75F;
        }

        if (drink.is(ModTags.Items.ONE_HUNDRED_POISON_CHANCE_DRINKS)) {
            return 1.0F;
        }
        
        return 0.0F;
    }

    public Thirst() {
        super(NAME);
    }

    @Override
    public boolean shouldRender(Player player) {
        return isEnabled();
    }

    @Override
    public void renderBar(GuiGraphics graphics, Player player, int screenWidth, int screenHeight, int vOffset) {
        double maxExhaustionLevel = toughasnails.init.ModConfig.thirst.thirstExhaustionThreshold;

        IThirst thirstData = ThirstHelper.getThirst(player);
        int thirstLevel = thirstData.getThirst();
        double hydrationLevel = Math.min(thirstData.getHydration(), MAX_HYDRATION_LEVEL);
        double exhaustionLevel = Math.min(thirstData.getExhaustion(), maxExhaustionLevel);

        int xStart = screenWidth / 2 + getHOffset();
        int yStart = screenHeight - vOffset;

        Color.reset();
        renderFullBarBackground(graphics, xStart, yStart);

        drawThirst(graphics, player, xStart, yStart, thirstLevel);

        if (hydrationLevel > 0 && ModConfig.showHydrationBar) {
            drawHydration(graphics, player, xStart, yStart, hydrationLevel);
        }

        if (ModConfig.showHeldDrinkOverlay && ThirstHelper.canDrink(player, true)) {
            drawHeldDrink(graphics, player, thirstData, xStart, yStart);
        }

        if (ModConfig.showThirstExhaustionOverlay /*&& Message.presentOnServer*/) {
            drawExhaustion(graphics, player, xStart, yStart, exhaustionLevel, maxExhaustionLevel);
        }
        Color.reset();
    }

    @Override
    public boolean shouldRenderText() {
        return ModConfig.thirstText;
    }

    private void drawThirst(GuiGraphics stack, Player player, int x, int y, double thirstLevel) {
        getSecondaryBarColor(0, player).color2Gl();
        double barWidth = ModUtils.getWidth(thirstLevel, Thirst.MAX_THIRST_LEVEL);
        double barXStart = x + (rightHandSide() ? BarOverlayImpl.WIDTH - barWidth : 0);
        renderPartialBar(stack, barXStart + 2, y + 2, barWidth);
    }

    private void drawHydration(GuiGraphics stack, Player player, int x, int y, double hydrationLevel) {
        getPrimaryBarColor(0, player).color2Gl();
        double barWidth = ModUtils.getWidth(hydrationLevel, Thirst.MAX_HYDRATION_LEVEL);
        double barXStart = x + (rightHandSide() ? BarOverlayImpl.WIDTH - barWidth : 0);
        renderPartialBar(stack, barXStart + 2, y + 2, barWidth);
    }

    private void drawHeldDrink(GuiGraphics stack, Player player, IThirst thirstData, int x, int y) {
        ItemStack drink = player.getMainHandItem();
        if (!drink.is(ModTags.Items.DRINKS)) return;
        double time = System.currentTimeMillis() / 1000D * ModConfig.transitionSpeed;
        double drinkAlpha = Math.sin(time) / 2 + .5;

        int thirstLevel = thirstData.getThirst();
        int potentialThirstLevel = getPotentialThirstLevel(drink);
        double restoredThirstLevel = Math.min(Thirst.MAX_THIRST_LEVEL - thirstLevel, potentialThirstLevel);
        if (thirstLevel < Thirst.MAX_THIRST_LEVEL) {
            double barWidth = ModUtils.getWidth(thirstLevel + restoredThirstLevel, Thirst.MAX_THIRST_LEVEL);
            double barXStart = x + (rightHandSide() ? BarOverlayImpl.WIDTH - barWidth : 0);
            getSecondaryBarColor(0, player).color2Gla((float)drinkAlpha);
            renderPartialBar(stack,barXStart + 2, y + 2, barWidth);
        }

        if (ModConfig.showHydrationBar) {
            float hydrationLevel = thirstData.getHydration();
            float potentialHydrationLevel = getPotentialHydrationLevel(drink);
            double restoredHydrationLevel = Math.min(Thirst.MAX_HYDRATION_LEVEL - hydrationLevel, potentialHydrationLevel);

            // Potential hydration level cannot go over (current thirst level + potential thirst level)
            restoredHydrationLevel = Math.min(restoredHydrationLevel, thirstLevel + potentialThirstLevel);
            restoredHydrationLevel = Math.min(restoredHydrationLevel, thirstLevel + restoredThirstLevel);

            if ((hydrationLevel + potentialHydrationLevel) > (thirstLevel + restoredThirstLevel)) {
                double diff = (hydrationLevel + potentialHydrationLevel) - (thirstLevel + restoredThirstLevel);
                restoredHydrationLevel = potentialHydrationLevel - diff;
            }
            double barWidth = ModUtils.getWidth(hydrationLevel + restoredHydrationLevel, Thirst.MAX_HYDRATION_LEVEL);
            double barXStart = x + (rightHandSide() ? BarOverlayImpl.WIDTH - barWidth : 0);
            getPrimaryBarColor(0, player).color2Gla((float)drinkAlpha);
            renderPartialBar(stack,barXStart + 2, y + 2, barWidth);
        }
    }

    private void drawExhaustion(GuiGraphics stack, Player player, int x, int y, double exhaustionLevel, double maxLevel) {
        RenderSystem.setShaderColor(1, 1, 1, .25f);
        double barWidth = ModUtils.getWidth(exhaustionLevel, maxLevel);
        double barXStart = x + (rightHandSide() ? BarOverlayImpl.WIDTH - barWidth : 0);
        ModUtils.drawTexturedModalRect(stack,barXStart + 2, y + 1, 1, 28, barWidth, 9);
    }

    @Override
    public void renderText(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
        int xStart = width / 2 + getIconOffset();
        int yStart = height - vOffset;
        IThirst thirstData = ThirstHelper.getThirst(player);
        int thirst = thirstData.getThirst();
        int c = getSecondaryBarColor(0, player).colorToText();
        textHelper(graphics, xStart, yStart, thirst, c);
    }

    @Override
    public void renderIcon(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
        int xStart = width / 2 + getIconOffset();
        int yStart = height - vOffset;

        int texX = 36;
        int texBgX = 0;
        if (player.hasEffect(TANEffects.THIRST)) {
            texX += 36;  // i.e texX = 72
            texBgX = texX + 45; // i.e texBg += 117
        }

        // thirst background
        ModUtils.drawTexturedModalRect(graphics, xStart, yStart, texBgX, 32, 9, 9);
        // thirst
        ModUtils.drawTexturedModalRect(graphics, xStart, yStart, texX, 32, 9, 9);
    }

    @Override
    public ResourceLocation getIconRL() {
        return ResourceLocation.fromNamespaceAndPath("toughasnails", "textures/gui/icons.png");
    }

    /**
     * Not used, but still impl in case.
     */
    @Override
    public double getBarWidth(Player player) {
        IThirst thirstData = ThirstHelper.getThirst(player);
        int thirst = thirstData.getThirst();
        return Math.ceil(BarOverlayImpl.WIDTH * thirst / MAX_THIRST_LEVEL);
    }

    /**
     * hydration
     */
    @Override
    public Color getPrimaryBarColor(int index, Player player) {
        if (player.hasEffect(TANEffects.THIRST)) return ColorUtils.awt2Color(ModConfig.hydrationBarDebuffColor);
        return ColorUtils.awt2Color(ModConfig.hydrationBarColor);
    }

    /**
     * thirst
     */
    @Override
    public Color getSecondaryBarColor(int index, Player player) {
        if (player.hasEffect(TANEffects.THIRST)) return ColorUtils.awt2Color(ModConfig.thirstBarDebuffColor);
        return ColorUtils.awt2Color(ModConfig.thirstBarColor);
    }

    public static boolean isEnabled() {
        return ThirstHelper.isThirstEnabled();
    }

}
