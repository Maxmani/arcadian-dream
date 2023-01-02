/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.compat.IRitualCraftingLocations;
import net.reimaden.arcadiandream.recipe.RitualCraftingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class EMIRitualCraftingRecipe implements EmiRecipe, IRitualCraftingLocations {

    private static final Identifier TEXTURE = new Identifier(ArcadianDream.MOD_ID, "textures/gui/ritual_crafting.png");
    private static final Identifier MOON_ICON = new Identifier(ArcadianDream.MOD_ID, "textures/gui/moon.png");
    private static final Identifier DIMENSION_ICON = new Identifier(ArcadianDream.MOD_ID, "textures/gui/dimension.png");

    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;
    private final byte moonPhase;
    private final String dimension;

    public EMIRitualCraftingRecipe(RitualCraftingRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = recipe.getIngredients().stream().map(EmiIngredient::of).collect(Collectors.toList());
        this.output = EmiStack.of(recipe.getOutput());
        this.moonPhase = recipe.getMoonPhase();
        this.dimension = recipe.getDimension();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIPlugin.RITUAL_CRAFTING_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 184;
    }

    @Override
    public int getDisplayHeight() {
        return 184;
    }
    
    private OrderedText moonPhaseTooltip() {
        Text tooltip;
        switch (moonPhase) {
            case 0 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_0");
            case 1 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_1");
            case 2 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_2");
            case 3 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_3");
            case 4 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_4");
            case 5 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + "..ritual_crafting.moon_phase_5");
            case 6 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_6");
            case 7 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_7");
            default -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_invalid");
        }

        return Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.requirement", tooltip).asOrderedText();
    }

    private OrderedText dimensionTooltip() {
        Text tooltip;
        switch (dimension) {
            case "overworld" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.dimension_overworld");
            case "the_nether" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.dimension_nether");
            case "the_end" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.dimension_end");
            default -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.dimension_unknown", dimension);
        }

        return Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.requirement", tooltip).asOrderedText();
    }

    @Override
    public void addWidgets(WidgetHolder widget) {
        widget.addTexture(TEXTURE, 0, 0, 184, 184, 0, 0, 184, 184, 184, 184);
        for (int i = 0; i < inputs.size(); i++) {
            widget.addSlot(inputs.get(i), ONBASHIRAS[i][0] - 1, ONBASHIRAS[i][1] - 1).drawBack(false);
        }
        widget.addSlot(output, OUTPUT_SLOT[0] - 5, OUTPUT_SLOT[1] - 5).recipeContext(this).output(true).drawBack(false);
        if (moonPhase != -1) {
            BiFunction<Integer, Integer, List<TooltipComponent>> tooltipSupplier = (mouseX, mouseY) ->
                    List.of(TooltipComponent.of(moonPhaseTooltip()));

            widget.addTexture(MOON_ICON, MOON_SLOT[0] + 1, MOON_SLOT[1] - 1, 16, 16,
                    0, 0, 16, 16, 16, 16).tooltip(tooltipSupplier);
        } else if (!dimension.isEmpty()) {
            BiFunction<Integer, Integer, List<TooltipComponent>> tooltipSupplier = (mouseX, mouseY) ->
                    List.of(TooltipComponent.of(dimensionTooltip()));

            widget.addTexture(DIMENSION_ICON, DIMENSION_SLOT[0] + 1, DIMENSION_SLOT[1] - 1, 16, 16,
                    0, 0, 16, 16, 16, 16).tooltip(tooltipSupplier);
        }
    }
}
