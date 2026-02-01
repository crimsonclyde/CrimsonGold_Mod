package com.example.crimsongold;

import com.example.crimsongold.registry.ModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public final class ModEventHandlers {

    private ModEventHandlers() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.register(ModEventHandlers.class);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.register(com.example.crimsongold.client.CountDuckulaClient.class);
        }
    }

    @SubscribeEvent
    public static void onAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.COUNT_DUCKULA.get(),
                com.example.crimsongold.entity.CountDuckulaEntity.createAttributes().build());
        event.put(ModEntities.FUCHSKY.get(),
                com.example.crimsongold.entity.FuchskyEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntities.COUNT_DUCKULA.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                com.example.crimsongold.entity.CountDuckulaEntity::canSpawnHere,
                RegisterSpawnPlacementsEvent.Operation.OR);
    }
}
