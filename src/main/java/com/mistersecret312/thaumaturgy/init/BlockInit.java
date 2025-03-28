package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.blocks.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
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

    public static final RegistryObject<CrucibleBlock> CRUCIBLE = registerBlock("crucible", () -> new CrucibleBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.COPPER).mapColor(MapColor.COLOR_GRAY)));

    public static final RegistryObject<RunicMatrixBlock> RUNIC_MATRIX = registerBlock("runic_matrix", () -> new RunicMatrixBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY).noOcclusion()));

    public static final RegistryObject<NitorBlock> NITOR = BLOCKS.register("nitor", () -> new NitorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().noCollission().lightLevel((state) -> 10).emissiveRendering((state, getter, pos) -> true).instabreak().sound(SoundTypeInit.NITOR)));
    public static final RegistryObject<EssentiaJarBlock> ESSENTIA_JAR = registerBlock("essentia_jar", () -> new EssentiaJarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).noOcclusion().instabreak().sound(SoundTypeInit.ESSENTIA_JAR)));

    public static final RegistryObject<Block> ARCANE_STONE = registerBlock("arcane_stone", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_STONE_SLAB = registerBlock("arcane_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_STONE_STAIRS = registerBlock("arcane_stone_stairs", () -> new StairBlock(ARCANE_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_STONE_WALL = registerBlock("arcane_stone_wall", () -> new WallBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_STONE_BRICKS = registerBlock("arcane_stone_bricks", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_STONE_BRICKS_SLAB = registerBlock("arcane_stone_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_STONE_BRICKS_STAIRS = registerBlock("arcane_stone_bricks_stairs", () -> new StairBlock(ARCANE_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_STONE_BRICKS_WALL = registerBlock("arcane_stone_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> ARCANE_STONE_BUTTON = registerBlock("arcane_stone_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY), BlockSetType.STONE, 30, false));
    public static final RegistryObject<Block> ARCANE_STONE_PRESSURE_PLATE = registerBlock("arcane_stone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY), BlockSetType.STONE));

    public static final RegistryObject<PedestalBlock> ARCANE_STONE_PEDESTAL = registerBlock("arcane_stone_pedestal", () -> new PedestalBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 5f).sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_GRAY)));

    public static final RegistryObject<Block> GREATWOOD_LOG = registerBlock("greatwood_log", () -> new GreatRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.NETHER_WOOD).ignitedByLava()));
    public static final RegistryObject<Block> GREATWOOD_WOOD = registerBlock("greatwood_wood", () -> new GreatRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.NETHER_WOOD).ignitedByLava()));
    public static final RegistryObject<Block> STRIPPED_GREATWOOD_LOG = registerBlock("stripped_greatwood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.NETHER_WOOD).ignitedByLava()));
    public static final RegistryObject<Block> STRIPPED_GREATWOOD_WOOD = registerBlock("stripped_greatwood_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.NETHER_WOOD).ignitedByLava()));
    public static final RegistryObject<Block> GREATWOOD_PLANKS = registerBlock("greatwood_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.NETHER_WOOD).ignitedByLava()));
    public static final RegistryObject<Block> GREATWOOD_PLANKS_SLAB = registerBlock("greatwood_planks_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.NETHER_WOOD)));
    public static final RegistryObject<Block> GREATWOOD_PLANKS_STAIRS = registerBlock("greatwood_planks_stairs", () -> new StairBlock(ARCANE_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.NETHER_WOOD).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block> GREATWOOD_PLANKS_BUTTON = registerBlock("greatwood_planks_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.NETHER_WOOD).mapColor(MapColor.COLOR_BROWN), BlockSetType.DARK_OAK, 30, true));
    public static final RegistryObject<Block> GREATWOOD_PLANKS_PRESSURE_PLATE = registerBlock("greatwood_planks_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.NETHER_WOOD).mapColor(MapColor.COLOR_BROWN), BlockSetType.DARK_OAK));

    public static final RegistryObject<AmethystBlock> AER_VIS_CRYSTAL_BLOCK = registerBlock("aer_vis_crystal_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingVisCrystalBlock> AER_BUDDING_VIS_CRYSTAL = registerBlock("aer_budding_vis_crystal", () -> new BuddingVisCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingVisCrystalBlock.CrystalAspect.AER));
    public static final RegistryObject<AmethystClusterBlock> AER_VIS_CRYSTAL_CLUSTER = registerBlock("aer_vis_crystal_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> AER_LARGE_VIS_CRYSTAL_BUD = registerBlock("aer_large_vis_crystal_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(AER_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> AER_MEDIUM_VIS_CRYSTAL_BUD = registerBlock("aer_medium_vis_crystal_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(AER_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> AER_SMALL_VIS_CRYSTAL_BUD = registerBlock("aer_small_vis_crystal_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(AER_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> IGNIS_VIS_CRYSTAL_BLOCK = registerBlock("ignis_vis_crystal_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingVisCrystalBlock> IGNIS_BUDDING_VIS_CRYSTAL = registerBlock("ignis_budding_vis_crystal", () -> new BuddingVisCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingVisCrystalBlock.CrystalAspect.IGNIS));
    public static final RegistryObject<AmethystClusterBlock> IGNIS_VIS_CRYSTAL_CLUSTER = registerBlock("ignis_vis_crystal_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> IGNIS_LARGE_VIS_CRYSTAL_BUD = registerBlock("ignis_large_vis_crystal_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(IGNIS_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> IGNIS_MEDIUM_VIS_CRYSTAL_BUD = registerBlock("ignis_medium_vis_crystal_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(IGNIS_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> IGNIS_SMALL_VIS_CRYSTAL_BUD = registerBlock("ignis_small_vis_crystal_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(IGNIS_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> AQUA_VIS_CRYSTAL_BLOCK = registerBlock("aqua_vis_crystal_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingVisCrystalBlock> AQUA_BUDDING_VIS_CRYSTAL = registerBlock("aqua_budding_vis_crystal", () -> new BuddingVisCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingVisCrystalBlock.CrystalAspect.AQUA));
    public static final RegistryObject<AmethystClusterBlock> AQUA_VIS_CRYSTAL_CLUSTER = registerBlock("aqua_vis_crystal_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> AQUA_LARGE_VIS_CRYSTAL_BUD = registerBlock("aqua_large_vis_crystal_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(AQUA_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> AQUA_MEDIUM_VIS_CRYSTAL_BUD = registerBlock("aqua_medium_vis_crystal_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(AQUA_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> AQUA_SMALL_VIS_CRYSTAL_BUD = registerBlock("aqua_small_vis_crystal_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(AQUA_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> TERRA_VIS_CRYSTAL_BLOCK = registerBlock("terra_vis_crystal_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingVisCrystalBlock> TERRA_BUDDING_VIS_CRYSTAL = registerBlock("terra_budding_vis_crystal", () -> new BuddingVisCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingVisCrystalBlock.CrystalAspect.TERRA));
    public static final RegistryObject<AmethystClusterBlock> TERRA_VIS_CRYSTAL_CLUSTER = registerBlock("terra_vis_crystal_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> TERRA_LARGE_VIS_CRYSTAL_BUD = registerBlock("terra_large_vis_crystal_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(TERRA_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> TERRA_MEDIUM_VIS_CRYSTAL_BUD = registerBlock("terra_medium_vis_crystal_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(TERRA_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> TERRA_SMALL_VIS_CRYSTAL_BUD = registerBlock("terra_small_vis_crystal_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(TERRA_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> ORDO_VIS_CRYSTAL_BLOCK = registerBlock("ordo_vis_crystal_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingVisCrystalBlock> ORDO_BUDDING_VIS_CRYSTAL = registerBlock("ordo_budding_vis_crystal", () -> new BuddingVisCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingVisCrystalBlock.CrystalAspect.ORDO));
    public static final RegistryObject<AmethystClusterBlock> ORDO_VIS_CRYSTAL_CLUSTER = registerBlock("ordo_vis_crystal_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> ORDO_LARGE_VIS_CRYSTAL_BUD = registerBlock("ordo_large_vis_crystal_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(ORDO_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> ORDO_MEDIUM_VIS_CRYSTAL_BUD = registerBlock("ordo_medium_vis_crystal_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(ORDO_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> ORDO_SMALL_VIS_CRYSTAL_BUD = registerBlock("ordo_small_vis_crystal_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(ORDO_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<AmethystBlock> PERDITIO_VIS_CRYSTAL_BLOCK = registerBlock("perditio_vis_crystal_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<BuddingVisCrystalBlock> PERDITIO_BUDDING_VIS_CRYSTAL = registerBlock("perditio_budding_vis_crystal", () -> new BuddingVisCrystalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY), BuddingVisCrystalBlock.CrystalAspect.PERDITIO));
    public static final RegistryObject<AmethystClusterBlock> PERDITIO_VIS_CRYSTAL_CLUSTER = registerBlock("perditio_vis_crystal_cluster", () -> new AmethystClusterBlock(7, 3,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> PERDITIO_LARGE_VIS_CRYSTAL_BUD = registerBlock("perditio_large_vis_crystal_bud", () -> new AmethystClusterBlock(5, 3,BlockBehaviour.Properties.copy(PERDITIO_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> PERDITIO_MEDIUM_VIS_CRYSTAL_BUD = registerBlock("perditio_medium_vis_crystal_bud", () -> new AmethystClusterBlock(4, 3,BlockBehaviour.Properties.copy(PERDITIO_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<AmethystClusterBlock> PERDITIO_SMALL_VIS_CRYSTAL_BUD = registerBlock("perditio_small_vis_crystal_bud", () -> new AmethystClusterBlock(3, 4,BlockBehaviour.Properties.copy(PERDITIO_VIS_CRYSTAL_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

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
