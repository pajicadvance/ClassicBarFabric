package me.pajic.classic_bars_fabric;

import me.pajic.classic_bars_fabric.gui.GuiHandler;
import me.pajic.classic_bars_fabric.network.NetworkHandler;
import net.fabricmc.api.ClientModInitializer;

public class ClientMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NetworkHandler.clientInit();
        GuiHandler.initGuiHandler();
    }
}
