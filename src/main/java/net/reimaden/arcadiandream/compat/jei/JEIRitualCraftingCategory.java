///*
// * Copyright (c) 2022 Maxmani and contributors.
// * Licensed under the EUPL-1.2 or later.
// */
//
//package net.reimaden.arcadiandream.compat.jei;
//
//import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.recipe.IFocusGroup;
//import mezz.jei.api.recipe.RecipeIngredientRole;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.text.Text;
//import net.minecraft.util.Identifier;
//import net.reimaden.arcadiandream.ArcadianDream;
//import net.reimaden.arcadiandream.block.ModBlocks;
//import net.reimaden.arcadiandream.compat.IRitualCraftingLocations;
//import net.reimaden.arcadiandream.recipe.RitualCraftingRecipe;
//import org.jetbrains.annotations.NotNull;
//
//public class JEIRitualCraftingCategory implements IRecipeCategory<RitualCraftingRecipe>, IRitualCraftingLocations {
//
//    public static final Identifier UID = new Identifier(ArcadianDream.MOD_ID, "ritual_crafting");
//    public static final Identifier TEXTURE = new Identifier(ArcadianDream.MOD_ID, "textures/gui/ritual_crafting.png");
//    public static final Text TITLE = Text.translatable("arcadiandream.category.ritual_crafting");
//
//    private final IDrawable background;
//    private final IDrawable icon;
//
//    public JEIRitualCraftingCategory(IGuiHelper guiHelper) {
//        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 184, 184).setTextureSize(184, 184).build();
//        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.RITUAL_SHRINE));
//    }
//
//    @Override
//    public @NotNull RecipeType<RitualCraftingRecipe> getRecipeType() {
//        return JEIPlugin.RITUAL_CRAFTING_TYPE;
//    }
//
//    @Override
//    public @NotNull Text getTitle() {
//        return TITLE;
//    }
//
//    @Override
//    public @NotNull IDrawable getBackground() {
//        return background;
//    }
//
//    @Override
//    public @NotNull IDrawable getIcon() {
//        return icon;
//    }
//
//    @Override
//    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, RitualCraftingRecipe recipe, @NotNull IFocusGroup focuses) {
//        for (int i = 0; i < recipe.getIngredients().size(); i++) {
//            builder.addSlot(RecipeIngredientRole.INPUT, ONBASHIRAS[i][0], ONBASHIRAS[i][1]).addIngredients(recipe.getIngredients().get(i));
//        }
//
//        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_SLOT[0], OUTPUT_SLOT[1]).addItemStack(recipe.getOutput());
//    }
//}
