/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.compat.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.compat.IOnbashiraLocations;

import java.util.ArrayList;
import java.util.List;

public class REIRitualCraftingCategory implements DisplayCategory<REIRitualCraftingDisplay>, IOnbashiraLocations {

    public static final REIRitualCraftingCategory INSTANCE = new REIRitualCraftingCategory();

    public static final Identifier ID = new Identifier(ArcadianDream.MOD_ID, "ritual_crafting");
    public static final Identifier TEXTURE = new Identifier(ArcadianDream.MOD_ID, "textures/gui/ritual_crafting.png");
    public static final CategoryIdentifier<? extends REIRitualCraftingDisplay> CATEGORY = CategoryIdentifier.of(ID);

    public static final EntryStack<ItemStack> ICON = EntryStacks.of(ModBlocks.RITUAL_SHRINE);
    public static final Text TITLE = Text.translatable("arcadiandream.category.ritual_crafting");

    @Override
    public CategoryIdentifier<? extends REIRitualCraftingDisplay> getCategoryIdentifier() {
        return CATEGORY;
    }

    @Override
    public Text getTitle() {
        return TITLE;
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public int getDisplayWidth(REIRitualCraftingDisplay display) {
        return 184;
    }

    @Override
    public int getDisplayHeight() {
        return 184;
    }

    @Override
    public List<Widget> setupDisplay(REIRitualCraftingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(TEXTURE, bounds.x, bounds.y, 0, 0, 184, 184, 184, 184));
        widgets.add(Widgets.createSlot(new Point(OUTPUT_SLOT[0] + bounds.x, OUTPUT_SLOT[1] + bounds.y))
                .entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        for (int i = 0; i < display.getInputEntries().size(); i++) {
            widgets.add(Widgets.createSlot(new Point(ONBASHIRAS[i][0] + bounds.x, ONBASHIRAS[i][1] + bounds.y))
                    .entries(display.getInputEntries().get(i)).disableBackground().markInput());
        }

        return widgets;
    }
}
