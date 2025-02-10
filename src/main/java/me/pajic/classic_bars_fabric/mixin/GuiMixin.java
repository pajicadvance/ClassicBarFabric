package me.pajic.classic_bars_fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.classic_bars_fabric.gui.GuiHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @WrapMethod(method = "renderPlayerHealth")
    private void replaceHudRendering(GuiGraphics guiGraphics, Operation<Void> original) {
        GuiHandler.render(guiGraphics);
    }

    @Inject(
            method = "renderVehicleHealth",
            at = @At("HEAD"),
            cancellable = true
    )
    private void hideVehicleHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        ci.cancel();
    }
}
