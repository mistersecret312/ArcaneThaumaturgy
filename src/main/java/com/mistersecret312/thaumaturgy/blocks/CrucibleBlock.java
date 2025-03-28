package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.CrucibleBlockEntity;
import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
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
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if(!level.isClientSide() && level.getBlockEntity(pos) instanceof CrucibleBlockEntity crucible)
        {
            if(state.getValue(IS_BOILING) && crucible.getWaterLevel() > 0 && entity instanceof ItemEntity itemEntity)
            {
                crucible.itemThrown(itemEntity);
            }
        }
        super.entityInside(state, level, pos, entity);
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
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pLevel.getBlockEntity(pPos) instanceof CrucibleBlockEntity crucible)
        {
            BlockPos pos = PointedDripstoneBlock.findStalactiteTipAboveCauldron(pLevel, pPos);
            if (pos != null)
            {
                Fluid fluid = PointedDripstoneBlock.getCauldronFillFluidType(pLevel, pos);
                if (fluid != Fluids.EMPTY)
                {
                    receiveStalactiteDrip(pState, pLevel, pPos, fluid, crucible);
                }
            }
        }
    }

    protected void receiveStalactiteDrip(BlockState pState, Level pLevel, BlockPos pPos, Fluid pFluid, CrucibleBlockEntity crucible) {
        if (pFluid == Fluids.WATER) {
            int waterLevel = crucible.getWaterLevel();
            if (waterLevel < 4) {
                crucible.setWaterLevel(crucible.getWaterLevel() + 1);
            }
        }
    }

    @Override
    public void handlePrecipitation(BlockState pState, Level pLevel, BlockPos pPos, Biome.Precipitation pPrecipitation) {
        if (pPrecipitation == Biome.Precipitation.RAIN) {
            pLevel.setBlockAndUpdate(pPos, Blocks.WATER_CAULDRON.defaultBlockState());
            pLevel.gameEvent(null, GameEvent.BLOCK_CHANGE, pPos);
        }
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
