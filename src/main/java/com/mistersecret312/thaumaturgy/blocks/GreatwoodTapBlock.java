package com.mistersecret312.thaumaturgy.blocks;

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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class GreatwoodTapBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
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
                itemStack.shrink(1);
                pPlayer.addItem(new ItemStack(ItemInit.GREAT_SAP_BOTTLE.get(), 1));

                pLevel.playSound(pPlayer, pPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1, 1);
            } else {
                pPlayer.addItem(new ItemStack(ItemInit.GREAT_SAP.get(), 1));

                pLevel.playSound(pPlayer, pPos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1, 1);
            }
            pLevel.setBlockAndUpdate(pPos, pState.setValue(SAP, false));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction tapDirection = pState.getValue(FACING);
        Block tappedBlock = pLevel.getBlockState(pPos.relative(tapDirection.getOpposite())).getBlock();

        if (tappedBlock.equals(BlockInit.GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.GREATWOOD_WOOD.get()) || tappedBlock.equals(BlockInit.STRIPPED_GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_WOOD.get())) {
            if (pLevel.getGameTime() % pRandom.nextInt(40, 100) == 0) {
                if (pRandom.nextDouble() > 0.75) {
                    if (!pState.getValue(SAP)) {
                        pLevel.setBlockAndUpdate(pPos, pState.setValue(SAP, true));
                    }
                }
                double x = pPos.getX() + 0.5;
                double y = pPos.getY() + 0.2;
                double z = pPos.getZ() + 0.5;

                if (tapDirection == Direction.NORTH) {
                    z = z + 0.15;
                } else if (tapDirection == Direction.SOUTH) {
                    z = z - 0.15;
                } else if (tapDirection == Direction.EAST) {
                    x = x - 0.15;
                } else if (tapDirection == Direction.WEST) {
                    x = x + 0.15;
                }

                for (int i = 0; i < 3; i++) {
                    pLevel.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
                }
            }
        }
        super.animateTick(pState, pLevel, pPos, pRandom);
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

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        LevelAccessor accessor = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, accessor.getFluidState(pos).getType() == Fluids.WATER).setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(SAP, false);
    }

    public boolean isOnGreatwoodTree(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction tapDirection = pState.getValue(FACING);
        Block tappedBlock = pLevel.getBlockState(pPos.relative(tapDirection.getOpposite())).getBlock();
        return tappedBlock.equals(BlockInit.GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.GREATWOOD_WOOD.get()) || tappedBlock.equals(BlockInit.STRIPPED_GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_WOOD.get());
    }

    public boolean isOnGreatwoodTree(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        Direction tapDirection = pState.getValue(FACING);
        Block tappedBlock = pLevel.getBlockState(pPos.relative(tapDirection.getOpposite())).getBlock();
        return tappedBlock.equals(BlockInit.GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.GREATWOOD_WOOD.get()) || tappedBlock.equals(BlockInit.STRIPPED_GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_WOOD.get());
    }
}
