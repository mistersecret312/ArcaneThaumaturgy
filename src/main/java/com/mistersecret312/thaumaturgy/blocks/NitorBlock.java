package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import com.mistersecret312.thaumaturgy.items.NitorItem;
import com.mistersecret312.thaumaturgy.items.WandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
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
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit)
    {
        if(pPlayer.getItemInHand(pHand).getItem() instanceof WandItem)
        {
            if(pLevel.getBlockEntity(pPos) instanceof NitorBlockEntity nitor)
            {
                nitor.setColor(NitorItem.DEFAULT_COLOR);
            }
        }

        if (pPlayer.getItemInHand(pHand).getItem() instanceof DyeItem dye)
        {
            if (pLevel.getBlockEntity(pPos) instanceof NitorBlockEntity nitor)
            {
                int color = nitor.getColor();
                float[] colors = dye.getDyeColor().getTextureDiffuseColors();

                int[] aint = new int[3];
                int i = 0;
                int j = 0;

                int k = color;
                float f = (float) (k >> 16 & 255) / 255.0F;
                float f1 = (float) (k >> 8 & 255) / 255.0F;
                float f2 = (float) (k & 255) / 255.0F;
                i += (int) (Math.max(f, Math.max(f1, f2)) * 255.0F);
                aint[0] += (int) (f * 255.0F);
                aint[1] += (int) (f1 * 255.0F);
                aint[2] += (int) (f2 * 255.0F);
                ++j;

                float[] afloat = colors;
                int i2 = (int) (afloat[0] * 255.0F);
                int l = (int) (afloat[1] * 255.0F);
                int i1 = (int) (afloat[2] * 255.0F);
                i += Math.max(i2, Math.max(l, i1));
                aint[0] += i2;
                aint[1] += l;
                aint[2] += i1;
                ++j;

                int j1 = aint[0] / j;
                int k1 = aint[1] / j;
                int l1 = aint[2] / j;
                float f3 = (float) i / (float) j;
                float f4 = (float) Math.max(j1, Math.max(k1, l1));
                j1 = (int) ((float) j1 * f3 / f4);
                k1 = (int) ((float) k1 * f3 / f4);
                l1 = (int) ((float) l1 * f3 / f4);
                int j2 = (j1 << 8) + k1;
                j2 = (j2 << 8) + l1;

                nitor.setColor(j2);
            }
        }
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        NitorBlockEntity nitor = getBlockEntity(level, pos);
        if(!level.isClientSide() && !player.isCreative() && nitor != null)
        {
            ItemStack stack = new ItemStack(asItem());

            NitorItem.setColorData(stack, nitor.getColor());
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
            nitor.setColor(NitorItem.getColorData(pStack));
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
