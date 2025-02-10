package me.pajic.classic_bars_fabric.compat;

import net.minecraft.world.entity.player.Player;
import toughasnails.api.thirst.ThirstHelper;

public class ToughAsNailsCompat {

    public static void setHydration(Player player, float value) {
        ThirstHelper.getThirst(player).setHydration(value);
    }

    public static void setThirstExhastion(Player player, float value) {
        ThirstHelper.getThirst(player).setExhaustion(value);
    }
}
