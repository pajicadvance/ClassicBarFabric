package me.pajic.classic_bars_fabric.network.payload;

import me.pajic.classic_bars_fabric.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record S2CHydrationSyncPayload(float hydration) implements CustomPacketPayload {
    public static final Type<S2CHydrationSyncPayload> TYPE = new Type<>(NetworkHandler.HYDRATION_SYNC);
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CHydrationSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, S2CHydrationSyncPayload::hydration,
            S2CHydrationSyncPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
