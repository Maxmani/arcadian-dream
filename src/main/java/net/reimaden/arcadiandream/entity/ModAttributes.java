/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.reimaden.arcadiandream.entity.custom.mob.FairyEntity;
import net.reimaden.arcadiandream.entity.custom.mob.IceFairyEntity;
import net.reimaden.arcadiandream.entity.custom.mob.SunflowerFairyEntity;

public class ModAttributes {

    public static void register() {
        FabricDefaultAttributeRegistry.register(ModEntities.FAIRY, FairyEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SUNFLOWER_FAIRY, SunflowerFairyEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ICE_FAIRY, IceFairyEntity.setAttributes());
    }
}
