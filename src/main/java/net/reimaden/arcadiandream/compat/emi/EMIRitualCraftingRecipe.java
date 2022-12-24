/*
 * Copyright (c) 2022 Maxmani and contributors.
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

    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;
    private final String moonPhase;

    public EMIRitualCraftingRecipe(RitualCraftingRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = recipe.getIngredients().stream().map(EmiIngredient::of).collect(Collectors.toList());
        this.output = EmiStack.of(recipe.getOutput());
        this.moonPhase = recipe.getMoonPhase();
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
    
    private OrderedText tooltip() {
        Text tooltip;
        switch (moonPhase) {
            case "0" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase.0");
            case "1" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase.1");
            case "2" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase.2");
            case "3" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase.3");
            case "4" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase.4");
            case "5" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + "..ritual_crafting.moon_phase.5");
            case "6" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase.6");
            case "7" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase.7");
            default -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase.invalid");
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
        if (!moonPhase.isEmpty()) {
            BiFunction<Integer, Integer, List<TooltipComponent>> tooltipSupplier = (mouseX, mouseY) ->
                    List.of(TooltipComponent.of(tooltip()));

            widget.addTexture(MOON_ICON, MOON_SLOT[0] + 1, MOON_SLOT[1] - 1, 16, 16,
                    0, 0, 16, 16, 16, 16).tooltip(tooltipSupplier);
        }
    }
}
