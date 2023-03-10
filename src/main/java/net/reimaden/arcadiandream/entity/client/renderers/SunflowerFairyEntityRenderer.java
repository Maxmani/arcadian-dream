/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.client.models.SunflowerFairyEntityModel;
import net.reimaden.arcadiandream.entity.client.renderers.layers.SunflowerFairyWingsLayer;
import net.reimaden.arcadiandream.entity.custom.hostile.SunflowerFairyEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SunflowerFairyEntityRenderer extends GeoEntityRenderer<SunflowerFairyEntity> {

    public SunflowerFairyEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SunflowerFairyEntityModel());
        shadowRadius = 0.6f;
        addRenderLayer(new SunflowerFairyWingsLayer(this));
    }

    @Override
    public Identifier getTextureLocation(SunflowerFairyEntity animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "textures/entity/fairy/sunflower_fairy.png");
    }
}
