/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.reimaden.arcadiandream.advancement.ModCriteria;
import net.reimaden.arcadiandream.block.custom.RitualShrineBlock;
import net.reimaden.arcadiandream.networking.ModMessages;
import net.reimaden.arcadiandream.recipe.RitualCraftingRecipe;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.statistic.ModStats;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RitualShrineBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory {

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public ItemStack getRenderStack() {
        return this.getStack(0);
    }

    public void setInventory(DefaultedList<ItemStack> list) {
        for (int i = 0; i < items.size(); i++) {
            this.items.set(i, list.get(i));
        }
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
            world.setBlockState(pos, world.getBlockState(pos).with(RitualShrineBlock.HAS_ITEM, hasItemStack()));
        }

        super.markDirty();
    }

    public static final BlockPos[] ONBASHIRA_LOCATIONS = {
            new BlockPos(0, 0, -4),
            new BlockPos(3, 0, -3),
            new BlockPos(4, 0, 0),
            new BlockPos(3, 0, 3),
            new BlockPos(0, 0, 4),
            new BlockPos(-3, 0, 3),
            new BlockPos(-4, 0, 0),
            new BlockPos(-3, 0, -3)
    };
    public static final BlockPos[] SECOND_ONBASHIRA_LOCATIONS = {
            new BlockPos(-2, 1, -5),
            new BlockPos(2, 1, -5),
            new BlockPos(5, 1, -2),
            new BlockPos(5, 1, 2),
            new BlockPos(2, 1, 5),
            new BlockPos(-2, 1, 5),
            new BlockPos(-5, 1, 2),
            new BlockPos(-5, 1, -2)
    };

    public void doCrafting(@Nullable PlayerEntity player) {
        if (world != null && !world.isClient()) {
            DynamicRegistryManager registryManager = world.getRegistryManager();

            List<OnbashiraBlockEntity> onbashiras = new ArrayList<>();
            for (BlockPos pos : ONBASHIRA_LOCATIONS) {
                getOnbashiraPos(pos.add(getPos())).ifPresent(onbashiras::add);
            }
            for (BlockPos pos : SECOND_ONBASHIRA_LOCATIONS) {
                getOnbashiraPos(pos.add(getPos())).ifPresent(onbashiras::add);
            }
            List<ItemStack> stacks = onbashiras.stream().filter(OnbashiraBlockEntity::hasItemStack).map(OnbashiraBlockEntity::getItemStack).toList();
            craftItem(stacks, onbashiras, this, player, registryManager);
        }
    }

    @SuppressWarnings({"DataFlowIssue", "OptionalGetWithoutIsPresent"})
    private void craftItem(List<ItemStack> stacks, List<OnbashiraBlockEntity> onbashiras, RitualShrineBlockEntity shrineBlock, PlayerEntity player, DynamicRegistryManager registryManager) {
        if (onbashiras.isEmpty()) return;

        SimpleInventory inventory = new SimpleInventory(stacks.size());
        for (int i = 0; i < stacks.size(); i++) {
            inventory.setStack(i, stacks.get(i));
        }

        OnbashiraBlockEntity onbashiraBlock = onbashiras.get(0);

        Optional<RitualCraftingRecipe> recipe = onbashiraBlock.getWorld().getRecipeManager()
                .getFirstMatch(RitualCraftingRecipe.Type.INSTANCE, inventory, onbashiraBlock.getWorld());

        if (hasRecipe(stacks, onbashiras)) {
            onbashiras.forEach(entity -> {
                if (!entity.isEmpty()) {
                    onbashiraEffects(entity);
                }
            });
            onbashiras.forEach(entity -> entity.setStack(0, ItemStack.EMPTY));
            onbashiras.forEach(OnbashiraBlockEntity::markDirty);

            final ItemStack output = recipe.get().getOutput(registryManager);
            shrineBlock.setStack(0, new ItemStack(output.getItem(), output.getCount()));
            shrineBlock.markDirty();
            craftEffects();
            player.incrementStat(ModStats.INTERACT_WITH_RITUAL_SHRINE);
            player.increaseStat(Stats.CRAFTED.getOrCreateStat(output.getItem()), output.getCount());
            ModCriteria.RITUAL_CRAFTING.trigger((ServerPlayerEntity) player);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    private boolean hasRecipe(List<ItemStack> stacks, List<OnbashiraBlockEntity> onbashiras) {
        SimpleInventory inventory = new SimpleInventory(stacks.size());
        for (int i = 0; i < stacks.size(); i++) {
            inventory.setStack(i, stacks.get(i));
        }

        OnbashiraBlockEntity onbashiraBlock = onbashiras.get(0);

        Optional<RitualCraftingRecipe> match = onbashiraBlock.getWorld().getRecipeManager()
                .getFirstMatch(RitualCraftingRecipe.Type.INSTANCE, inventory, onbashiraBlock.getWorld());

        return match.isPresent();
    }

    private void craftEffects() {
        if (world != null) {
            world.playSound(null, getPos(), ModSounds.BLOCK_RITUAL_SHRINE_USE, SoundCategory.BLOCKS, 1f, 0.8f + world.random.nextFloat() * 0.4f);
        }

        double d0 = pos.getX() + 0.5;
        double d1 = pos.getY() + 1.25;
        double d2 = pos.getZ() + 0.5;

        ((ServerWorld) world).spawnParticles(ParticleTypes.END_ROD, d0, d1, d2, 9,
                world.random.nextGaussian() * 0.005D, world.random.nextGaussian() * 0.005D, world.random.nextGaussian() * 0.005D, 0.05);
    }

    private void onbashiraEffects(OnbashiraBlockEntity onbashiraBlock) {
        if (!onbashiraBlock.getStack(0).isEmpty()) {
            if (world != null) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE,
                        onbashiraBlock.getPos().getX() + 0.5,
                        onbashiraBlock.getPos().getY() + 1.25,
                        onbashiraBlock.getPos().getZ() + 0.5,
                        5, 0, 0.1, 0, 0.01);
            }
        }
    }

    @SuppressWarnings("DataFlowIssue")
    private Optional<OnbashiraBlockEntity> getOnbashiraPos(BlockPos pos) {
        return Optional.ofNullable((OnbashiraBlockEntity) world.getBlockEntity(pos));
    }

    public RitualShrineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RITUAL_SHRINE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public boolean hasItemStack() {
        return !isEmpty();
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
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
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
