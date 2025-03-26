package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.items.RevelationGogglesItem;
import com.mistersecret312.thaumaturgy.items.ThaumometerItem;
import com.mistersecret312.thaumaturgy.items.ThaumonomiconItem;
import com.mistersecret312.thaumaturgy.items.WandItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<Item> IRON_KNOB = ITEMS.register("iron_knob", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> GOLD_KNOB = ITEMS.register("gold_knob", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<WandItem> IRON_WAND = ITEMS.register("iron_wand", () -> new WandItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<WandItem> GOLD_WAND = ITEMS.register("gold_wand", () -> new WandItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> AER_VIS_CRYSTAL = ITEMS.register("aer_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IGNIS_VIS_CRYSTAL = ITEMS.register("ignis_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AQUA_VIS_CRYSTAL = ITEMS.register("aqua_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TERRA_VIS_CRYSTAL = ITEMS.register("terra_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ORDO_VIS_CRYSTAL = ITEMS.register("ordo_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PERDITIO_VIS_CRYSTAL = ITEMS.register("perditio_vis_crystal", () -> new Item(new Item.Properties()));

    public static final RegistryObject<RevelationGogglesItem> REVELATION_GOGGLES = ITEMS.register("revelation_goggles", () -> new RevelationGogglesItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<ThaumonomiconItem> THAUMONOMICON = ITEMS.register("thaumonomicon", () -> new ThaumonomiconItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<ThaumometerItem> THAUMOMETER = ITEMS.register("thaumometer", () -> new ThaumometerItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SCRIBING_TOOLS = ITEMS.register("scribing_tools", () -> new Item(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
