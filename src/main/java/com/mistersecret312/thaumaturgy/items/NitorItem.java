package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.aspects.Aspects;
import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class NitorItem extends BlockItem implements DyeableLeatherItem
{
    public static final String COLOR = "color";
    public static final int DEFAULT_COLOR = 16762880;

    public NitorItem(Properties pProperties)
    {
        super(BlockInit.NITOR.get(), pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag)
    {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        int color = this.getColor(pStack);

        String hex = String.format("#%06X", (color & 0xFFFFFF));

        pTooltip.add(Component.translatable("thaumaturgy.nitor.color").append(" "+hex).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResult place(BlockPlaceContext context)
    {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof NitorBlockEntity nitor)
        {
            nitor.setColor(getColor(context.getItemInHand()));
            return InteractionResult.SUCCESS;
        }

        return super.place(context);
    }

    @Override
    public int getColor(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains(COLOR))
            return stack.getTag().getInt(COLOR);
        else return DEFAULT_COLOR;
    }

    @Override
    public void setColor(ItemStack stack, int pColor)
    {
        stack.getOrCreateTag().putInt(COLOR, pColor);
    }

    @Override
    public boolean hasCustomColor(ItemStack stack)
    {
        return stack.getTag() != null && stack.getTag().contains(COLOR) && stack.getTag().getInt(COLOR) != DEFAULT_COLOR;
    }

    @Override
    public void clearColor(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains(COLOR))
            setColor(stack, DEFAULT_COLOR);
    }

    public static int getColorData(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains(COLOR))
        {
            return stack.getTag().getInt(COLOR);
        }
        else
        {
            setColorData(stack, DEFAULT_COLOR);
            return DEFAULT_COLOR;
        }
    }

    public static void setColorData(ItemStack stack, int color)
    {
        stack.getOrCreateTag().putInt(COLOR, color);
    }
}
