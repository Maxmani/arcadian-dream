/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.DynamicRegistryManager;
import net.reimaden.arcadiandream.recipe.RitualCraftingRecipe;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class REIRitualCraftingDisplay implements Display {

    private final List<EntryIngredient> input;
    private final EntryIngredient output;
    private final byte moonPhase;
    private final String dimension;

    public REIRitualCraftingDisplay(RitualCraftingRecipe recipe) {
        DynamicRegistryManager registryManager = Objects.requireNonNull(MinecraftClient.getInstance().world).getRegistryManager();
        this.input = EntryIngredients.ofIngredients(recipe.getIngredients());
        this.output = EntryIngredients.of(recipe.getOutput(registryManager));
        this.moonPhase = recipe.getMoonPhase();
        this.dimension = recipe.getDimension();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(output);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REIRitualCraftingCategory.CATEGORY;
    }

    byte getMoonPhase() {
        return moonPhase;
    }

    String getDimension() {
        return dimension;
    }
}
