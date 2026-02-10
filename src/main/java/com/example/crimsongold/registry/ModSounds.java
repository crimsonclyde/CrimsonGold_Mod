package com.example.crimsongold.registry;

import com.example.crimsongold.CrimsonGoldMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
        public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT,
                        CrimsonGoldMod.MOD_ID);

        public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COUNT_DUCKULA_AMBIENT = registerSoundEvent(
                        "entity.count_duckula.ambient");
        public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COUNT_DUCKULA_HURT = registerSoundEvent(
                        "entity.count_duckula.hurt");
        public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COUNT_DUCKULA_DEATH = registerSoundEvent(
                        "entity.count_duckula.death");

        private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
                ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CrimsonGoldMod.MOD_ID, name);
                return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
        }

        public static void register(IEventBus eventBus) {
                SOUND_EVENTS.register(eventBus);
        }
}
