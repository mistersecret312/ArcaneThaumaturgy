package com.mistersecret312.init;

import com.mistersecret312.datapack.Aspect;
import com.mistersecret312.items.AspectDisplayTest;
import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<AspectDisplayTest> DISPLAY_TEST = ITEM.register("display_test", () -> new AspectDisplayTest(new Item.Properties().setNoRepair()));

    public static void register(IEventBus bus)
    {
        ITEM.register(bus);
    }
}
