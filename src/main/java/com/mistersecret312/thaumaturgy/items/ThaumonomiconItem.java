package com.mistersecret312.thaumaturgy.items;

import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.book.Book;
import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.klikli_dev.modonomicon.data.BookDataManager;
import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThaumonomiconItem extends Item
{
    public ThaumonomiconItem(Properties pProperties)
    {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);
        if (pLevel.isClientSide) {
            Book book = BookDataManager.get().getBook(new ResourceLocation(ArcaneThaumaturgyMod.MODID, "thaumonomicon"));
            BookGuiManager.get().openBook(book.getId());
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, pLevel.isClientSide);
    }
}
