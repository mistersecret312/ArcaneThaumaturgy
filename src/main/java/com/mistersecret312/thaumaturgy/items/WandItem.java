package com.mistersecret312.thaumaturgy.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.IntStream;

public class WandItem extends Item
{
    public static final String ASPECT_STORAGE = "aspect_storage";
    public static final String CAPACITY = "capacity";

    public WandItem(Properties pProperties)
    {
        super(pProperties);
    }

    public static ItemStack create(Item item, int[] initial, int capacity)
    {
        ItemStack stack = new ItemStack(item);
        if(item instanceof WandItem wand)
        {
            wand.setCapacity(stack, capacity);
            wand.setAspects(stack, initial);
        }
        return stack;
    }

    public static ItemStack createFull(Item item, int capacity)
    {
        return WandItem.create(item, IntStream.generate(() -> capacity).limit(6).toArray(), capacity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced)
    {
        super.appendHoverText(stack, pLevel, pTooltipComponents, pIsAdvanced);

        int[] aspects = this.getAspects(stack);
        for (int i = 0; i < aspects.length; i++)
            pTooltipComponents.add(Component.translatable("aspect.thaumaturgy."+aspectIDToName(i)).withStyle(aspectIDToColor(i)).append(": ").append(String.valueOf(aspects[i])));
    }

    public void setAspects(ItemStack stack, int[] aspects)
    {
        CompoundTag tag = new CompoundTag();
        int a = 0;
        for(int i : aspects)
        {
            if(i > getCapacity(stack))
                i = getCapacity(stack);
            tag.putInt(WandItem.aspectIDToName(a), i);
            a++;
        }

        stack.getOrCreateTag().put(ASPECT_STORAGE, tag);
    }

    public int[] getAspects(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains(ASPECT_STORAGE) && stack.getTag().contains(CAPACITY))
        {
            int[] aspects = new int[6];
            CompoundTag tag = (CompoundTag) stack.getTag().get(ASPECT_STORAGE);
            if(tag != null)
                for (int i = 0; i < 6; i++)
                    aspects[i] = tag.getInt(WandItem.aspectIDToName(i));

            return aspects;
        }
        else return new int[6];
    }

    public void setCapacity(ItemStack stack, int capacity)
    {
        stack.getOrCreateTag().putInt(CAPACITY, capacity);
    }

    public int getCapacity(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains(CAPACITY))
        {
            return stack.getTag().getInt(CAPACITY);
        }
        else return 25;
    }

    public static String aspectIDToName(int id)
    {
        return switch (id)
        {
            case 0 -> "aer";
            case 1 -> "terra";
            case 2 -> "ignis";
            case 3 -> "aqua";
            case 4 -> "ordo";
            case 5 -> "perditio";
            default -> "error";
        };
    }

    public static ChatFormatting aspectIDToColor(int id)
    {
        return switch (id)
        {
            case 0 -> ChatFormatting.DARK_GRAY;
            case 1 -> ChatFormatting.WHITE;
            case 2 -> ChatFormatting.AQUA;
            case 3 -> ChatFormatting.RED;
            case 4 -> ChatFormatting.GREEN;
            case 5 -> ChatFormatting.YELLOW;
            default -> ChatFormatting.LIGHT_PURPLE;
        };
    }
}
