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

import java.util.stream.IntStream;

public class ItemTabInit
{
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = TABS.register("main_tab",
            () -> CreativeModeTab.builder().icon(() -> BlockInit.CRUCIBLE.get().asItem().getDefaultInstance())
                    .title(Component.translatable("creativetab.main_tab"))
                    .displayItems((parameters, output) ->
                    {
                        output.accept(BlockInit.CRUCIBLE.get());
                        output.accept(AspectDisplayTest.create(ResourceKey.create(Aspect.REGISTRY_KEY, new ResourceLocation("thaumaturgy:terra"))));

                        output.accept(WandItem.createFull(ItemInit.IRON_WAND.get(), 25));
                        output.accept(WandItem.createFull(ItemInit.GOLD_WAND.get(), 50));

                        output.accept(ItemInit.IRON_KNOB.get());
                        output.accept(ItemInit.GOLD_KNOB.get());
                    })
                    .build());


    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
    }
}
