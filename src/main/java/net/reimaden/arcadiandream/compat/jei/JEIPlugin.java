package net.reimaden.arcadiandream.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.recipe.RitualCraftingRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class JEIPlugin implements IModPlugin {

    public static final Identifier id = new Identifier(ArcadianDream.MOD_ID, "jei_plugin");
    public static RecipeType<RitualCraftingRecipe> RITUAL_CRAFTING_TYPE = new RecipeType<>(JEIRitualCraftingCategory.UID, RitualCraftingRecipe.class);

    @Override
    public @NotNull Identifier getPluginUid() {
        return id;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new JEIRitualCraftingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(MinecraftClient.getInstance().world).getRecipeManager();
        List<RitualCraftingRecipe> recipes = rm.listAllOfType(RitualCraftingRecipe.Type.INSTANCE);
        registration.addRecipes(RITUAL_CRAFTING_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.RITUAL_SHRINE), RITUAL_CRAFTING_TYPE);
    }
}
