package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.items.AspectItem;
import com.mistersecret312.thaumaturgy.items.WandItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemTabInit
{
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = TABS.register("main_tab",
            () -> CreativeModeTab.builder().icon(() -> ItemInit.REVELATION_GOGGLES.get().asItem().getDefaultInstance())
                    .title(Component.translatable("tabs.thaumaturgy.main_tab"))
                    .displayItems((parameters, output) ->
                    {
                        output.accept(BlockInit.ARCANE_WORKBENCH.get());

                        output.accept(BlockInit.RESEARCH_TABLE_LEFT.get());
                        output.accept(BlockInit.RESEARCH_TABLE_RIGHT.get());

                        output.accept(BlockInit.CRUCIBLE.get());

                        output.accept(BlockInit.RUNIC_MATRIX.get());

                        output.accept(ItemInit.THAUMONOMICON.get());

                        output.accept(BlockInit.ESSENTIA_JAR.get());

                        output.accept(ItemInit.SCRIBING_TOOLS.get());

                        output.accept(WandItem.createPrimal(ItemInit.IRON_WAND.get(), 25, true));
                        output.accept(WandItem.createPrimal(ItemInit.GOLD_WAND.get(), 50, true));

                        output.accept(ItemInit.THAUMOMETER.get());

                        output.accept(ItemInit.REVELATION_GOGGLES.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> RESOURCE_TAB = TABS.register("resource_tab",
            () -> CreativeModeTab.builder().icon(() -> BlockInit.AER_VIS_CRYSTAL_CLUSTER.get().asItem().getDefaultInstance())
                    .title(Component.translatable("tabs.thaumaturgy.resources_tab"))
                    .withTabsBefore(MAIN_TAB.getKey())
                    .displayItems((parameters, output) ->
                    {
                        output.accept(ItemInit.IRON_KNOB.get());
                        output.accept(ItemInit.GOLD_KNOB.get());

                        output.accept(ItemInit.GREATWOOD_CORE.get());

                        output.accept(ItemInit.ARCANE_STEEL_INGOT.get());

                        output.accept(ItemInit.AER_VIS_CRYSTAL.get());
                        output.accept(BlockInit.AER_VIS_CRYSTAL_BLOCK.get());
                        output.accept(BlockInit.AER_BUDDING_VIS_CRYSTAL.get());
                        output.accept(BlockInit.AER_VIS_CRYSTAL_CLUSTER.get());
                        output.accept(BlockInit.AER_LARGE_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.AER_MEDIUM_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.AER_SMALL_VIS_CRYSTAL_BUD.get());

                        output.accept(ItemInit.IGNIS_VIS_CRYSTAL.get());
                        output.accept(BlockInit.IGNIS_VIS_CRYSTAL_BLOCK.get());
                        output.accept(BlockInit.IGNIS_BUDDING_VIS_CRYSTAL.get());
                        output.accept(BlockInit.IGNIS_VIS_CRYSTAL_CLUSTER.get());
                        output.accept(BlockInit.IGNIS_LARGE_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.IGNIS_MEDIUM_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.IGNIS_SMALL_VIS_CRYSTAL_BUD.get());

                        output.accept(ItemInit.AQUA_VIS_CRYSTAL.get());
                        output.accept(BlockInit.AQUA_VIS_CRYSTAL_BLOCK.get());
                        output.accept(BlockInit.AQUA_BUDDING_VIS_CRYSTAL.get());
                        output.accept(BlockInit.AQUA_VIS_CRYSTAL_CLUSTER.get());
                        output.accept(BlockInit.AQUA_LARGE_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.AQUA_MEDIUM_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.AQUA_SMALL_VIS_CRYSTAL_BUD.get());

                        output.accept(ItemInit.TERRA_VIS_CRYSTAL.get());
                        output.accept(BlockInit.TERRA_VIS_CRYSTAL_BLOCK.get());
                        output.accept(BlockInit.TERRA_BUDDING_VIS_CRYSTAL.get());
                        output.accept(BlockInit.TERRA_VIS_CRYSTAL_CLUSTER.get());
                        output.accept(BlockInit.TERRA_LARGE_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.TERRA_MEDIUM_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.TERRA_SMALL_VIS_CRYSTAL_BUD.get());

                        output.accept(ItemInit.ORDO_VIS_CRYSTAL.get());
                        output.accept(BlockInit.ORDO_VIS_CRYSTAL_BLOCK.get());
                        output.accept(BlockInit.ORDO_BUDDING_VIS_CRYSTAL.get());
                        output.accept(BlockInit.ORDO_VIS_CRYSTAL_CLUSTER.get());
                        output.accept(BlockInit.ORDO_LARGE_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.ORDO_MEDIUM_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.ORDO_SMALL_VIS_CRYSTAL_BUD.get());

                        output.accept(ItemInit.PERDITIO_VIS_CRYSTAL.get());
                        output.accept(BlockInit.PERDITIO_VIS_CRYSTAL_BLOCK.get());
                        output.accept(BlockInit.PERDITIO_BUDDING_VIS_CRYSTAL.get());
                        output.accept(BlockInit.PERDITIO_VIS_CRYSTAL_CLUSTER.get());
                        output.accept(BlockInit.PERDITIO_LARGE_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.PERDITIO_MEDIUM_VIS_CRYSTAL_BUD.get());
                        output.accept(BlockInit.PERDITIO_SMALL_VIS_CRYSTAL_BUD.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> DECORATIONS_TAB = TABS.register("decorations_tab",
            () -> CreativeModeTab.builder().icon(() -> BlockInit.ARCANE_STONE_BRICKS.get().asItem().getDefaultInstance())
                    .title(Component.translatable("tabs.thaumaturgy.decorations_tab"))
                    .withTabsBefore(RESOURCE_TAB.getKey())
                    .displayItems((parameters, output) ->
                    {
                        output.accept(BlockInit.GREATWOOD_LOG.get());
                        output.accept(BlockInit.GREATWOOD_WOOD.get());
                        output.accept(BlockInit.STRIPPED_GREATWOOD_LOG.get());
                        output.accept(BlockInit.STRIPPED_GREATWOOD_WOOD.get());
                        output.accept(BlockInit.GREATWOOD_PLANKS.get());
                        output.accept(BlockInit.GREATWOOD_STAIRS.get());
                        output.accept(BlockInit.GREATWOOD_SLAB.get());
                        output.accept(BlockInit.GREATWOOD_FENCE.get());
                        output.accept(BlockInit.GREATWOOD_FENCE_GATE.get());
                        output.accept(BlockInit.GREATWOOD_DOOR.get());
                        output.accept(BlockInit.GREATWOOD_TRAPDOOR.get());
                        output.accept(BlockInit.GREATWOOD_PRESSURE_PLATE.get());
                        output.accept(BlockInit.GREATWOOD_BUTTON.get());

                        output.accept(BlockInit.GREATWOOD_TABLE.get());

                        output.accept(BlockInit.GREATWOOD_ARMCHAIR.get());
                        output.accept(BlockInit.GREATWOOD_STOOL.get());

                        output.accept(BlockInit.THAUMATURGE_EMBLEM.get());

                        output.accept(BlockInit.ARCANE_STONE.get());
                        output.accept(BlockInit.ARCANE_STONE_STAIRS.get());
                        output.accept(BlockInit.ARCANE_STONE_SLAB.get());
                        output.accept(BlockInit.ARCANE_STONE_WALL.get());
                        output.accept(BlockInit.ARCANE_STONE_PRESSURE_PLATE.get());
                        output.accept(BlockInit.ARCANE_STONE_BUTTON.get());
                        output.accept(BlockInit.ARCANE_STONE_BRICKS.get());
                        output.accept(BlockInit.ARCANE_STONE_BRICKS_STAIRS.get());
                        output.accept(BlockInit.ARCANE_STONE_BRICKS_SLAB.get());
                        output.accept(BlockInit.ARCANE_STONE_BRICKS_WALL.get());

                        output.accept(BlockInit.ARCANE_STONE_PEDESTAL.get());

                        output.accept(ItemInit.NITOR.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> ASPECT_TAG = TABS.register("aspect_tab",
            () -> CreativeModeTab.builder().icon(() -> AspectItem.create(AspectInit.ORDO.get()))
                    .title(Component.translatable("tabs.thaumaturgy.aspects_tab"))
                    .withTabsBefore(DECORATIONS_TAB.getKey())
                    .displayItems((parameters, output) -> {
                        AspectInit.ASPECTS.getEntries().forEach(aspect ->
                        {
                            output.accept(AspectItem.create(aspect.get()));
                        });
                    })
                    .build());

    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
    }
}
