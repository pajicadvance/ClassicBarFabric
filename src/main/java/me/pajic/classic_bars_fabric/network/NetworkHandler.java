package me.pajic.classic_bars_fabric.network;

import me.pajic.classic_bars_fabric.Main;
import me.pajic.classic_bars_fabric.compat.ToughAsNailsCompat;
import me.pajic.classic_bars_fabric.network.payload.S2CExhaustionSyncPayload;
import me.pajic.classic_bars_fabric.network.payload.S2CHydrationSyncPayload;
import me.pajic.classic_bars_fabric.network.payload.S2CSaturationSyncPayload;
import me.pajic.classic_bars_fabric.network.payload.S2CThirstExhaustionSyncPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NetworkHandler {

    public static final ResourceLocation EXHAUSTION_SYNC = ResourceLocation.fromNamespaceAndPath(Main.MODID, "exhaustion_sync");
    public static final ResourceLocation SATURATION_SYNC = ResourceLocation.fromNamespaceAndPath(Main.MODID, "saturation_sync");
    public static final ResourceLocation HYDRATION_SYNC = ResourceLocation.fromNamespaceAndPath(Main.MODID, "hydration_sync");
    public static final ResourceLocation THIRST_EXHAUSTION_SYNC = ResourceLocation.fromNamespaceAndPath(Main.MODID, "thirst_exhaustion_sync");

    private static final Map<UUID, Float> lastSaturationLevels = new HashMap<>();
    private static final Map<UUID, Float> lastExhaustionLevels = new HashMap<>();

    public static void onPlayerUpdate(ServerPlayer player) {
        Float lastSaturationLevel = lastSaturationLevels.get(player.getUUID());
        Float lastExhaustionLevel = lastExhaustionLevels.get(player.getUUID());

        float saturation = player.getFoodData().getSaturationLevel();
        if (lastSaturationLevel == null || lastSaturationLevel != saturation) {
            ServerPlayNetworking.send(player, new S2CSaturationSyncPayload(saturation));
            lastSaturationLevels.put(player.getUUID(), saturation);
        }

        float exhaustionLevel = player.getFoodData().getExhaustionLevel();
        if (lastExhaustionLevel == null || Math.abs(lastExhaustionLevel - exhaustionLevel) >= 0.01f) {
            ServerPlayNetworking.send(player, new S2CExhaustionSyncPayload(exhaustionLevel));
            lastExhaustionLevels.put(player.getUUID(), exhaustionLevel);
        }
    }

    public static void onPlayerLoggedIn(ServerPlayer player) {
        lastSaturationLevels.remove(player.getUUID());
        lastExhaustionLevels.remove(player.getUUID());
    }

    public static void clientInit() {
        ClientPlayNetworking.registerGlobalReceiver(S2CExhaustionSyncPayload.TYPE, (payload, context) ->
                context.client().execute(() -> context.client().player.getFoodData().setExhaustion(payload.exhaustion()))
        );
        ClientPlayNetworking.registerGlobalReceiver(S2CSaturationSyncPayload.TYPE, (payload, context) ->
                context.client().execute(() -> context.client().player.getFoodData().setSaturation(payload.saturation()))
        );
        if (Main.TOUGH_AS_NAILS_LOADED) {
            ClientPlayNetworking.registerGlobalReceiver(S2CHydrationSyncPayload.TYPE, (payload, context) ->
                    context.client().execute(() -> ToughAsNailsCompat.setHydration(context.client().player, payload.hydration()))
            );
            ClientPlayNetworking.registerGlobalReceiver(S2CThirstExhaustionSyncPayload.TYPE, (payload, context) ->
                    context.client().execute(() -> ToughAsNailsCompat.setThirstExhastion(context.client().player, payload.thirstExhaustion()))
            );
        }
    }

    public static void init() {
        PayloadTypeRegistry.playS2C().register(S2CExhaustionSyncPayload.TYPE, S2CExhaustionSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(S2CSaturationSyncPayload.TYPE, S2CSaturationSyncPayload.CODEC);
        if (Main.TOUGH_AS_NAILS_LOADED) {
            PayloadTypeRegistry.playS2C().register(S2CHydrationSyncPayload.TYPE, S2CHydrationSyncPayload.CODEC);
            PayloadTypeRegistry.playS2C().register(S2CThirstExhaustionSyncPayload.TYPE, S2CThirstExhaustionSyncPayload.CODEC);
        }
    }
}
