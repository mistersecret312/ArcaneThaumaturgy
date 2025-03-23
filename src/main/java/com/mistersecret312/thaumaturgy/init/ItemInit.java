package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.items.AspectDisplayTest;
import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.items.WandItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<AspectDisplayTest> DISPLAY_TEST = ITEMS.register("display_test", () -> new AspectDisplayTest(new Item.Properties().setNoRepair()));

    public static final RegistryObject<Item> IRON_KNOB = ITEMS.register("iron_knob", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> GOLD_KNOB = ITEMS.register("gold_knob", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<WandItem> IRON_WAND = ITEMS.register("iron_wand", () -> new WandItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<WandItem> GOLD_WAND = ITEMS.register("gold_wand", () -> new WandItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CRYSTALLIZED_AER_SHARD = ITEMS.register("crystallized_aer_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTALLIZED_IGNIS_SHARD = ITEMS.register("crystallized_ignis_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTALLIZED_AQUA_SHARD = ITEMS.register("crystallized_aqua_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTALLIZED_TERRA_SHARD = ITEMS.register("crystallized_terra_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTALLIZED_ORDO_SHARD = ITEMS.register("crystallized_ordo_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTALLIZED_PERDITIO_SHARD = ITEMS.register("crystallized_perditio_shard", () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
