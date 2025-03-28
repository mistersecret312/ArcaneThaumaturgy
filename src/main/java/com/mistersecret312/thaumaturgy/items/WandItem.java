package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.aspects.Aspects;
import com.mistersecret312.thaumaturgy.aspects.DefinedAspectStackHandler;
import com.mistersecret312.thaumaturgy.datapack.Aspect;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.Style;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class WandItem extends Item
{
    public static final String VIS_STORAGE = "vis_storage";

    public WandItem(Properties pProperties)
    {
        super(pProperties);
    }

    public static ItemStack create(Item item, DefinedAspectStackHandler handler)
    {
        ItemStack stack = new ItemStack(item);
        if(item instanceof WandItem wand)
        {
            wand.setAspects(stack, handler);
        }
        return stack;
    }

    public static ItemStack createPrimal(Item item, int capacity, boolean full)
    {
        ItemStack stack = new ItemStack(item);
        List<Aspect> basePrimes = List.of(Aspects.Primal.AER, Aspects.Primal.TERRA, Aspects.Primal.IGNIS, Aspects.Primal.AQUA, Aspects.Primal.ORDO, Aspects.Primal.PERDITIO);
        DefinedAspectStackHandler handler = new DefinedAspectStackHandler(basePrimes, capacity);
        if(full)
            basePrimes.forEach(primal -> handler.setStackInSlot(primal, new AspectStack(Holder.direct(primal), capacity)));

        if(item instanceof WandItem wand)
        {
            wand.setAspects(stack, handler);
        }

        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced)
    {
        super.appendHoverText(stack, pLevel, pTooltipComponents, pIsAdvanced);

        DefinedAspectStackHandler aspects = this.getAspects(stack);

        List<Aspect> basePrimes = List.of(Aspects.Primal.AER, Aspects.Primal.TERRA, Aspects.Primal.IGNIS, Aspects.Primal.AQUA, Aspects.Primal.ORDO, Aspects.Primal.PERDITIO);

        basePrimes.forEach(primal ->
        {
            AspectStack aspectStack = aspects.getStackInSlot(primal);
            if (!aspectStack.isEmpty())
            {
                List<Integer> rgb = primal.getColor();
                int color = (rgb.get(0) << 16) | (rgb.get(1) << 8) | rgb.get(2);

                pTooltipComponents.add(aspectStack.getTranslatable().append(": " + aspectStack.getAmount()).withStyle(style -> style.withColor(color)));
            }
        });

    }

    public DefinedAspectStackHandler getAspects(ItemStack stack)
    {
        List<Aspect> basePrimes = List.of(Aspects.Primal.AER, Aspects.Primal.TERRA, Aspects.Primal.IGNIS, Aspects.Primal.AQUA, Aspects.Primal.ORDO, Aspects.Primal.PERDITIO);
        DefinedAspectStackHandler handler = new DefinedAspectStackHandler(basePrimes, 25);

        if(stack.getTag() != null && stack.getTag().contains(VIS_STORAGE))
            handler.deserializeNBT(stack.getTag().getCompound(VIS_STORAGE));

        return handler;
    }

    public void setAspects(ItemStack stack, DefinedAspectStackHandler handler)
    {
        stack.getOrCreateTag().put(VIS_STORAGE, handler.serializeNBT());
    }


}
