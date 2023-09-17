/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class OnbashiraPillarBlock extends Block implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    public OnbashiraPillarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final Item paper = Items.PAPER;
        final Item lead = Items.LEAD;

        boolean hasItems = (player.getMainHandStack().isOf(paper) && player.getOffHandStack().isOf(lead))
                || (player.getMainHandStack().isOf(lead) && player.getOffHandStack().isOf(paper));

        if (world.isClient()) return hasItems ? ActionResult.SUCCESS : ActionResult.PASS;

        if (hasItems) {
            if (!player.isCreative()) {
                player.getMainHandStack().decrement(1);
                player.getOffHandStack().decrement(1);
            }
            world.setBlockState(pos, ModBlocks.ONBASHIRA.getStateWithProperties(state));
            player.incrementStat(Stats.CRAFTED.getOrCreateStat(ModBlocks.ONBASHIRA.asItem()));
            world.playSound(null, pos, ModSounds.BLOCK_ONBASHIRA_CREATE, SoundCategory.BLOCKS, 0.8f, 1.2f);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
