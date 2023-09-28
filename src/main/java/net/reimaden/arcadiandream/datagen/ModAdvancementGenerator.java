/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.DamagePredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.DistancePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.advancement.BulletsCancelledCriterion;
import net.reimaden.arcadiandream.advancement.DanmakuModifiedCriterion;
import net.reimaden.arcadiandream.advancement.RitualCraftingCriterion;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.item.custom.danmaku.DyeableBullet;
import net.reimaden.arcadiandream.util.ColorMap;
import net.reimaden.arcadiandream.util.ModTags;
import net.reimaden.arcadiandream.world.structure.ModStructures;

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

        private static final Identifier BACKGROUND = new Identifier(ArcadianDream.MOD_ID, "textures/block/danmaku_crafting_table_bottom.png");
        private static final Item[] PATTERN_ITEMS = new Item[]{ModItems.SPREAD_PATTERN, ModItems.RAY_PATTERN,
                ModItems.RING_PATTERN, ModItems.ARC_PATTERN, ModItems.DOUBLE_PATTERN, ModItems.TRIPLE_PATTERN};

        private static <T> AdvancementDisplay makeDisplay(AdvancementFrame frame, T icon, String titleKey) {
            final String prefix = "advancements." + ArcadianDream.MOD_ID + "." + titleKey;
            ItemStack stack = icon instanceof ItemConvertible ? ((ItemConvertible) icon).asItem().getDefaultStack() : (ItemStack) icon;
            return new AdvancementDisplay(stack,
                    Text.translatable(prefix + ".title"),
                    Text.translatable(prefix + ".description"),
                    null,
                    frame,
                    true,
                    true,
                    false);
        }

        private static String makeName(String name) {
            return new Identifier(ArcadianDream.MOD_ID, name).toString();
        }

        @SuppressWarnings("unused")
        @Override
        public void accept(Consumer<Advancement> consumer) {

            Advancement rootAdvancement = Advancement.Builder.create()
                    .display(ModItems.POWER_ITEM, Text.translatable("advancements." + ArcadianDream.MOD_ID + ".root.title"),
                            Text.translatable("advancements." + ArcadianDream.MOD_ID + ".root.description"),
                            BACKGROUND,
                            AdvancementFrame.TASK,
                            false,
                            false,
                            false)
                    .criterion("get_item", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(ModTags.Items.ITEMS).build()))
                    .build(consumer, makeName("root"));

            Advancement mineDragonGem = Advancement.Builder.create()
                    .parent(rootAdvancement)
                    .display(makeDisplay(AdvancementFrame.TASK, ModItems.DRAGON_GEM, "mine_dragon_gem"))
                    .criterion(ModItems.DRAGON_GEM.toString(), InventoryChangedCriterion.Conditions.items(ModItems.DRAGON_GEM))
                    .build(consumer, makeName("mine_dragon_gem"));

            Advancement ritualCrafting = Advancement.Builder.create()
                    .parent(mineDragonGem)
                    .display(makeDisplay(AdvancementFrame.TASK, ModBlocks.RITUAL_SHRINE, "ritual_crafting"))
                    .criterion("ritual_crafting", RitualCraftingCriterion.Conditions.create())
                    .build(consumer, makeName("ritual_crafting"));

            Advancement obtainHouraiElixir = Advancement.Builder.create()
                    .parent(ritualCrafting)
                    .display(makeDisplay(AdvancementFrame.CHALLENGE, ModItems.HOURAI_ELIXIR, "obtain_hourai_elixir"))
                    .criterion(ModItems.HOURAI_ELIXIR.toString(), InventoryChangedCriterion.Conditions.items(ModItems.HOURAI_ELIXIR))
                    .rewards(AdvancementRewards.Builder.experience(150))
                    .build(consumer, makeName("obtain_hourai_elixir"));

            Advancement shootDanmaku = Advancement.Builder.create()
                    .parent(rootAdvancement)
                    .display(makeDisplay(AdvancementFrame.TASK, ModItems.CIRCLE_SHOT, "shoot_danmaku"))
                    .criterion("shot_danmaku", PlayerHurtEntityCriterion.Conditions.create(DamagePredicate.Builder.create()
                            .type(DamageSourcePredicate.Builder.create().tag(TagPredicate.expected(ModTags.DamageTypes.IS_DANMAKU))
                                    .directEntity(EntityPredicate.Builder.create().type(ModTags.EntityTypes.DANMAKU)))))
                    .build(consumer, makeName("shoot_danmaku"));

            Advancement modifyDanmaku = Advancement.Builder.create()
                    .parent(shootDanmaku)
                    .display(makeDisplay(AdvancementFrame.TASK, ModBlocks.DANMAKU_CRAFTING_TABLE, "modify_danmaku"))
                    .criterion("modified_danmaku", DanmakuModifiedCriterion.Conditions.create())
                    .build(consumer, makeName("modify_danmaku"));

            Advancement curtainFire = Advancement.Builder.create()
                    .parent(modifyDanmaku)
                    .display(makeDisplay(AdvancementFrame.GOAL, coloredBullet(ModItems.CIRCLE_SHOT, ColorMap.getColorInt("magenta")), "curtain_fire"))
                    .criterion("shot_danmaku", ItemDurabilityChangedCriterion.Conditions.create(ItemPredicate.Builder.create()
                            .tag(ModTags.Items.SHOTS).nbt(densityNbt()).build(), NumberRange.IntRange.ANY))
                    .build(consumer, makeName("curtain_fire"));

            Advancement findAbandonedShrine = Advancement.Builder.create()
                    .parent(rootAdvancement)
                    .display(makeDisplay(AdvancementFrame.TASK, ModItems.MYSTERIOUS_SEAL, "find_abandoned_shrine"))
                    .criterion(ModStructures.ABANDONED_SHRINE.getValue().getPath(), TickCriterion.Conditions.createLocation(LocationPredicate.feature(ModStructures.ABANDONED_SHRINE)))
                    .build(consumer, makeName("find_abandoned_shrine"));

            requirePatternItemsCollected(Advancement.Builder.create())
                    .parent(modifyDanmaku)
                    .display(makeDisplay(AdvancementFrame.CHALLENGE, ModItems.RING_PATTERN, "pattern_master"))
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .build(consumer, makeName("pattern_master"));

            Advancement sniperDuelFairy = Advancement.Builder.create()
                    .parent(shootDanmaku)
                    .display(makeDisplay(AdvancementFrame.CHALLENGE, coloredBullet(ModItems.STAR_SHOT, ColorMap.getColorInt("cyan")), "sniper_duel_fairy"))
                    .criterion("killed_fairy", OnKilledCriterion.Conditions.createPlayerKilledEntity(EntityPredicate.Builder.create()
                            .type(ModTags.EntityTypes.FAIRIES).distance(DistancePredicate.horizontal(NumberRange.FloatRange.atLeast(50.0))),
                            DamageSourcePredicate.Builder.create().tag(TagPredicate.expected(ModTags.DamageTypes.IS_DANMAKU))))
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .build(consumer, makeName("sniper_duel_fairy"));

            Advancement cancelBullets = Advancement.Builder.create()
                    .parent(rootAdvancement)
                    .display(makeDisplay(AdvancementFrame.TASK, ModItems.STAR_ITEM, "cancel_bullets"))
                    .criterion("cancelled_bullets", BulletsCancelledCriterion.Conditions.create(false))
                    .build(consumer, makeName("cancel_bullets"));

            Advancement cancelManyBullets = Advancement.Builder.create()
                    .parent(cancelBullets)
                    .display(makeDisplay(AdvancementFrame.GOAL, ModItems.BOMB_ITEM, "cancel_many_bullets"))
                    .criterion("cancelled_bullets", BulletsCancelledCriterion.Conditions.amount(NumberRange.IntRange.atLeast(50)))
                    .build(consumer, makeName("cancel_many_bullets"));

            Advancement hisouSword = Advancement.Builder.create()
                    .parent(ritualCrafting)
                    .display(makeDisplay(AdvancementFrame.TASK, ModItems.HISOU_SWORD, ModItems.HISOU_SWORD.toString()))
                    .criterion(ModItems.HISOU_SWORD.toString(), InventoryChangedCriterion.Conditions.items(ModItems.HISOU_SWORD))
                    .build(consumer, makeName(ModItems.HISOU_SWORD.toString()));

            Advancement obtainHeavenlyPeach = Advancement.Builder.create()
                    .parent(hisouSword)
                    .display(makeDisplay(AdvancementFrame.GOAL, ModItems.HEAVENLY_PEACH, "obtain_heavenly_peach"))
                    .criterion(ModItems.HEAVENLY_PEACH.toString(), InventoryChangedCriterion.Conditions.items(ModItems.HEAVENLY_PEACH))
                    .build(consumer, makeName("obtain_heavenly_peach"));

            Advancement mochiMalletKills = Advancement.Builder.create()
                    .parent(ritualCrafting)
                    .display(makeDisplay(AdvancementFrame.TASK, getMallet(), "mochi_mallet_kills"))
                    .criterion("kills_accumulated", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create()
                            .items(ModItems.MOCHI_MALLET).nbt(getKillsNbt()).build()))
                    .build(consumer, makeName("mochi_mallet_kills"));

            Advancement obtainHihiirokaneIngot = Advancement.Builder.create()
                    .parent(ritualCrafting)
                    .display(makeDisplay(AdvancementFrame.TASK, ModItems.HIHIIROKANE_INGOT, "obtain_hihiirokane_ingot"))
                    .criterion(ModItems.HIHIIROKANE_INGOT.toString(), InventoryChangedCriterion.Conditions.items(ModItems.HIHIIROKANE_INGOT))
                    .build(consumer, makeName("obtain_hihiirokane_ingot"));

            Advancement hihiirokaneArmor = Advancement.Builder.create()
                    .parent(obtainHihiirokaneIngot)
                    .display(makeDisplay(AdvancementFrame.CHALLENGE, ModItems.HIHIIROKANE_CHESTPLATE, "hihiirokane_armor"))
                    .criterion("hihiirokane_armor", InventoryChangedCriterion.Conditions.items(ModItems.HIHIIROKANE_HELMET,
                            ModItems.HIHIIROKANE_CHESTPLATE, ModItems.HIHIIROKANE_LEGGINGS, ModItems.HIHIIROKANE_BOOTS))
                    .rewards(AdvancementRewards.Builder.experience(200))
                    .build(consumer, makeName("hihiirokane_armor"));

            Advancement obtainHihiirokaneHoe = Advancement.Builder.create()
                    .parent(obtainHihiirokaneIngot)
                    .display(makeDisplay(AdvancementFrame.CHALLENGE, ModItems.HIHIIROKANE_HOE, "obtain_hihiirokane_hoe"))
                    .criterion(ModItems.HIHIIROKANE_HOE.toString(), InventoryChangedCriterion.Conditions.items(ModItems.HIHIIROKANE_HOE))
                    .rewards(AdvancementRewards.Builder.experience(150))
                    .build(consumer, makeName("obtain_hihiirokane_hoe"));
        }

        private static NbtCompound densityNbt() {
            NbtCompound nbt = new NbtCompound();
            nbt.putInt("density", 25);
            return nbt;
        }

        private static ItemStack coloredBullet(Item item, int color) {
            ItemStack stack = new ItemStack(item);
            ((DyeableBullet) stack.getItem()).setColor(stack, color);
            return stack;
        }

        private static Advancement.Builder requirePatternItemsCollected(Advancement.Builder builder) {
            for (Item item : PATTERN_ITEMS) {
                builder.criterion(Registries.ITEM.getId(item).getPath(), InventoryChangedCriterion.Conditions.items(item));
            }
            return builder;
        }

        private static ItemStack getMallet() {
            ItemStack stack = new ItemStack(ModItems.MOCHI_MALLET);
            stack.setNbt(getKillsNbt());
            return stack;
        }

        private static NbtCompound getKillsNbt() {
            NbtCompound nbt = new NbtCompound();
            nbt.putInt("kill_count", 100);
            return nbt;
        }
    }
}
