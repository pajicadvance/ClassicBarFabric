package me.pajic.classic_bars_fabric.mixin;

import me.pajic.classic_bars_fabric.network.NetworkHandler;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(
            method = "placeNewPlayer",
            at = @At("TAIL")
    )
    private void onPlayerConnect(Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo ci) {
        NetworkHandler.onPlayerLoggedIn(player);
    }
}
