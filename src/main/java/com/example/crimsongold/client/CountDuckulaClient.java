package com.example.crimsongold.client;

import com.example.crimsongold.CrimsonGoldMod;
import com.example.crimsongold.registry.ModEntities;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CrimsonGoldMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class CountDuckulaClient {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CrimsonGoldMod.MOD_ID,
            "textures/entity/count_duckula.png");

    private CountDuckulaClient() {
    }

    public static final ModelLayerLocation FUCHSKY_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CrimsonGoldMod.MOD_ID, "fuchsky"), "main");

    public static final ModelLayerLocation COUNT_DUCKULA_CUSTOM_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CrimsonGoldMod.MOD_ID, "count_duckula_custom"), "main");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(COUNT_DUCKULA_CUSTOM_LAYER, CountDuckulaCustomModel::createBodyLayer);
        event.registerLayerDefinition(FUCHSKY_LAYER, FuchskyModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.COUNT_DUCKULA.get(), CountDuckulaRenderer::new);
        event.registerEntityRenderer(ModEntities.FUCHSKY.get(), FuchskyRenderer::new);
    }

    public static class CountDuckulaRenderer
            extends MobRenderer<com.example.crimsongold.entity.CountDuckulaEntity, CountDuckulaCustomModel> {
        public CountDuckulaRenderer(EntityRendererProvider.Context ctx) {
            super(ctx, new CountDuckulaCustomModel(ctx.bakeLayer(COUNT_DUCKULA_CUSTOM_LAYER)), 0.3F);
        }

        @Override
        public ResourceLocation getTextureLocation(com.example.crimsongold.entity.CountDuckulaEntity entity) {
            return TEXTURE;
        }
    }
}
