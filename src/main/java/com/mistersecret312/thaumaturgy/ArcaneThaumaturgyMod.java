package com.mistersecret312.thaumaturgy;

import com.mistersecret312.thaumaturgy.client.Layers;
import com.mistersecret312.thaumaturgy.client.gui.WandAspectOverlay;
import com.mistersecret312.thaumaturgy.client.renderer.*;
import com.mistersecret312.thaumaturgy.client.screen.ArcaneWorkbenchScreen;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.init.*;
import com.mistersecret312.thaumaturgy.tooltipcomponents.AspectTooltipComponent;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        SoundInit.register(modEventBus);
        EntityTypeInit.register(modEventBus);
        RecipeTypeInit.register(modEventBus);
        RecipeTypeInit.Types.register(modEventBus);
        AspectInit.register(modEventBus);
        MenuInit.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener((DataPackRegistryEvent.NewRegistry event) -> {
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
        public static void clientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() ->
            {
                EntityRenderers.register(EntityTypeInit.HOVERING_ITEM.get(), HoveringItemRenderer::new);

                MenuScreens.register(MenuInit.ARCANE_WORKBENCH.get(), ArcaneWorkbenchScreen::new);
            });
        }

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
            event.registerBlockEntityRenderer(BlockEntityInit.CRUCIBLE.get(), CrucibleRenderer::new);
        }

        @SubscribeEvent
        public static void registerReloadListeners(RegisterClientReloadListenersEvent event)
        {
            event.registerReloadListener(ThaumometerInfoRenderer.INSTANCE);
        }

/*        @SubscribeEvent
        public static void itemColors(RegisterColorHandlersEvent.Item event)
        {
            event.register((stack, color) -> color != 0 ? -1 : NitorItemOld.getColorData(stack), ItemInit.NITOR_OLD.get());
        }*/

        @SubscribeEvent
        public static void onModelBaked(ModelEvent.RegisterAdditional event)
        {
            event.register(ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "item/thaumometer_baked"));
        }

    }
}
