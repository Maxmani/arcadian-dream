/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.entity.DanmakuCraftingTableBlockEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseShotItem;
import net.reimaden.arcadiandream.item.custom.danmaku.BulletCoreItem;

public class DanmakuCraftingGuiDescription extends SyncedGuiDescription {

    private static final int INVENTORY_SIZE = DanmakuCraftingTableBlockEntity.SIZE;
    private static final TextureIcon CORE_ICON = new TextureIcon(new Identifier(ArcadianDream.MOD_ID, "textures/gui/danmaku_crafting/bullet_core_slot.png"));
    private static final TextureIcon SHOT_ICON = new TextureIcon(new Identifier(ArcadianDream.MOD_ID, "textures/gui/danmaku_crafting/shot_slot.png"));
    private static final TextureIcon FAITH_ICON = new TextureIcon(new Identifier(ArcadianDream.MOD_ID, "textures/gui/danmaku_crafting/faith_slot.png"));
    private static final TextureIcon PATTERN_ICON = new TextureIcon(new Identifier(ArcadianDream.MOD_ID, "textures/gui/danmaku_crafting/pattern_slot.png"));
    private static final TextureIcon DYE_ICON = new TextureIcon(new Identifier(ArcadianDream.MOD_ID, "textures/gui/danmaku_crafting/dye_slot.png"));

    public DanmakuCraftingGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreens.DANMAKU_CRAFTING, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context));

        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        setTitleAlignment(HorizontalAlignment.CENTER);

        root.setSize(162, 173);
        root.setInsets(Insets.ROOT_PANEL);

        // Create slots
        WItemSlot coreSlot = WItemSlot.of(blockInventory, 0)
                .setIcon(CORE_ICON)
                .setFilter(this::isCore);
        WItemSlot shotSlot = WItemSlot.of(blockInventory, 1)
                .setIcon(SHOT_ICON)
                .setFilter(this::isShot);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, 2)
                .setInsertingAllowed(false);
        WItemSlot modifierSlot = WItemSlot.of(blockInventory, 3);
        WItemSlot repairSlot = WItemSlot.of(blockInventory, 4)
                .setIcon(FAITH_ICON)
                .setFilter(this::isFaith);
        WItemSlot patternSlot = WItemSlot.of(blockInventory, 5)
                .setIcon(PATTERN_ICON);
        WItemSlot colorSlot = WItemSlot.of(blockInventory, 6)
                .setIcon(DYE_ICON)
                .setFilter(this::isDye);

        // Set some helper variables for positioning
        final int topY = 25;
        final int bottomY = 49;

        // Add slots
        root.add(coreSlot, 7, topY);
        root.add(shotSlot, 31, topY);
        root.add(outputSlot, 128, 37);
        root.add(modifierSlot, 19, bottomY);
        root.add(repairSlot, 72, topY);
        root.add(patternSlot, 60, bottomY);
        root.add(colorSlot, 84, bottomY);

        // TODO: Implement the crafting logic
        updateIcon(coreSlot, CORE_ICON);
        updateIcon(shotSlot, SHOT_ICON);
        updateIcon(patternSlot, PATTERN_ICON);
        updateIcon(colorSlot, DYE_ICON);

        WPlayerInvPanel playerInvPanel = createPlayerInventoryPanel();
        root.add(playerInvPanel, 0, 86);

        root.validate(this);
    }

    private void updateIcon(WItemSlot itemSlot, TextureIcon icon) {
        itemSlot.addChangeListener((slot, inventory, index, stack) -> slot.setIcon(stack.isEmpty() ? icon : null));
    }

    private boolean isCore(ItemStack stack) {
        return stack.getItem() instanceof BulletCoreItem;
    }

    private boolean isShot(ItemStack stack) {
        return stack.getItem() instanceof BaseShotItem;
    }

    private boolean isFaith(ItemStack stack) {
        return stack.getItem() == ModItems.FAITH_ITEM;
    }

    private boolean isDye(ItemStack stack) {
        return stack.isIn(ConventionalItemTags.DYES);
    }
}
