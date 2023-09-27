/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import dhyces.trimmed.api.data.ItemOverrideDataProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.ModItems;

public class ModItemOverrideGenerator extends ItemOverrideDataProvider {

    public ModItemOverrideGenerator(DataOutput output) {
        super(output, ArcadianDream.MOD_ID);
    }

    @Override
    protected void addItemOverrides() {
        anyTrimBuilder(new ArmorSet(ModItems.MAKAITE_HELMET, ModItems.MAKAITE_CHESTPLATE, ModItems.MAKAITE_LEGGINGS, ModItems.MAKAITE_BOOTS))
                .twoLayer()
                .excludeVanillaDarker()
                .exclude(new Identifier(ArcadianDream.MOD_ID, "trims/color_palettes/makaite"))
                .include(new Identifier(ArcadianDream.MOD_ID, "trims/color_palettes/makaite_darker"))
                .end();
        anyTrimBuilder(new ArmorSet(ModItems.HIHIIROKANE_HELMET, ModItems.HIHIIROKANE_CHESTPLATE, ModItems.HIHIIROKANE_LEGGINGS, ModItems.HIHIIROKANE_BOOTS))
                .twoLayer()
                .excludeVanillaDarker()
                .exclude(new Identifier(ArcadianDream.MOD_ID, "trims/color_palettes/hihiirokane"))
                .include(new Identifier(ArcadianDream.MOD_ID, "trims/color_palettes/hihiirokane_darker"))
                .end();
    }
}
