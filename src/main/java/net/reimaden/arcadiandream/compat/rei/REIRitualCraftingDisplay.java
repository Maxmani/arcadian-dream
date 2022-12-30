/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.reimaden.arcadiandream.recipe.RitualCraftingRecipe;

import java.util.Collections;
import java.util.List;

public class REIRitualCraftingDisplay implements Display {

    private final List<EntryIngredient> input;
    private final EntryIngredient output;
    private final byte moonPhase;

    public REIRitualCraftingDisplay(RitualCraftingRecipe recipe) {
        this.input = EntryIngredients.ofIngredients(recipe.getIngredients());
        this.output = EntryIngredients.of(recipe.getOutput());
        this.moonPhase = recipe.getMoonPhase();
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
}
