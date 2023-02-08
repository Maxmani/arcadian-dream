/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.models;

import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.custom.armor.OrdinaryArmorItem;
import software.bernie.geckolib.model.GeoModel;

public class OrdinaryArmorModel extends GeoModel<OrdinaryArmorItem> {

    @Override
    public Identifier getModelResource(OrdinaryArmorItem animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "geo/armor/ordinary_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(OrdinaryArmorItem animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "textures/models/armor/ordinary_armor.png");
    }

    @Override
    public Identifier getAnimationResource(OrdinaryArmorItem animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "animations/armor/armor.animation.json");
    }
}
