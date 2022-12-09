package net.reimaden.arcadiandream.block.custom;

import net.reimaden.arcadiandream.block.entity.ModBlockEntities;
import net.reimaden.arcadiandream.block.entity.OnbashiraBlockEntity;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.reimaden.arcadiandream.statistic.ModStats;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"deprecation", "ConstantConditions"})
public class OnbashiraBlock extends BlockWithEntity implements BlockEntityProvider, Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty HAS_ITEM = BooleanProperty.of("has_item");

    Random random = Random.createThreadSafe();

    public OnbashiraBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState()
                .with(WATERLOGGED, false)
                .with(HAS_ITEM, false));
    }

    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OnbashiraBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        ItemStack stack = player.getStackInHand(hand);
        boolean bl = !blockEntity.getStack(0).isEmpty();
        boolean bl2 = !stack.isEmpty();
        boolean bl3 = !bl && bl2;

        if (world.isClient) return bl || bl2 ? ActionResult.SUCCESS : ActionResult.PASS;

        if (bl3 && world.getBlockState(pos.up()).isAir()) {
            blockEntity.setStack(0, player.getStackInHand(hand).copy().split(1));
            if (!player.isCreative()) {
                player.getStackInHand(hand).decrement(1);
            }
            blockEntity.markDirty();
            world.playSound(null, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, ModSounds.BLOCK_ONBASHIRA_ADD_ITEM, SoundCategory.BLOCKS,
                    1.0f, 1.0f);
            player.incrementStat(ModStats.INTERACT_WITH_ONBASHIRA);
            return ActionResult.SUCCESS;
        } else if (bl) {
            player.getInventory().offerOrDrop(blockEntity.getStack(0));
            blockEntity.removeStack(0);
            blockEntity.markDirty();
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, player.getSoundCategory(),
                    0.2f, this.random.nextFloat() - this.random.nextFloat() * 1.4f + 2.0f);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof Inventory) {
            ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER)
                .with(HAS_ITEM, false);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, HAS_ITEM);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.ONBASHIRA, OnbashiraBlockEntity::tick);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(HAS_ITEM) ? 15 : 0;
    }
}
