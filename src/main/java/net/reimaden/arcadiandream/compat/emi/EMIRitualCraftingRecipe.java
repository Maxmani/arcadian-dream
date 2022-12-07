package net.reimaden.arcadiandream.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.compat.IOnbashiraLocations;
import net.reimaden.arcadiandream.recipe.RitualCraftingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class EMIRitualCraftingRecipe implements EmiRecipe, IOnbashiraLocations {

    public static final Identifier TEXTURE = new Identifier(ArcadianDream.MOD_ID, "textures/gui/ritual_crafting.png");

    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;

    public EMIRitualCraftingRecipe(RitualCraftingRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = recipe.getIngredients().stream().map(EmiIngredient::of).collect(Collectors.toList());
        this.output = EmiStack.of(recipe.getOutput());
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

    @Override
    public void addWidgets(WidgetHolder widget) {
        widget.addTexture(TEXTURE, 0, 0, 184, 184, 0, 0, 184, 184, 184, 184);
        for (int i = 0; i < inputs.size(); i++) {
            widget.addSlot(inputs.get(i), ONBASHIRAS[i][0] - 1, ONBASHIRAS[i][1] - 1).drawBack(false);
        }
        widget.addSlot(output, OUTPUT_SLOT[0] - 5, OUTPUT_SLOT[1] - 5).recipeContext(this).output(true).drawBack(false);
    }
}
