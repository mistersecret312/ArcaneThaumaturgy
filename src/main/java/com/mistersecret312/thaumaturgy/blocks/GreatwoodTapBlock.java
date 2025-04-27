package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.CrucibleBlockEntity;
import com.mistersecret312.thaumaturgy.block_entities.GreatwoodTapBlockEntity;
import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mistersecret312.thaumaturgy.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class GreatwoodTapBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty SAP = BooleanProperty.create("sap");

    public static final VoxelShape SHAPE_NORTH = MathUtil.buildShape(
            Block.box(6, 4, 11, 10, 10, 16)
    );
    public static final VoxelShape SHAPE_SOUTH = MathUtil.buildShape(
            Block.box(6, 4, 0, 10, 10, 5)
    );
    public static final VoxelShape SHAPE_WEST = MathUtil.buildShape(
            Block.box(11, 4, 6, 16, 10, 10)
    );
    public static final VoxelShape SHAPE_EAST = MathUtil.buildShape(
            Block.box(0, 4, 6, 5, 10, 10)
    );

    public GreatwoodTapBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(SAP, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        boolean sap = pState.getValue(SAP);

        if (sap) {
            if (itemStack.getItem() == Items.GLASS_BOTTLE) {
                if (!pPlayer.isCreative()) {
                    itemStack.shrink(1);
                }
                if (itemStack.isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(ItemInit.GREAT_SAP_BOTTLE.get()));
                } else if (!pPlayer.getInventory().add(new ItemStack(ItemInit.GREAT_SAP_BOTTLE.get()))) {
                    pPlayer.drop(new ItemStack(ItemInit.GREAT_SAP_BOTTLE.get()), false);
                }

                pLevel.playSound(pPlayer, pPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1, 1.25f);
            } else {
                if (itemStack.isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(ItemInit.GREAT_SAP.get()));
                } else if (!pPlayer.getInventory().add(new ItemStack(ItemInit.GREAT_SAP.get()))) {
                    pPlayer.drop(new ItemStack(ItemInit.GREAT_SAP.get()), false);
                }

                pLevel.playSound(pPlayer, pPos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1, 1.25f);
            }
            if (!pLevel.isClientSide) {
                pLevel.setBlock(pPos, pState.setValue(SAP, false), 2);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        switch (pState.getValue(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            case WEST:
                return SHAPE_WEST;
            default:
                return SHAPE_NORTH;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(WATERLOGGED, FACING, SAP);
        super.createBlockStateDefinition(blockStateBuilder);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos)
    {
        if (!isOnGreatwoodTree(pLevel, pPos, pState)) {
            pLevel.destroyBlock(pPos, true);
        }

        if (pState.getValue(WATERLOGGED))
        {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public boolean isOnGreatwoodTree(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        Direction tapDirection = pState.getValue(FACING);
        Block tappedBlock = pLevel.getBlockState(pPos.relative(tapDirection.getOpposite())).getBlock();
        return tappedBlock.equals(BlockInit.GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.GREATWOOD_WOOD.get()) || tappedBlock.equals(BlockInit.STRIPPED_GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_WOOD.get());
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        LevelAccessor accessor = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, accessor.getFluidState(pos).getType() == Fluids.WATER).setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(SAP, false);
    }

    @org.jetbrains.annotations.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityInit.GREATWOOD_TAP.get(), GreatwoodTapBlockEntity::tick);
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityInit.GREATWOOD_TAP.get().create(pos, state);
    }
}
