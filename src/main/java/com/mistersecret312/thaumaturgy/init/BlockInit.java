package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.blocks.BuddingAspectCrystalBlock;
import com.mistersecret312.thaumaturgy.blocks.CrucibleBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<CrucibleBlock> CRUCIBLE = registerBlock("crucible", () -> new CrucibleBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.METAL).mapColor(MapColor.COLOR_GRAY)));

    public static final RegistryObject<Block> RUNIC_MATRIX = registerBlock("runic_matrix", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY).noOcclusion()));

    public static final RegistryObject<Block> ARCANE_STONE = registerBlock("arcane_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_BRICKS = registerBlock("arcane_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_STONE_PEDESTAL = registerBlock("arcane_stone_pedestal", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));

    public static final RegistryObject<AmethystBlock> CRYSTALLIZED_AER_BLOCK = registerBlock("crystallized_aer_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingAspectCrystalBlock> BUDDING_CRYSTALLIZED_AER = registerBlock("budding_crystallized_aer", () -> new BuddingAspectCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingAspectCrystalBlock.CrystalAspect.AER));
    public static final RegistryObject<AmethystClusterBlock> CRYSTALLIZED_AER_CLUSTER = registerBlock("crystallized_aer_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> LARGE_CRYSTALLIZED_AER_BUD = registerBlock("large_crystallized_aer_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_AER_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> MEDIUM_CRYSTALLIZED_AER_BUD = registerBlock("medium_crystallized_aer_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_AER_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> SMALL_CRYSTALLIZED_AER_BUD = registerBlock("small_crystallized_aer_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(CRYSTALLIZED_AER_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> CRYSTALLIZED_IGNIS_BLOCK = registerBlock("crystallized_ignis_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingAspectCrystalBlock> BUDDING_CRYSTALLIZED_IGNIS = registerBlock("budding_crystallized_ignis", () -> new BuddingAspectCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingAspectCrystalBlock.CrystalAspect.IGNIS));
    public static final RegistryObject<AmethystClusterBlock> CRYSTALLIZED_IGNIS_CLUSTER = registerBlock("crystallized_ignis_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> LARGE_CRYSTALLIZED_IGNIS_BUD = registerBlock("large_crystallized_ignis_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_IGNIS_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> MEDIUM_CRYSTALLIZED_IGNIS_BUD = registerBlock("medium_crystallized_ignis_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_IGNIS_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> SMALL_CRYSTALLIZED_IGNIS_BUD = registerBlock("small_crystallized_ignis_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(CRYSTALLIZED_IGNIS_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> CRYSTALLIZED_AQUA_BLOCK = registerBlock("crystallized_aqua_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingAspectCrystalBlock> BUDDING_CRYSTALLIZED_AQUA = registerBlock("budding_crystallized_aqua", () -> new BuddingAspectCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingAspectCrystalBlock.CrystalAspect.AQUA));
    public static final RegistryObject<AmethystClusterBlock> CRYSTALLIZED_AQUA_CLUSTER = registerBlock("crystallized_aqua_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> LARGE_CRYSTALLIZED_AQUA_BUD = registerBlock("large_crystallized_aqua_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_AQUA_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> MEDIUM_CRYSTALLIZED_AQUA_BUD = registerBlock("medium_crystallized_aqua_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_AQUA_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> SMALL_CRYSTALLIZED_AQUA_BUD = registerBlock("small_crystallized_aqua_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(CRYSTALLIZED_AQUA_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> CRYSTALLIZED_TERRA_BLOCK = registerBlock("crystallized_terra_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingAspectCrystalBlock> BUDDING_CRYSTALLIZED_TERRA = registerBlock("budding_crystallized_terra", () -> new BuddingAspectCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingAspectCrystalBlock.CrystalAspect.TERRA));
    public static final RegistryObject<AmethystClusterBlock> CRYSTALLIZED_TERRA_CLUSTER = registerBlock("crystallized_terra_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> LARGE_CRYSTALLIZED_TERRA_BUD = registerBlock("large_crystallized_terra_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_TERRA_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> MEDIUM_CRYSTALLIZED_TERRA_BUD = registerBlock("medium_crystallized_terra_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_TERRA_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> SMALL_CRYSTALLIZED_TERRA_BUD = registerBlock("small_crystallized_terra_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(CRYSTALLIZED_TERRA_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> CRYSTALLIZED_ORDO_BLOCK = registerBlock("crystallized_ordo_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingAspectCrystalBlock> BUDDING_CRYSTALLIZED_ORDO = registerBlock("budding_crystallized_ordo", () -> new BuddingAspectCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingAspectCrystalBlock.CrystalAspect.ORDO));
    public static final RegistryObject<AmethystClusterBlock> CRYSTALLIZED_ORDO_CLUSTER = registerBlock("crystallized_ordo_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> LARGE_CRYSTALLIZED_ORDO_BUD = registerBlock("large_crystallized_ordo_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_ORDO_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> MEDIUM_CRYSTALLIZED_ORDO_BUD = registerBlock("medium_crystallized_ordo_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_ORDO_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> SMALL_CRYSTALLIZED_ORDO_BUD = registerBlock("small_crystallized_ordo_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(CRYSTALLIZED_ORDO_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> CRYSTALLIZED_PERDITIO_BLOCK = registerBlock("crystallized_perditio_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingAspectCrystalBlock> BUDDING_CRYSTALLIZED_PERDITIO = registerBlock("budding_crystallized_perditio", () -> new BuddingAspectCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingAspectCrystalBlock.CrystalAspect.PERDITIO));
    public static final RegistryObject<AmethystClusterBlock> CRYSTALLIZED_PERDITIO_CLUSTER = registerBlock("crystallized_perditio_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> LARGE_CRYSTALLIZED_PERDITIO_BUD = registerBlock("large_crystallized_perditio_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_PERDITIO_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> MEDIUM_CRYSTALLIZED_PERDITIO_BUD = registerBlock("medium_crystallized_perditio_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(CRYSTALLIZED_PERDITIO_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> SMALL_CRYSTALLIZED_PERDITIO_BUD = registerBlock("small_crystallized_perditio_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(CRYSTALLIZED_PERDITIO_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus bus)
    {
        BLOCKS.register(bus);
    }
}
