/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.models;

import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.FairyEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FairyEntityModel extends DefaultedEntityGeoModel<FairyEntity> {

    public FairyEntityModel() {
        super(new Identifier(ArcadianDream.MOD_ID, "monster/fairy"), true);
    }
}
