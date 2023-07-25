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
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.reimaden.arcadiandream.advancement.ModCriteria;
import net.reimaden.arcadiandream.block.entity.DanmakuCraftingTableBlockEntity;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseShotItem;
import net.reimaden.arcadiandream.item.custom.danmaku.BulletCoreItem;
import net.reimaden.arcadiandream.item.custom.danmaku.PatternItem;
import net.reimaden.arcadiandream.util.ModTags;

public class DanmakuCraftingScreenHandler extends ScreenHandler {

    private static final int SIZE = DanmakuCraftingTableBlockEntity.SIZE;

    private final Inventory inventory;

    public DanmakuCraftingScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, new SimpleInventory(SIZE), new ArrayPropertyDelegate(2));
    }

    public DanmakuCraftingScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
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
        DanmakuOutputSlot resultSlot = new DanmakuOutputSlot(playerInventory.player, inventory, 2, 136, 44, propertyDelegate);
        DanmakuCraftingSlot modifierSlot = new DanmakuCraftingSlot(inventory, 3, 27, bottomY, ModTags.Items.DANMAKU_MODIFIERS);
        DanmakuCraftingSlot repairSlot = new DanmakuCraftingSlot(inventory, 4, 80, topY, ModTags.Items.DANMAKU_REPAIR_ITEMS);
        DanmakuCraftingSlot patternSlot = new DanmakuCraftingSlot(inventory, 5, 68, bottomY, PatternItem.class);
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

        addProperties(propertyDelegate);
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
                if (slot instanceof DanmakuOutputSlot) {
                    slot.onTakeItem(player, newStack);
                }
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

        private final PlayerEntity player;
        private final PropertyDelegate propertyDelegate;

        public DanmakuOutputSlot(PlayerEntity player, Inventory inventory, int index, int x, int y, PropertyDelegate propertyDelegate) {
            super(inventory, index, x, y);
            this.player = player;
            this.propertyDelegate = propertyDelegate;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        public void onTakeItem(PlayerEntity player, ItemStack stack) {
            super.onTakeItem(player, stack);

            onCrafted(stack);

            // Clear the input slots
            craft(inventory, propertyDelegate, player);
            propertyDelegate.set(0, 0);
            propertyDelegate.set(1, 0);
        }

        @Override
        protected void onCrafted(ItemStack stack) {
            int count = stack.getCount();

            // Don't run the onCraft method if the shot is being modified
            if (inventory.getStack(1).isEmpty()) {
                stack.onCraft(player.getWorld(), player, count);
            }
        }
    }

    static class DanmakuCraftingSlot extends Slot {

        private TagKey<Item> insertTag;
        private Class<?> insertClass;

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
            return (insertClass != null && insertClass.isInstance(stack.getItem())) ||
                    (insertTag != null && stack.isIn(insertTag));
        }
    }

    private static void craft(Inventory inventory, PropertyDelegate propertyDelegate, PlayerEntity player) {
        if (player.getWorld().isClient()) return;

        /* Inventory slot indexes
         * 0 = Core
         * 1 = Shot
         * 2 = Result
         * 3 = Modifier
         * 4 = Repair
         * 5 = Pattern
         * 6 = Color
         */

        ItemStack coreStack = inventory.getStack(0);
        ItemStack shotStack = inventory.getStack(1);
        ItemStack modifierStack = inventory.getStack(3);
        ItemStack repairStack = inventory.getStack(4);
        ItemStack patternStack = inventory.getStack(5);
        ItemStack colorStack = inventory.getStack(6);

        // If creating a shot from a bullet core, clear the modifier slot too
        if (coreStack.getItem() instanceof BulletCoreItem) {
            modifierStack.decrement(1);
        }

        // If modifying a shot, clear any modifier slots too
        if (shotStack.getItem() instanceof BaseShotItem) {
            modifierStack.decrement(propertyDelegate.get(0));
            repairStack.decrement(propertyDelegate.get(1));

            // Trigger advancement for modifying a shot
            if (propertyDelegate.get(0) > 0 || !patternStack.isEmpty() || !colorStack.isEmpty()) { // Make sure we are actually modifying a shot
                ModCriteria.DANMAKU_MODIFIED.trigger((ServerPlayerEntity) player);
            }

            patternStack.decrement(1);
            colorStack.decrement(1);
        }

        // Clear the input slots
        coreStack.decrement(1);
        shotStack.decrement(1);

        // Dirty fix to prevent a dupe
        if (coreStack.isEmpty() && shotStack.isEmpty()) {
            inventory.getStack(2).decrement(1);
        }
    }
}
