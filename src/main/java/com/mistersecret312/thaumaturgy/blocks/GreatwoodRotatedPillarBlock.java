package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

public class GreatwoodRotatedPillarBlock extends RotatedPillarBlock
{
    public GreatwoodRotatedPillarBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction,
                                                     boolean simulate)
    {
        if(context.getItemInHand().getItem() instanceof AxeItem)
        {
            if(state.is(BlockInit.GREATWOOD_LOG.get()))
                return BlockInit.STRIPPED_GREATWOOD_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));

            if(state.is(BlockInit.GREATWOOD_WOOD.get()))
                return BlockInit.STRIPPED_GREATWOOD_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
