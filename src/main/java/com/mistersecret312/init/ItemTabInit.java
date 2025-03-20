package com.mistersecret312.init;

import com.mistersecret312.datapack.Aspect;
import com.mistersecret312.items.AspectDisplayTest;
import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
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
            () -> CreativeModeTab.builder().icon(() -> ItemInit.DISPLAY_TEST.get().getDefaultInstance())
                    .title(Component.translatable("creativetab.main_tab"))
                    .displayItems((parameters, output) ->
                    {
                        output.accept(AspectDisplayTest.create(ResourceKey.create(Aspect.REGISTRY_KEY, new ResourceLocation("thaumaturgy:terra"))));
                    })
                    .build());


    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
    }
}
