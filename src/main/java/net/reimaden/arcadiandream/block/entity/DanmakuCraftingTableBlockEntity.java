/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block.entity;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.gui.DanmakuCraftingScreenHandler;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseShotItem;
import net.reimaden.arcadiandream.item.custom.danmaku.BulletCoreItem;
import net.reimaden.arcadiandream.networking.ModMessages;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DanmakuCraftingTableBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory, SidedInventory {

    public static final int SIZE = 7;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);

    public DanmakuCraftingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DANMAKU_CRAFTING_TABLE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public void setInventory(DefaultedList<ItemStack> list) {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, list.get(i));
        }
    }

    private void updateResult() {
        /* Inventory slot indexes
         * 0 = Core
         * 1 = Shot
         * 2 = Result
         * 3 = Modifier
         * 4 = Repair
         * 5 = Pattern
         * 6 = Color
         */

        if (!isValid()) {
            items.set(2, ItemStack.EMPTY);
            return;
        }

        if (craftingShot()) {
            craftShot();
        } else if (modifyingShot()) {
            modifyShot();
        }
    }

    private void craftShot() {
        if (!(items.get(0).getItem() instanceof BulletCoreItem) || !items.get(3).isOf(ModItems.POWER_ITEM)) {
            items.set(2, ItemStack.EMPTY);
            return;
        }

        // Get the bullet type of the core
        String itemName = items.get(0).getItem().getTranslationKey();
        int underscoreIndex = itemName.indexOf("_");
        String bulletCoreType = itemName.substring(itemName.lastIndexOf(".") + 1, underscoreIndex);
        String shotType = bulletCoreType + "_shot";

        // Set the output shot to the bullet type
        ItemStack shotStack = new ItemStack(Registries.ITEM.get(new Identifier(ArcadianDream.MOD_ID, shotType)));
        items.set(2, shotStack);
    }

    private void modifyShot() {
        if (!(items.get(1).getItem() instanceof BaseShotItem)) {
            items.set(2, ItemStack.EMPTY);
            return;
        }

        ItemStack modifiedShot = items.get(1).copy();
        modifyShot(modifiedShot);
        items.set(2, modifiedShot);
    }

    private void modifyShot(ItemStack stack) {
        if (items.get(5).isEmpty()) {
            return;
        }

        ImmutableList<Item> patternItems = ImmutableList.of(
                ModItems.SPREAD_PATTERN, ModItems.RAY_PATTERN, ModItems.RING_PATTERN
        );

        Map<Item, Integer> itemMap = new HashMap<>();
        for (int i = 0; i < patternItems.size(); i++) {
            itemMap.put(patternItems.get(i), i);
        }

        int itemId = itemMap.getOrDefault(items.get(5).getItem(), 0);

        switch (itemId) {
            case 0 -> stack.getOrCreateNbt().putString("pattern", "spread");
            case 1 -> stack.getOrCreateNbt().putString("pattern", "ray");
            case 2 -> stack.getOrCreateNbt().putString("pattern", "ring");
            default -> throw new IllegalArgumentException("No valid bullet pattern found!");
        }
    }

    private boolean craftingShot() {
        return !items.get(0).isEmpty() && items.get(1).isEmpty();
    }

    private boolean modifyingShot() {
        return items.get(0).isEmpty() && !items.get(1).isEmpty();
    }

    private boolean isValid() {
        return craftingShot() || modifyingShot();
    }

    @Override
    public void markDirty() {
        if (world != null && !world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(items.size());
            for (ItemStack item : items) {
                data.writeItemStack(item);
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.ITEM_SYNC, data);
            }
        }

        updateResult();
        super.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (world != null && world.getBlockEntity(pos) != this) {
            return false;
        }
        return !(player.squaredDistanceTo((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5) > 64.0);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new DanmakuCraftingScreenHandler(syncId, inventory, this);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }
}
