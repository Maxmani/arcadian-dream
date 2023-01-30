/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.gui;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.reimaden.arcadiandream.block.entity.DanmakuCraftingTableBlockEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseShotItem;
import net.reimaden.arcadiandream.item.custom.danmaku.BulletCoreItem;

public class DanmakuCraftingScreenHandler extends ScreenHandler {

    private static final int SIZE = DanmakuCraftingTableBlockEntity.SIZE;

    private final Inventory inventory;

    public DanmakuCraftingScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, new SimpleInventory(SIZE));
    }

    public DanmakuCraftingScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.DANMAKU_CRAFTING_SCREEN_HANDLER, syncId);
        checkSize(inventory, SIZE);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        // Set some helper variables
        final int topY = 32;
        final int bottomY = 56;

        /* Inventory slot indexes
         * 0 = Core
         * 1 = Shot
         * 2 = Result
         * 3 = Modifier
         * 4 = Repair
         * 5 = Pattern
         * 6 = Color
         */

        // Create slots
        DanmakuCraftingSlot coreSlot = new DanmakuCraftingSlot(inventory, 0, 15, topY, BulletCoreItem.class);
        DanmakuCraftingSlot shotSlot = new DanmakuCraftingSlot(inventory, 1, 39, topY, BaseShotItem.class);
        DanmakuOutputSlot resultSlot = new DanmakuOutputSlot(inventory, 2, 136, 44);
        Slot modifierSlot = new Slot(inventory, 3, 27, bottomY);
        DanmakuCraftingSlot repairSlot = new DanmakuCraftingSlot(inventory, 4, 80, topY, ModItems.FAITH_ITEM);
        Slot patternSlot = new Slot(inventory, 5, 68, bottomY);
        DanmakuCraftingSlot colorSlot = new DanmakuCraftingSlot(inventory, 6, 92, bottomY, ConventionalItemTags.DYES);

        // Add slots to screen
        addSlot(coreSlot);
        addSlot(shotSlot);
        addSlot(resultSlot);
        addSlot(modifierSlot);
        addSlot(repairSlot);
        addSlot(patternSlot);
        addSlot(colorSlot);

        // Add player inventory
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 105 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 163));
        }
    }

    public boolean hasStack(Slot slot) {
        return slot.hasStack();
    }

    static class DanmakuOutputSlot extends Slot {

        public DanmakuOutputSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }
    }

    static class DanmakuCraftingSlot extends Slot {

        private Item insertItem;
        private TagKey<Item> insertTag;
        private Class<?> insertClass;

        public DanmakuCraftingSlot(Inventory inventory, int index, int x, int y, Item insertItem) {
            super(inventory, index, x, y);
            this.insertItem = insertItem;
        }

        public DanmakuCraftingSlot(Inventory inventory, int index, int x, int y, TagKey<Item> insertTag) {
            super(inventory, index, x, y);
            this.insertTag = insertTag;
        }

        public DanmakuCraftingSlot(Inventory inventory, int index, int x, int y, Class<?> insertClass) {
            super(inventory, index, x, y);
            this.insertClass = insertClass;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return (insertItem != null && stack.isOf(insertItem)) ||
                    (insertClass != null && insertClass.isInstance(stack.getItem())) ||
                    (insertTag != null && stack.isIn(insertTag));
        }
    }
}
