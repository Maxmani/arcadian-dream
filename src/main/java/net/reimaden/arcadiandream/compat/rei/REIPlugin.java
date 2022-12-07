package net.reimaden.arcadiandream.compat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.recipe.RitualCraftingRecipe;

public class REIPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(REIRitualCraftingCategory.INSTANCE);
        registry.addWorkstations(REIRitualCraftingCategory.CATEGORY, EntryStacks.of(ModBlocks.RITUAL_SHRINE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(RitualCraftingRecipe.class, REIRitualCraftingDisplay::new);
    }
}
