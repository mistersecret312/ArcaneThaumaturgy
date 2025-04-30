package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mistersecret312.thaumaturgy.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class GreatwoodSapBowlBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty SAP_AMOUNT = IntegerProperty.create("sap", 0, 5);

    public static final VoxelShape SHAPE = MathUtil.buildShape(
            Block.box(2, 0, 2, 14, 1, 14),
            Block.box(2, 1, 13, 14, 7, 14),
            Block.box(2, 1, 2, 14, 7, 3),
            Block.box(2, 1, 3, 3, 7, 13),
            Block.box(13, 1, 3, 14, 7, 13)
    );

    public GreatwoodSapBowlBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(SAP_AMOUNT, 0));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        int sap = pState.getValue(SAP_AMOUNT);

        if (sap > 0) {
            if (itemStack.getItem() == Items.GLASS_BOTTLE) {
                if (!pLevel.isClientSide) {
                    pLevel.setBlock(pPos, pState.setValue(SAP_AMOUNT, sap - 1), 2);
                }

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
                if (!pLevel.isClientSide) {
                    pLevel.setBlock(pPos, pState.setValue(SAP_AMOUNT, 0), 2);
                }

                if (itemStack.isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(ItemInit.GREAT_SAP.get(), sap));
                } else if (!pPlayer.getInventory().add(new ItemStack(ItemInit.GREAT_SAP.get(), sap))) {
                    pPlayer.drop(new ItemStack(ItemInit.GREAT_SAP.get(), sap), false);
                }

                pLevel.playSound(pPlayer, pPos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1, 1.25f);

            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        if (pState.getBlock() != pNewState.getBlock()) {
            int sap = pState.getValue(SAP_AMOUNT);
            if (sap > 0) {
                ItemStack sapStack = new ItemStack(ItemInit.GREAT_SAP.get(), sap);
                pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), sapStack));
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(WATERLOGGED, SAP_AMOUNT);
        super.createBlockStateDefinition(blockStateBuilder);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos)
    {
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
        return this.defaultBlockState().setValue(WATERLOGGED, accessor.getFluidState(pos).getType() == Fluids.WATER).setValue(SAP_AMOUNT, 0);
    }
}
