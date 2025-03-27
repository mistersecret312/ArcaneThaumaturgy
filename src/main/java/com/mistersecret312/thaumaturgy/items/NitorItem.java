package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NitorItem extends BlockItem
{
    public static final String COLOR = "color";

    public NitorItem(Properties pProperties)
    {
        super(BlockInit.NITOR.get(), pProperties);
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
        }

        return super.place(context);
    }

    public static int getColor(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains(COLOR))
        {
            return stack.getTag().getInt(COLOR);
        }
        else
        {
            setColor(stack, 0xFFFFFF);
            return 0xFFFFFF;
        }
    }

    public static void setColor(ItemStack stack, int color)
    {
        stack.getOrCreateTag().putInt(COLOR, color);
    }
}
