package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.CrucibleBlockEntity;
import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CrucibleBlock extends BaseEntityBlock
{
    public static final BooleanProperty IS_BOILING = BooleanProperty.create("is_boiling");

    public CrucibleBlock(Properties pProperties)
    {
        super(pProperties);
        this.defaultBlockState().setValue(IS_BOILING, false);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return Shapes.join(Shapes.block(), Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D)), BooleanOp.ONLY_FIRST);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit)
    {
        if (pLevel.getBlockEntity(pPos) instanceof CrucibleBlockEntity crucible)
        {
            ItemStack stack = pPlayer.getItemInHand(pHand);
            int waterLevel = crucible.getWaterLevel();
            int itemsLeft = crucible.getItemsCrafted();

            if (waterLevel < 1)
            {
                if (stack.is(Items.WATER_BUCKET))
                {
                    crucible.setWaterLevel(4);
                    if (!pPlayer.isCreative())
                    {
                        pPlayer.setItemInHand(pHand, Items.BUCKET.getDefaultInstance());
                    }
                    pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            } else if (waterLevel < 4)
            {
                if (stack.is(Items.POTION) && PotionUtils.getPotion(stack) == Potions.WATER)
                {
                    crucible.setWaterLevel(waterLevel + 1);
                    if (!pPlayer.isCreative())
                    {
                        pPlayer.setItemInHand(pHand, Items.GLASS_BOTTLE.getDefaultInstance());
                    }
                    pLevel.playSound(null, pPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return this.defaultBlockState().setValue(IS_BOILING, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(IS_BOILING);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos)
    {
        return pState.setValue(IS_BOILING, hasHeatSource(pLevel, pPos));
    }

    public boolean hasHeatSource(LevelAccessor accessor, BlockPos pos)
    {
        BlockState state = accessor.getBlockState(pos.below());
        Block block = state.getBlock();

        return block instanceof FireBlock || (block instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) || block instanceof SoulFireBlock || block instanceof TorchBlock || state.getFluidState().is(FluidTags.LAVA) || block instanceof NitorBlock;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityInit.CRUCIBLE.get(), CrucibleBlockEntity::tick);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityInit.CRUCIBLE.get().create(pos, state);
    }
}
