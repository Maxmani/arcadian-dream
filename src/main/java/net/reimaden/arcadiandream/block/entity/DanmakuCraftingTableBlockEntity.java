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
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
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
import net.reimaden.arcadiandream.util.ColorMap;
import net.reimaden.arcadiandream.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DanmakuCraftingTableBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory, SidedInventory {

    public static final int SIZE = 7;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int modifierCount = 0;
    private int repairCount = 0;

    public DanmakuCraftingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DANMAKU_CRAFTING_TABLE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> modifierCount;
                    case 1 -> repairCount;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> modifierCount = value;
                    case 1 -> repairCount = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
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

        propertyDelegate.set(0, 0);
        propertyDelegate.set(1, 0);

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
        if (!(items.get(0).getItem() instanceof BulletCoreItem) || !items.get(3).isOf(ModItems.BIG_POWER_ITEM)) {
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

        shotStack.getOrCreateNbt();
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

    // I am too afraid to optimize this
    // TODO: Add gravity and divergence modifiers
    private void modifyShot(ItemStack stack) {
        BaseShotItem shot = (BaseShotItem) stack.getItem();

        if (!items.get(3).isEmpty()) {
            ItemStack modifierStack = items.get(3);
            int count = modifierStack.getCount();
            float percentage = 0.05f;
            float reductionPercentage = 0.02f;

            double maxPower, maxSpeed;
            int maxDuration;
            maxPower = shot.getMaxPower();
            maxSpeed = shot.getMaxSpeed();
            maxDuration = shot.getMaxDuration();

            applyModifiers(stack, shot, modifierStack, count, percentage, reductionPercentage, maxPower, maxSpeed, maxDuration);
        }

        if (!items.get(4).isEmpty()) {
            int repairAmount = 50;

            for (int i = 0; i < items.get(4).getCount(); i++) {
                if (stack.getDamage() <= 0) {
                    break;
                }

                stack.setDamage(stack.getDamage() - repairAmount);
                propertyDelegate.set(1, propertyDelegate.get(1) + 1);
            }
        }

        if (!items.get(5).isEmpty()) {
            ImmutableList<Item> patternItems = ImmutableList.of(
                    ModItems.SPREAD_PATTERN, ModItems.RAY_PATTERN, ModItems.RING_PATTERN, ModItems.CONE_PATTERN,
                    ModItems.DOUBLE_PATTERN, ModItems.TRIPLE_PATTERN
            );

            Map<Item, Integer> itemMap = new HashMap<>();
            for (int i = 0; i < patternItems.size(); i++) {
                itemMap.put(patternItems.get(i), i);
            }

            int itemId = itemMap.getOrDefault(items.get(5).getItem(), 0);

            switch (itemId) {
                case 0 -> shot.setPattern(stack, "spread");
                case 1 -> shot.setPattern(stack, "ray");
                case 2 -> shot.setPattern(stack, "ring");
                case 3 -> shot.setPattern(stack, "cone");
                case 4 -> shot.setPattern(stack, "double");
                case 5 -> shot.setPattern(stack, "triple");
                default -> throw new IllegalArgumentException("No valid bullet pattern found!");
            }
        }

        if (!items.get(6).isEmpty()) {
            Item dye = items.get(6).getItem();
            if (dye instanceof DyeItem) {
                int colorInt = ColorMap.getColorInt(((DyeItem) dye).getColor().getName());
                shot.setColor(stack, colorInt);
            }
        }
    }

    private void applyModifiers(ItemStack stack, BaseShotItem shot, ItemStack modifierStack, int count, float percentage, float reductionPercentage, double maxPower, double maxSpeed, int maxDuration) {
        if (modifierStack.isIn(ModTags.Items.DANMAKU_POWER_MODIFIERS)) {
            maxPower *= 2;

            for (int i = 0; i < count; i++) {
                if (shot.getPower(stack) >= shot.getMaxPower()) {
                    break;
                }

                double power = shot.getPower(stack);
                double result = power * (1 + percentage * (1 - (power / maxPower)));
                shot.setPower(stack, (float) result);

                shot.setCooldown(stack, shot.getCooldown(stack) + 1);
                double speed = shot.getSpeed(stack);
                double resultReductionSpeed = speed * (1 - reductionPercentage * (speed / maxSpeed));
                int duration = shot.getDuration(stack);
                double resultReductionDuration = duration * (1 - reductionPercentage * (duration / (double) maxDuration));
                shot.setSpeed(stack, Math.max((float) resultReductionSpeed, 0.05f));
                shot.setDuration(stack, Math.max((int) resultReductionDuration, 20));

                propertyDelegate.set(0, propertyDelegate.get(0) + 1);
            }
        } else if (modifierStack.isIn(ModTags.Items.DANMAKU_DENSITY_MODIFIERS)) {
            for (int i = 0; i < count; i++) {
                if (shot.getDensity(stack) >= shot.getMaxDensity()) {
                    break;
                }

                shot.setDensity(stack, shot.getDensity(stack) + 1);

                shot.setCooldown(stack, shot.getCooldown(stack) + 1);
                double resultReductionPower = shot.getPower(stack) * (1 - reductionPercentage * (shot.getPower(stack) / maxPower));
                double speed = shot.getSpeed(stack);
                double resultReductionSpeed = speed * (1 - reductionPercentage * (speed / maxSpeed));
                int duration = shot.getDuration(stack);
                double resultReductionDuration = duration * (1 - reductionPercentage * (duration / (double) maxDuration));
                shot.setPower(stack, Math.max((float) resultReductionPower, 0.1f));
                shot.setSpeed(stack, Math.max((float) resultReductionSpeed, 0.05f));
                shot.setDuration(stack, Math.max((int) resultReductionDuration, 20));

                propertyDelegate.set(0, propertyDelegate.get(0) + 1);
            }
        } else if (modifierStack.isIn(ModTags.Items.DANMAKU_SPEED_MODIFIERS)) {
            maxSpeed *= 2;

            for (int i = 0; i < count; i++) {
                if (shot.getSpeed(stack) >= shot.getMaxSpeed()) {
                    break;
                }

                double speed = shot.getSpeed(stack);
                double result = speed * (1 + percentage * (1 - (speed / maxSpeed)));
                shot.setSpeed(stack, (float) result);

                shot.setCooldown(stack, shot.getCooldown(stack) + 1);
                double resultReductionPower = shot.getPower(stack) * (1 - reductionPercentage * (shot.getPower(stack) / maxPower));
                int duration = shot.getDuration(stack);
                double resultReductionDuration = duration * (1 - reductionPercentage * (duration / (double) maxDuration));
                shot.setPower(stack, Math.max((float) resultReductionPower, 0.1f));
                shot.setDuration(stack, Math.max((int) resultReductionDuration, 20));

                propertyDelegate.set(0, propertyDelegate.get(0) + 1);
            }
        } else if (modifierStack.isIn(ModTags.Items.DANMAKU_DURATION_MODIFIERS)) {
            maxDuration *= 2;

            for (int i = 0; i < count; i++) {
                if (shot.getDuration(stack) >= shot.getMaxDuration()) {
                    break;
                }

                double duration = shot.getDuration(stack);
                double result = duration * (1 + percentage * (1 - (duration / maxDuration)));
                result = Math.ceil(result);
                shot.setDuration(stack, (int) result);

                shot.setCooldown(stack, shot.getCooldown(stack) + 1);
                double resultReductionPower = shot.getPower(stack) * (1 - reductionPercentage * (shot.getPower(stack) / maxPower));
                double speed = shot.getSpeed(stack);
                double resultReductionSpeed = speed * (1 - reductionPercentage * (speed / maxSpeed));
                shot.setPower(stack, Math.max((float) resultReductionPower, 0.1f));
                shot.setSpeed(stack, Math.max((float) resultReductionSpeed, 0.05f));

                propertyDelegate.set(0, propertyDelegate.get(0) + 1);
            }
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
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        nbt.putInt("modifierCount", modifierCount);
        nbt.putInt("repairCount", repairCount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
        modifierCount = nbt.getInt("modifierCount");
        repairCount = nbt.getInt("repairCount");
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
        return new DanmakuCraftingScreenHandler(syncId, inventory, this, propertyDelegate);
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
