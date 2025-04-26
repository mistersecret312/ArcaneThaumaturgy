package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.blocks.ResearchTableBlock;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;

public class ResearchTableItem extends BlockItem
{

    public ResearchTableItem(Properties pProperties)
    {
        super(BlockInit.RESEARCH_TABLE.get(), pProperties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext)
    {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Direction direction = pContext.getNearestLookingDirection();

        if (level.getBlockState(pos.relative(direction.getClockWise())).canBeReplaced(pContext))
        {
            level.setBlockAndUpdate(pos.relative(direction.getClockWise()), this.getPlacementState(pContext).setValue(ResearchTableBlock.DUMMY, true));
            return super.place(pContext);
        } else return InteractionResult.FAIL;
    }
}
