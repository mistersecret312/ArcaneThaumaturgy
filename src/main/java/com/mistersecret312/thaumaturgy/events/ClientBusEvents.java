package com.mistersecret312.thaumaturgy.events;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.items.AspectDisplayTest;
import com.mistersecret312.thaumaturgy.items.GogglesItem;
import com.mistersecret312.thaumaturgy.tooltipcomponents.AspectTooltipComponent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = ArcaneThaumaturgyMod.MODID, value = Dist.CLIENT)
public class ClientBusEvents
{

    @SubscribeEvent
    public static void aspectTooltip(RenderTooltipEvent.GatherComponents event)
    {
        ItemStack stack = event.getItemStack();
        Minecraft minecraft = Minecraft.getInstance();
        ClientPacketListener clientPacketListener = minecraft.getConnection();
        RegistryAccess registries = clientPacketListener.registryAccess();
        Registry<AspectComposition> aspectCompositions = registries.registryOrThrow(AspectComposition.REGISTRY_KEY);

        Stream<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> filtered = aspectCompositions.entrySet().stream().filter(key -> {
            Item item = key.getValue().getItem();
            return event.getItemStack().is(item);
        });

        Optional<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> object = filtered.findFirst();
        LocalPlayer player = Minecraft.getInstance().player;
        boolean reveal = player.getInventory().armor.stream().anyMatch(item -> item.getItem() instanceof GogglesItem);
        if(object.isPresent() && reveal)
        {
            AspectComposition composition = object.get().getValue();
            event.getTooltipElements().add(Either.right(new AspectTooltipComponent(composition)));
        }
    }

}
