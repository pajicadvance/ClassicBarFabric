package me.pajic.classic_bars_fabric.mixin.compat;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toughasnails.thirst.ThirstOverlayRenderer;

@IfModLoaded("toughasnails")
@Mixin(value = ThirstOverlayRenderer.class, remap = false)
public class ThirstOverlayRendererMixin {

    @Inject(
            method = "onBeginRenderAir",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void hideThirstInfo(CallbackInfo ci) {
        ci.cancel();
    }
}