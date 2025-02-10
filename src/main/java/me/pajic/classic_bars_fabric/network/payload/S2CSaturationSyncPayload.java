package me.pajic.classic_bars_fabric.network.payload;

import me.pajic.classic_bars_fabric.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record S2CSaturationSyncPayload(float saturation) implements CustomPacketPayload {
    public static final Type<S2CSaturationSyncPayload> TYPE = new Type<>(NetworkHandler.SATURATION_SYNC);
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSaturationSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, S2CSaturationSyncPayload::saturation,
            S2CSaturationSyncPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
