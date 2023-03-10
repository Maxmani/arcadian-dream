/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.models;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.hostile.SunflowerFairyEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SunflowerFairyEntityModel extends GeoModel<SunflowerFairyEntity> {

    @Override
    public Identifier getModelResource(SunflowerFairyEntity animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "geo/entity/sunflower_fairy.geo.json");
    }

    @Override
    public Identifier getTextureResource(SunflowerFairyEntity animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "textures/entity/fairy/sunflower_fairy.png");
    }

    @Override
    public Identifier getAnimationResource(SunflowerFairyEntity animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "animations/entity/sunflower_fairy.animation.json");
    }

    @Override
    public void setCustomAnimations(SunflowerFairyEntity animatable, long instanceId, AnimationState<SunflowerFairyEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
