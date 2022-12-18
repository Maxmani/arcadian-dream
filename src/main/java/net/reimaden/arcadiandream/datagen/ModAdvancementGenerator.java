/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.ItemConvertible;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModAdvancementGenerator extends FabricAdvancementProvider {

    private static final List<Consumer<Consumer<Advancement>>> generators = Util.make(Lists.newArrayList(),
            list -> list.add(new ModAdvancementsGenerator()));

    protected ModAdvancementGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        generators.forEach(i -> i.accept(consumer));
    }

    private static class ModAdvancementsGenerator implements Consumer<Consumer<Advancement>> {

        private static AdvancementDisplay makeDisplay(AdvancementFrame frame, ItemConvertible icon, String titleKey) {
            return new AdvancementDisplay(icon.asItem().getDefaultStack(),
                    Text.translatable("advancements." + ArcadianDream.MOD_ID + "." + titleKey + ".title"),
                    Text.translatable("advancements." + ArcadianDream.MOD_ID + "." + titleKey + ".description"),
                    null,
                    frame,
                    true,
                    true,
                    false);
        }

        @Override
        public void accept(Consumer<Advancement> consumer) {

            Advancement parentAdvancement = Advancement.Builder.create()
                    .display(ModItems.POWER_ITEM, Text.translatable("advancements." + ArcadianDream.MOD_ID + ".root.title"),
                            Text.translatable("advancements." + ArcadianDream.MOD_ID + ".root.description"),
                            new Identifier("arcadiandream:textures/block/dragon_gem_block.png"),
                            AdvancementFrame.TASK,
                            false,
                            false,
                            false)
                    .criterion("inventory_changed", InventoryChangedCriterion.Conditions.items(ModItems.POWER_ITEM))
                    .build(consumer, "arcadiandream:root");

            Advancement dragonGem = Advancement.Builder.create()
                    .parent(parentAdvancement)
                    .display(makeDisplay(AdvancementFrame.TASK, ModItems.DRAGON_GEM, "mine_dragon_gem"))
                    .criterion("inventory_changed", InventoryChangedCriterion.Conditions.items(ModItems.DRAGON_GEM))
                    .build(consumer, "arcadiandream:mine_dragon_gem");
        }
    }
}
