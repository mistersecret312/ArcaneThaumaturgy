package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.items.AspectDisplayTest;
import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.items.WandItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemTabInit
{
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = TABS.register("main_tab",
            () -> CreativeModeTab.builder().icon(() -> BlockInit.CRUCIBLE.get().asItem().getDefaultInstance())
                    .title(Component.translatable("tabs.thaumaturgy.main_tab"))
                    .displayItems((parameters, output) ->
                    {
                        output.accept(BlockInit.ARCANE_STONE.get());

                        output.accept(BlockInit.CRUCIBLE.get());

                        output.accept(BlockInit.RUNIC_MATRIX.get());

                        output.accept(AspectDisplayTest.create(ResourceKey.create(Aspect.REGISTRY_KEY, new ResourceLocation("thaumaturgy:terra"))));

                        output.accept(WandItem.createFull(ItemInit.IRON_WAND.get(), 25));
                        output.accept(WandItem.createFull(ItemInit.GOLD_WAND.get(), 50));

                        output.accept(ItemInit.IRON_KNOB.get());
                        output.accept(ItemInit.GOLD_KNOB.get());

                        output.accept(ItemInit.GOGGLES.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> RESOURCE_TAB = TABS.register("resource_tab",
            () -> CreativeModeTab.builder().icon(() -> BlockInit.CRYSTALLIZED_AER_CLUSTER.get().asItem().getDefaultInstance())
                    .title(Component.translatable("tabs.thaumaturgy.resources_tab"))
                    .displayItems((parameters, output) ->
                    {
                        output.accept(ItemInit.CRYSTALLIZED_AER_SHARD.get());
                        output.accept(BlockInit.CRYSTALLIZED_AER_BLOCK.get());
                        output.accept(BlockInit.BUDDING_CRYSTALLIZED_AER.get());
                        output.accept(BlockInit.CRYSTALLIZED_AER_CLUSTER.get());
                        output.accept(BlockInit.LARGE_CRYSTALLIZED_AER_BUD.get());
                        output.accept(BlockInit.MEDIUM_CRYSTALLIZED_AER_BUD.get());
                        output.accept(BlockInit.SMALL_CRYSTALLIZED_AER_BUD.get());

                        output.accept(ItemInit.CRYSTALLIZED_IGNIS_SHARD.get());
                        output.accept(BlockInit.CRYSTALLIZED_IGNIS_BLOCK.get());
                        output.accept(BlockInit.BUDDING_CRYSTALLIZED_IGNIS.get());
                        output.accept(BlockInit.CRYSTALLIZED_IGNIS_CLUSTER.get());
                        output.accept(BlockInit.LARGE_CRYSTALLIZED_IGNIS_BUD.get());
                        output.accept(BlockInit.MEDIUM_CRYSTALLIZED_IGNIS_BUD.get());
                        output.accept(BlockInit.SMALL_CRYSTALLIZED_IGNIS_BUD.get());

                        output.accept(ItemInit.CRYSTALLIZED_AQUA_SHARD.get());
                        output.accept(BlockInit.CRYSTALLIZED_AQUA_BLOCK.get());
                        output.accept(BlockInit.BUDDING_CRYSTALLIZED_AQUA.get());
                        output.accept(BlockInit.CRYSTALLIZED_AQUA_CLUSTER.get());
                        output.accept(BlockInit.LARGE_CRYSTALLIZED_AQUA_BUD.get());
                        output.accept(BlockInit.MEDIUM_CRYSTALLIZED_AQUA_BUD.get());
                        output.accept(BlockInit.SMALL_CRYSTALLIZED_AQUA_BUD.get());

                        output.accept(ItemInit.CRYSTALLIZED_TERRA_SHARD.get());
                        output.accept(BlockInit.CRYSTALLIZED_TERRA_BLOCK.get());
                        output.accept(BlockInit.BUDDING_CRYSTALLIZED_TERRA.get());
                        output.accept(BlockInit.CRYSTALLIZED_TERRA_CLUSTER.get());
                        output.accept(BlockInit.LARGE_CRYSTALLIZED_TERRA_BUD.get());
                        output.accept(BlockInit.MEDIUM_CRYSTALLIZED_TERRA_BUD.get());
                        output.accept(BlockInit.SMALL_CRYSTALLIZED_TERRA_BUD.get());

                        output.accept(ItemInit.CRYSTALLIZED_ORDO_SHARD.get());
                        output.accept(BlockInit.CRYSTALLIZED_ORDO_BLOCK.get());
                        output.accept(BlockInit.BUDDING_CRYSTALLIZED_ORDO.get());
                        output.accept(BlockInit.CRYSTALLIZED_ORDO_CLUSTER.get());
                        output.accept(BlockInit.LARGE_CRYSTALLIZED_ORDO_BUD.get());
                        output.accept(BlockInit.MEDIUM_CRYSTALLIZED_ORDO_BUD.get());
                        output.accept(BlockInit.SMALL_CRYSTALLIZED_ORDO_BUD.get());

                        output.accept(ItemInit.CRYSTALLIZED_PERDITIO_SHARD.get());
                        output.accept(BlockInit.CRYSTALLIZED_PERDITIO_BLOCK.get());
                        output.accept(BlockInit.BUDDING_CRYSTALLIZED_PERDITIO.get());
                        output.accept(BlockInit.CRYSTALLIZED_PERDITIO_CLUSTER.get());
                        output.accept(BlockInit.LARGE_CRYSTALLIZED_PERDITIO_BUD.get());
                        output.accept(BlockInit.MEDIUM_CRYSTALLIZED_PERDITIO_BUD.get());
                        output.accept(BlockInit.SMALL_CRYSTALLIZED_PERDITIO_BUD.get());
                    })
                    .build());


    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
    }
}
