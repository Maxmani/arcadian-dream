/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.armor;

import net.reimaden.arcadiandream.item.custom.armor.OrdinaryArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class OrdinaryArmorRenderer extends GeoArmorRenderer<OrdinaryArmorItem> {

    public OrdinaryArmorRenderer() {
        super(new OrdinaryArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
    }
}
