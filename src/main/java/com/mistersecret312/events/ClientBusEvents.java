package com.mistersecret312.events;

import com.mistersecret312.datapack.Aspect;
import com.mistersecret312.items.AspectDisplayTest;
import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

import java.nio.Buffer;

@Mod.EventBusSubscriber(modid = ArcaneThaumaturgyMod.MODID, value = Dist.CLIENT)
public class ClientBusEvents
{

    @SubscribeEvent
    public static void renderTooltip(RenderTooltipEvent.Color event)
    {
        GuiGraphics graphics = event.getGraphics();
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

                PoseStack pose = graphics.pose();
                Matrix4f matrix4f = pose.last().pose();

                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder builder = tesselator.getBuilder();

                RenderSystem.setShaderTexture(0, aspect.getTexture());
                builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                builder.vertex(matrix4f, event.getX(), event.getY(), 1f);
                BufferUploader.drawWithShader(builder.end());

                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            }
        }
    }

}
