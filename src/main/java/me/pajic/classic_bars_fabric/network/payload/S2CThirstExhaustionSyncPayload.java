package me.pajic.classic_bars_fabric.network.payload;

import me.pajic.classic_bars_fabric.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record S2CThirstExhaustionSyncPayload(float thirstExhaustion) implements CustomPacketPayload {
    public static final Type<S2CThirstExhaustionSyncPayload> TYPE = new Type<>(NetworkHandler.THIRST_EXHAUSTION_SYNC);
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CThirstExhaustionSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, S2CThirstExhaustionSyncPayload::thirstExhaustion,
            S2CThirstExhaustionSyncPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
