package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.blocks.NitorBlock;
import com.mistersecret312.thaumaturgy.items.*;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
    public static final RegistryObject<Item> GREATWOOD_CORE = ITEMS.register("greatwood_core", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ARCANE_STEEL_INGOT = ITEMS.register("arcane_steel_ingot", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ARCANE_STEEL_NUGGET = ITEMS.register("arcane_steel_nugget", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<WandItem> IRON_WAND = ITEMS.register("iron_wand", () -> new WandItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<WandItem> GOLD_WAND = ITEMS.register("gold_wand", () -> new WandItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<NitorItem> NITOR_IGNIS = ITEMS.register("nitor_ignis", () -> new NitorItem(new Item.Properties().stacksTo(1), NitorBlock.NitorType.IGNIS));
    public static final RegistryObject<NitorItem> NITOR_ORDO = ITEMS.register("nitor_ordo", () -> new NitorItem(new Item.Properties().stacksTo(1), NitorBlock.NitorType.ORDO));
    public static final RegistryObject<NitorItem> NITOR_TERRA = ITEMS.register("nitor_terra", () -> new NitorItem(new Item.Properties().stacksTo(1), NitorBlock.NitorType.TERRA));
    public static final RegistryObject<NitorItem> NITOR_AQUA = ITEMS.register("nitor_aqua", () -> new NitorItem(new Item.Properties().stacksTo(1), NitorBlock.NitorType.AQUA));
    public static final RegistryObject<NitorItem> NITOR_PERDITIO = ITEMS.register("nitor_perditio", () -> new NitorItem(new Item.Properties().stacksTo(1), NitorBlock.NitorType.PERDITIO));
    public static final RegistryObject<NitorItem> NITOR_AER = ITEMS.register("nitor_aer", () -> new NitorItem(new Item.Properties().stacksTo(1), NitorBlock.NitorType.AER));

    public static final RegistryObject<ResearchTableItem> RESEARCH_TABLE = ITEMS.register("research_table", () -> new ResearchTableItem(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> AER_VIS_CRYSTAL = ITEMS.register("aer_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IGNIS_VIS_CRYSTAL = ITEMS.register("ignis_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AQUA_VIS_CRYSTAL = ITEMS.register("aqua_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TERRA_VIS_CRYSTAL = ITEMS.register("terra_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ORDO_VIS_CRYSTAL = ITEMS.register("ordo_vis_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PERDITIO_VIS_CRYSTAL = ITEMS.register("perditio_vis_crystal", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CINNABAR_CRYSTAL = ITEMS.register("cinnabar_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> QUICKSILVER = ITEMS.register("quicksilver", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PURE_NETHERITE_NUCLEUS = ITEMS.register("pure_netherite_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_DIAMOND_NUCLEUS = ITEMS.register("pure_diamond_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_EMERALD_NUCLEUS = ITEMS.register("pure_emerald_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_QUARTZ_NUCLEUS = ITEMS.register("pure_quartz_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_LAPIS_NUCLEUS = ITEMS.register("pure_lapis_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_REDSTONE_NUCLEUS = ITEMS.register("pure_redstone_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_IRON_NUCLEUS = ITEMS.register("pure_iron_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_GOLD_NUCLEUS = ITEMS.register("pure_gold_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_COPPER_NUCLEUS = ITEMS.register("pure_copper_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_COAL_NUCLEUS = ITEMS.register("pure_coal_nucleus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_QUICKSILVER_NUCLEUS = ITEMS.register("pure_quicksilver_nucleus", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ASPECT = ITEMS.register("aspect", AspectItem::new);

    public static final RegistryObject<ThaumaturgeEmblemBlockItem> THAUMATURGE_EMBLEM = ITEMS.register("thaumaturge_emblem", () -> new ThaumaturgeEmblemBlockItem(new Item.Properties()));
    public static final RegistryObject<GreatwoodTapBlockItem> GREATWOOD_TAP = ITEMS.register("greatwood_tap", () -> new GreatwoodTapBlockItem(new Item.Properties()));
    public static final RegistryObject<SilverwoodTapBlockItem> SILVERWOOD_TAP = ITEMS.register("silverwood_tap", () -> new SilverwoodTapBlockItem(new Item.Properties()));

    public static final RegistryObject<RevelationGogglesItem> REVELATION_GOGGLES = ITEMS.register("revelation_goggles", () -> new RevelationGogglesItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<ThaumonomiconItem> THAUMONOMICON = ITEMS.register("thaumonomicon", () -> new ThaumonomiconItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<ThaumometerItem> THAUMOMETER = ITEMS.register("thaumometer", () -> new ThaumometerItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SCRIBING_TOOLS = ITEMS.register("scribing_tools", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> GREAT_SAP = ITEMS.register("great_sap", () -> new Item((new Item.Properties())));
    public static final RegistryObject<GreatSapBottleItem> GREAT_SAP_BOTTLE = ITEMS.register("great_sap_bottle", () -> new GreatSapBottleItem((new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).food(FoodsInit.GREAT_SAP_BOTTLE).stacksTo(16)));
    public static final RegistryObject<GreatSyrupBottleItem> GREAT_SYRUP_BOTTLE = ITEMS.register("great_syrup_bottle", () -> new GreatSyrupBottleItem((new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).food(FoodsInit.GREAT_SYRUP_BOTTLE).stacksTo(16)));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
