/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.models;

import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.client.entities.FairyEntityRenderer;
import net.reimaden.arcadiandream.entity.custom.FairyEntity;
import software.bernie.geckolib.model.GeoModel;

public class FairyEntityModel extends GeoModel<FairyEntity> {

    @Override
    public Identifier getModelResource(FairyEntity animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "geo/entity/fairy.geo.json");
    }

    @Override
    public Identifier getTextureResource(FairyEntity animatable) {
        return FairyEntityRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(FairyEntity animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "animations/entity/fairy.animation.json");
    }
}
