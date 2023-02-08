/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.entities;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.reimaden.arcadiandream.entity.client.models.FairyEntityModel;
import net.reimaden.arcadiandream.entity.custom.FairyEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FairyEntityRenderer extends GeoEntityRenderer<FairyEntity> {

    public FairyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new FairyEntityModel());
        shadowRadius = 0.45f;
    }
}
