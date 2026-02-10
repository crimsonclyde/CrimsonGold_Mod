package com.example.crimsongold;

import com.example.crimsongold.registry.ModCreativeTabs;
import com.example.crimsongold.registry.ModEntities;
import com.example.crimsongold.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CrimsonGoldMod.MOD_ID)
public class CrimsonGoldMod {
    public static final String MOD_ID = "crimsongold_mod";

    public CrimsonGoldMod(IEventBus modEventBus) {
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModEventHandlers.register(modEventBus);
        com.example.crimsongold.registry.ModSounds.register(modEventBus);
    }
}
