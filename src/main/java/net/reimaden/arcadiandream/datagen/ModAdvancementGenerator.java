/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.ItemConvertible;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.advancement.ModCriteria;
import net.reimaden.arcadiandream.advancement.RitualCraftingCriterion;
import net.reimaden.arcadiandream.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModAdvancementGenerator extends FabricAdvancementProvider {

    private static final List<Consumer<Consumer<Advancement>>> generators = Util.make(Lists.newArrayList(),
            list -> list.add(new ModAdvancementsGenerator()));

    public ModAdvancementGenerator(FabricDataOutput output) {
        super(output);
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

        @SuppressWarnings("unused")
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
                    .criterion(ModItems.POWER_ITEM.toString(), InventoryChangedCriterion.Conditions.items(ModItems.POWER_ITEM))
                    .build(consumer, "arcadiandream:root");

            Advancement mineDragonGem = Advancement.Builder.create()
                    .parent(parentAdvancement)
                    .display(makeDisplay(AdvancementFrame.TASK, ModItems.DRAGON_GEM, "mine_dragon_gem"))
                    .criterion(ModItems.DRAGON_GEM.toString(), InventoryChangedCriterion.Conditions.items(ModItems.DRAGON_GEM))
                    .build(consumer, "arcadiandream:mine_dragon_gem");

            Advancement ritualCrafting = Advancement.Builder.create()
                    .parent(mineDragonGem)
                    .display(makeDisplay(AdvancementFrame.GOAL, ModItems.RITUAL_SHRINE, "ritual_crafting"))
                    .criterion("ritual_crafting", new RitualCraftingCriterion.Conditions(ModCriteria.RITUAL_CRAFTING.getId(), EntityPredicate.Extended.EMPTY))
                    .build(consumer, "arcadiandream:ritual_crafting");
        }
    }
}
