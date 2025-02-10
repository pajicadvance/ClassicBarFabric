package me.pajic.classic_bars_fabric.network.payload;

import me.pajic.classic_bars_fabric.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record S2CExhaustionSyncPayload(float exhaustion) implements CustomPacketPayload {
    public static final Type<S2CExhaustionSyncPayload> TYPE = new Type<>(NetworkHandler.EXHAUSTION_SYNC);
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CExhaustionSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, S2CExhaustionSyncPayload::exhaustion,
            S2CExhaustionSyncPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}