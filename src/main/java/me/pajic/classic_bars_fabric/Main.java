package me.pajic.classic_bars_fabric;

import me.pajic.classic_bars_fabric.config.ModConfig;
import me.pajic.classic_bars_fabric.network.NetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {

    public static final String MODID = "classic_bars_fabric";
    public static Logger LOGGER = LogManager.getLogger();
    public static final boolean TOUGH_AS_NAILS_LOADED = FabricLoader.getInstance().isModLoaded("toughasnails");

    @Override
    public void onInitialize() {
        ModConfig.HANDLER.load();
        NetworkHandler.init();
    }
}
