package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.CrucibleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CrucibleBlock extends Block implements EntityBlock
{
    public static final EnumProperty<FilledCrucible> FILLED = EnumProperty.create("filled", FilledCrucible.class);

    public CrucibleBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return Shapes.join(Shapes.block(), Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D)), BooleanOp.ONLY_FIRST);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit)
    {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if(stack.is(Items.WATER_BUCKET))
        {
            pPlayer.setItemInHand(pHand, Items.BUCKET.getDefaultInstance());
            pLevel.setBlockAndUpdate(pPos, pState.setValue(FILLED, FilledCrucible.FULL_1));
            pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return this.defaultBlockState().setValue(FILLED, FilledCrucible.EMPTY);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FILLED);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new CrucibleBlockEntity(pos, state);
    }

    public enum FilledCrucible implements StringRepresentable
    {
        EMPTY("empty"),
        FULL_1("full_1"),
        FULL_2("full_2"),
        FULL_3("full_3"),
        FULL_4("full_4");

        public String name;
        FilledCrucible(String name)
        {
            this.name = name;
        }

        @Override
        public String getSerializedName()
        {
            return name;
        }
    }
}
