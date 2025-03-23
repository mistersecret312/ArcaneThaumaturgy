package com.mistersecret312.thaumaturgy.events;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.items.AspectDisplayTest;
import com.mistersecret312.thaumaturgy.tooltipcomponents.AspectTooltipComponent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector2i;
import org.joml.Vector2ic;

@Mod.EventBusSubscriber(modid = ArcaneThaumaturgyMod.MODID, value = Dist.CLIENT)
public class ClientBusEvents
{

    @SubscribeEvent
    public static void aspectTooltip(RenderTooltipEvent.GatherComponents event)
    {
        ItemStack stack = event.getItemStack();
        if(stack.getItem() instanceof AspectDisplayTest item)
        {
            Minecraft minecraft = Minecraft.getInstance();
            ClientPacketListener clientPacketListener = minecraft.getConnection();
            RegistryAccess registries = clientPacketListener.registryAccess();
            Registry<Aspect> aspectsRegistry = registries.registryOrThrow(Aspect.REGISTRY_KEY);

            Aspect aspect = aspectsRegistry.get(item.getAspect(stack));
            if(aspect != null)
            {
                event.getTooltipElements().add(Either.right(new AspectTooltipComponent(aspect)));
            }
        }
    }

}
