/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.armor;

import net.reimaden.arcadiandream.entity.client.models.OrdinaryArmorModel;
import net.reimaden.arcadiandream.item.custom.armor.OrdinaryArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class OrdinaryArmorRenderer extends GeoArmorRenderer<OrdinaryArmorItem> {

    public OrdinaryArmorRenderer() {
        super(new OrdinaryArmorModel());
    }
}
