package me.pajic.classic_bars_fabric.gui;

import me.pajic.classic_bars_fabric.Main;
import me.pajic.classic_bars_fabric.api.BarOverlay;
import me.pajic.classic_bars_fabric.config.ModConfig;
import me.pajic.classic_bars_fabric.impl.overlays.mod.Thirst;
import me.pajic.classic_bars_fabric.impl.overlays.vanilla.*;
import me.pajic.classic_bars_fabric.util.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class GuiHandler {

    public static void initGuiHandler() {
        setupOverlays();
        setupSides();
    }

    public static int rightHeight = 39;
    public static int leftHeight = 39;

    private static final List<BarOverlay> all = new ArrayList<>();
    public static final Map<String, BarOverlay> registry = new HashMap<>();
    private static final List<BarOverlay> errored = new ArrayList<>();

    public static void register(BarOverlay iBarOverlay) {
        registry.put(iBarOverlay.name(), iBarOverlay);
    }

    public static void registerAll(BarOverlay... iBarOverlay) {
        Arrays.stream(iBarOverlay).forEach(overlay -> {
            if (overlay != null) {
                registry.put(overlay.name(), overlay);
            }
        });
    }

    public static void render(GuiGraphics guiGraphics) {
        Entity entity = ModUtils.mc.getCameraEntity();
        if (!(entity instanceof Player player)) return;
        if (player.getAbilities().instabuild || player.isSpectator()) return;
        ModUtils.mc.getProfiler().push("classicbars_hud");

        rightHeight = 39;
        leftHeight = 39;
        int screenWidth = ModUtils.mc.getWindow().getGuiScaledWidth();
        int screenHeight = ModUtils.mc.getWindow().getGuiScaledHeight();

        for (BarOverlay overlay : all) {
            boolean rightHand = overlay.rightHandSide();
            try {
                overlay.render(guiGraphics, player, screenWidth, screenHeight, getOffset(rightHand));
            } catch (Error e) {
                Main.LOGGER.error("Removing broken overlay {}", overlay.name());
                Main.LOGGER.error(e);
                errored.add(overlay);
            }
        }
        if (!errored.isEmpty()) all.removeAll(errored);

        ModUtils.mc.getProfiler().pop();
    }

    public static void increment(boolean side, int amount){
        if (side) rightHeight += amount;
        else leftHeight += amount;
    }

    public static int getOffset(boolean right) {
        return right ? rightHeight : leftHeight;
    }

    private static void setupOverlays() {
        Main.LOGGER.info("Registering Vanilla Overlays");
        registerAll(
                new Absorption(),
                new Air(),
                new Armor(),
                new ArmorToughness(),
                new Health(),
                new Hunger(),
                new MountHealth()
        );
        Main.LOGGER.info("Registering Modded Overlays");
        if (Main.TOUGH_AS_NAILS_LOADED) register(new Thirst());
    }

    public static void setupSides() {
        all.clear();
        ModConfig.leftorder.stream().filter(s -> registry.get(s) != null).forEach(e -> all.add(registry.get(e).setSide(false)));
        ModConfig.rightorder.stream().filter(s -> registry.get(s) != null).forEach(e -> all.add(registry.get(e).setSide(true)));
        all.removeAll(errored);
    }
}
