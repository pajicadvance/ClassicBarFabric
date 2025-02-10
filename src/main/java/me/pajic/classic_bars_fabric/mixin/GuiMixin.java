package me.pajic.classic_bars_fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.classic_bars_fabric.gui.GuiHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public class GuiMixin {

    @WrapMethod(method = "renderPlayerHealth")
    private void renderPlayerHealth(GuiGraphics guiGraphics, Operation<Void> original) {
        GuiHandler.render(guiGraphics);
    }
}
