/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.client.models.IceFairyEntityModel;
import net.reimaden.arcadiandream.entity.client.renderers.layers.IceFairyWingsLayer;
import net.reimaden.arcadiandream.entity.custom.mob.IceFairyEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IceFairyEntityRenderer extends GeoEntityRenderer<IceFairyEntity> {

    public IceFairyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new IceFairyEntityModel());
        shadowRadius = 0.45f;
        addRenderLayer(new IceFairyWingsLayer(this));
    }

    @Override
    public Identifier getTextureLocation(IceFairyEntity animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "textures/entity/fairy/ice_fairy.png");
    }
}
