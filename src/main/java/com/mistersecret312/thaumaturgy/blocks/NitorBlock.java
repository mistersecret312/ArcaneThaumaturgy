package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class NitorBlock extends Block implements EntityBlock
{
    private static final VoxelShape SHAPE = Block.box(4, 4, 4, 12, 12, 12);

    public NitorBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }



    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new NitorBlockEntity(pPos, pState);
    }
}
