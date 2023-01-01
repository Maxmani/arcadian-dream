/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 *
 * Credits to ArekkuusuJerii for the original code.
 * https://github.com/TeamNightclipse/Grimoire-Of-Alice/blob/1.12/src/main/java/arekkuusu/grimoireofalice/common/item/ItemWallPassingChisel.java
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */

package net.reimaden.arcadiandream.item.custom.misc;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.ModTags;
import org.jetbrains.annotations.Nullable;

public class WallPassingChiselItem extends Item {

    public WallPassingChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        Hand hand = context.getHand();
        Direction facing = context.getSide();
        ItemStack stack = context.getStack();

        BlockPos travel = travelPos(world, pos, facing);

        if (ArcadianDream.CONFIG.chiselOptions.canUse()) {
            if (world.isClient) return travel != null ? ActionResult.SUCCESS : ActionResult.PASS;

            if (player != null) {
                if (travel != null && isSafePos(world, travel.up())) {
                    player.setPosition(travel.getX() + 0.5, travel.getY(), travel.getZ() + 0.5);
                    player.refreshPositionAfterTeleport(player.getPos());

                    stack.damage(1, player, e -> e.sendToolBreakStatus(hand));

                    world.playSound(null, travel.getX(), travel.getY(), travel.getZ(), ModSounds.ITEM_WALL_PASSING_CHISEL_USE,
                            player.getSoundCategory(), 0.4f, world.random.nextFloat() * 0.4f + 0.8f);
                    player.getItemCooldownManager().set(this, 60);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }

    @Nullable
    private static BlockPos travelPos(World world, BlockPos pos, Direction facing) {
        facing = facing.getOpposite();

        // Minimum value of two in order to go through a one block wide wall
        for (int i = 0; i < ArcadianDream.CONFIG.chiselOptions.maxDistance() + 1; i++) {
            BlockState state = world.getBlockState(pos);
            if (!state.getBlock().getDefaultState().isIn(ModTags.Blocks.OBSIDIAN_BLOCKS) && state.getBlock().getHardness() >= 0.0f) {
                if (isSafePos(world, pos)) {
                    if (facing == Direction.DOWN) {
                        if (!isSafePos(world, pos.offset(facing))) {
                            return null;
                        }
                        pos = pos.offset(facing);
                    } else if (facing != Direction.UP && isSafePos(world, pos.down())) {
                        pos = pos.down();
                    }

                    return pos;
                } else {
                    pos = pos.offset(facing);
                }
            }
        }

        return null;
    }

    private static boolean isSafePos(World world, BlockPos pos) {
        return world.getBlockState(pos).isAir();
    }
}
