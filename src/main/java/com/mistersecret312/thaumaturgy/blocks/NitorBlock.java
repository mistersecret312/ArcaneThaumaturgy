package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import com.mistersecret312.thaumaturgy.items.NitorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        NitorBlockEntity nitor = getBlockEntity(level, pos);
        if(!level.isClientSide() && !player.isCreative() && nitor != null)
        {
            ItemStack stack = new ItemStack(asItem());

            NitorItem.setColor(stack, nitor.getColor());
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer,
                            ItemStack pStack)
    {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if(pLevel.getBlockEntity(pPos) instanceof NitorBlockEntity nitor)
        {
            nitor.setColor(NitorItem.getColor(pStack));
        }
    }

    public NitorBlockEntity getBlockEntity(Level level, BlockPos pos)
    {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof NitorBlockEntity nitor)
            return nitor;
        else return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new NitorBlockEntity(pPos, pState);
    }
}
