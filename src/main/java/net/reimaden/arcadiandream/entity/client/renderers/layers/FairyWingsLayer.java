/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers.layers;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.mob.FairyEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class FairyWingsLayer extends GeoRenderLayer<FairyEntity> {

    private static final Identifier TEXTURE = new Identifier(ArcadianDream.MOD_ID, "textures/entity/fairy/fairy_wings.png");

    public FairyWingsLayer(GeoRenderer<FairyEntity> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(MatrixStack poseStack, FairyEntity animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderLayer renderLayer = RenderLayer.getEntityTranslucent(TEXTURE);

        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, renderLayer,
                bufferSource.getBuffer(renderLayer), partialTick, packedLight, OverlayTexture.DEFAULT_UV,
                1, 1, 1, 0.5f);
    }
}
