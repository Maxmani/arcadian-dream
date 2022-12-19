/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModRecipes {

    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ArcadianDream.MOD_ID, RitualCraftingRecipe.Serializer.ID),
                RitualCraftingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(ArcadianDream.MOD_ID, RitualCraftingRecipe.Type.ID),
                RitualCraftingRecipe.Type.INSTANCE);
    }
}
