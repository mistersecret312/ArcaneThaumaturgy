package com.mistersecret312.thaumaturgy;

import com.mistersecret312.thaumaturgy.client.Layers;
import com.mistersecret312.thaumaturgy.client.gui.WandAspectOverlay;
import com.mistersecret312.thaumaturgy.client.renderer.NitorRenderer;
import com.mistersecret312.thaumaturgy.client.renderer.PedestalRenderer;
import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.init.*;
import com.mistersecret312.thaumaturgy.tooltipcomponents.AspectTooltipComponent;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;

import java.util.function.Function;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArcaneThaumaturgyMod.MODID)
public class ArcaneThaumaturgyMod
{
    public static final String MODID = "thaumaturgy";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<Item> WANDS = new TagKey<>(Registries.ITEM, new ResourceLocation(MODID, "wands"));

    public ArcaneThaumaturgyMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        BlockInit.register(modEventBus);
        BlockEntityInit.register(modEventBus);
        ItemInit.register(modEventBus);
        ItemTabInit.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener((DataPackRegistryEvent.NewRegistry event) -> {
            event.dataPackRegistry(Aspect.REGISTRY_KEY, Aspect.CODEC, Aspect.CODEC);
            event.dataPackRegistry(AspectComposition.REGISTRY_KEY, AspectComposition.CODEC, AspectComposition.CODEC);
        });

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(Layers::registerLayers);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            NetworkInit.registerPackets();
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerTooltip(RegisterClientTooltipComponentFactoriesEvent event)
        {
            event.register(AspectTooltipComponent.class, Function.identity());
        }

        @SubscribeEvent
        public static void registerOverlays(RegisterGuiOverlaysEvent event)
        {
            event.registerAboveAll("wand_aspects", WandAspectOverlay.OVERLAY);
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerBlockEntityRenderer(BlockEntityInit.PEDESTAL.get(), PedestalRenderer::new);
            event.registerBlockEntityRenderer(BlockEntityInit.NITOR.get(), NitorRenderer::new);
        }

    }
}
