package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.init.EntityTypeInit;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.renderer.entity.NoopRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRenderersInit {
    private EntityRenderersInit() {}

    @SubscribeEvent
    public static void onFMLCLientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityTypeInit.SEAT.get(), NoopRenderer::new);
    }
}
