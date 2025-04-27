package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class ThaumaturgeEmblemBlockItem extends BlockItem {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;;

    public ThaumaturgeEmblemBlockItem(Properties pProperties) {
        super(BlockInit.THAUMATURGE_EMBLEM.get(), pProperties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        return isValidBlock(pContext.getLevel(), pContext.getClickedPos(), pContext.getHorizontalDirection());
    }

    public boolean isValidBlock(Level pLevel, BlockPos pPos, Direction tapDirection) {
        BlockState tappedBlockState = pLevel.getBlockState(pPos.relative(tapDirection));
        return !tappedBlockState.isAir();
    }
}
