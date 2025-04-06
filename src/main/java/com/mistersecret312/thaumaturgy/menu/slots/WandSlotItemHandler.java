package com.mistersecret312.thaumaturgy.menu.slots;

import com.mistersecret312.thaumaturgy.items.WandItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class WandSlotItemHandler extends SlotItemHandler
{
    public WandSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack)
    {
        return !stack.isEmpty() && stack.getItem() instanceof WandItem;
    }


}
