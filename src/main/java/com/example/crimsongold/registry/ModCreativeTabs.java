package com.example.crimsongold.registry;

import com.example.crimsongold.CrimsonGoldMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, CrimsonGoldMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COUNT_DUCKULA_TAB = CREATIVE_MODE_TABS
            .register("count_duckula_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.literal("CrimsonGold Mod"))
                            .icon(() -> ModItems.COUNT_DUCKULA_SPAWN_EGG.get().getDefaultInstance())
                            .displayItems((parameters, output) -> {
                                output.accept(ModItems.COUNT_DUCKULA_SPAWN_EGG.get());
                                output.accept(ModItems.FUCHSKY_SPAWN_EGG.get());
                            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
