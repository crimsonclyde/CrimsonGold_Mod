package com.example.crimsongold.registry;

import com.example.crimsongold.CrimsonGoldMod;
import com.example.crimsongold.entity.CountDuckulaEntity;
import com.example.crimsongold.entity.FuchskyEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {

        public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
                        .create(Registries.ENTITY_TYPE, CrimsonGoldMod.MOD_ID);

        public static final DeferredHolder<EntityType<?>, EntityType<CountDuckulaEntity>> COUNT_DUCKULA = ENTITY_TYPES
                        .register("count_duckula",
                                        () -> EntityType.Builder.of(CountDuckulaEntity::new, MobCategory.MONSTER)
                                                        .sized(0.4F, 0.7F)
                                                        .clientTrackingRange(8)
                                                        .build("count_duckula"));

        public static final DeferredHolder<EntityType<?>, EntityType<FuchskyEntity>> FUCHSKY = ENTITY_TYPES
                        .register("fuchsky",
                                        () -> EntityType.Builder.of(FuchskyEntity::new, MobCategory.CREATURE) // Creature
                                                                                                              // because
                                                                                                              // it's
                                                                                                              // like a
                                                                                                              // wolf/fox
                                                        .sized(0.6F, 0.7F) // Fox dimensions (approx)
                                                        .clientTrackingRange(8)
                                                        .build("fuchsky"));

        private ModEntities() {
        }

        public static void register(IEventBus bus) {
                ENTITY_TYPES.register(bus);
        }
}
