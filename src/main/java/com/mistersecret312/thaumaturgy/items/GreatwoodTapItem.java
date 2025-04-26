package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GreatwoodTapItem extends BlockItem {
    public GreatwoodTapItem(Properties pProperties) {
        super(BlockInit.GREATWOOD_TAP.get(), pProperties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        return isOnGreatwoodTree(pContext.getLevel(), pContext.getClickedPos(), pContext.getHorizontalDirection());
    }

    public boolean isOnGreatwoodTree(Level pLevel, BlockPos pPos, Direction tapDirection) {
        Block tappedBlock = pLevel.getBlockState(pPos.relative(tapDirection)).getBlock();
        return tappedBlock.equals(BlockInit.GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.GREATWOOD_WOOD.get()) || tappedBlock.equals(BlockInit.STRIPPED_GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_WOOD.get());
    }
}
