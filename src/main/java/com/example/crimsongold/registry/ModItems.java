package com.example.crimsongold.registry;

import com.example.crimsongold.CrimsonGoldMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.Registries;

public final class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM,
            CrimsonGoldMod.MOD_ID);

    public static final DeferredHolder<Item, SpawnEggItem> COUNT_DUCKULA_SPAWN_EGG = ITEMS.register(
            "count_duckula_spawn_egg",
            () -> new SpawnEggItem(ModEntities.COUNT_DUCKULA.get(), 0x1F1F1F, 0xFF0000, new Item.Properties()));

    public static final DeferredHolder<Item, SpawnEggItem> FUCHSKY_SPAWN_EGG = ITEMS.register("fuchsky_spawn_egg",
            () -> new SpawnEggItem(ModEntities.FUCHSKY.get(), 0xE07941, 0xFFFFFF, new Item.Properties()));

    private ModItems() {
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
