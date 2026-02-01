package com.example.crimsongold.client;

import com.example.crimsongold.CrimsonGoldMod;
import com.example.crimsongold.entity.FuchskyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FuchskyRenderer extends MobRenderer<FuchskyEntity, FuchskyModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft",
            "textures/entity/fox/fox.png");
    private static final ResourceLocation SLEEP_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft",
            "textures/entity/fox/fox_sleep.png");

    public FuchskyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new FuchskyModel(ctx.bakeLayer(CountDuckulaClient.FUCHSKY_LAYER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(FuchskyEntity entity) {
        if (entity.isInSittingPose()) {
            return SLEEP_TEXTURE;
        }
        return TEXTURE;
    }
}
