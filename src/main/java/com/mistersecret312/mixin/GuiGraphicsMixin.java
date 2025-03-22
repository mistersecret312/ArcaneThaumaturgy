package com.mistersecret312.mixin;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.client.ThaumaturgyRenderTypes;
import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.items.AspectDisplayTest;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.joml.Matrix4f;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin
{

    @Shadow private ItemStack tooltipStack;

    @Shadow @Final private MultiBufferSource.BufferSource bufferSource;

    @Shadow @Final private PoseStack pose;

    @Inject(method = "renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void renderAspect(Font pFont, List<ClientTooltipComponent> pComponents, int pMouseX, int pMouseY,
                             ClientTooltipPositioner pTooltipPositioner, CallbackInfo ci,
                             RenderTooltipEvent.Pre preEvent, int i, int j, int i2, int j2, Vector2ic vector2ic, int l,
                             int i1, int j1)
    {
        GuiGraphics graphics = ((GuiGraphics) (Object) this);
        ItemStack stack = tooltipStack;
        final PoseStack pose = graphics.pose();

        if(stack.getItem() instanceof AspectDisplayTest item)
        {
            Minecraft minecraft = Minecraft.getInstance();
            ClientPacketListener clientPacketListener = minecraft.getConnection();
            RegistryAccess registries = clientPacketListener.registryAccess();
            Registry<Aspect> aspectsRegistry = registries.registryOrThrow(Aspect.REGISTRY_KEY);

            Aspect aspect = aspectsRegistry.get(item.getAspect(stack));
            if(aspect != null)
            {
                pose.pushPose();
                this.transparentBlit(new ResourceLocation(ArcaneThaumaturgyMod.MODID, "textures/aspect_shadow.png"), vector2ic.x()-6, vector2ic.x()-6+22, vector2ic.y()-26, vector2ic.y()-26+22, 0, 0, 22, 0, 22);
                //graphics.blit(new ResourceLocation(ArcaneThaumaturgyMod.MODID, "textures/aspect_shadow.png"), vector2ic.x()-6, vector2ic.y()-26, 0, 0, 22, 22, 22, 22);
                pose.popPose();

                pose.pushPose();
                graphics.blit(aspect.getTexture(), vector2ic.x()-4, vector2ic.y()-24, 0, 0, 18, 18, 18, 18);
                pFont.drawInBatch("15", vector2ic.x()+12, vector2ic.y()-12, -1, true, pose.last().pose(), this.bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                pose.popPose();
            }
        }

    }

    private void transparentBlit(ResourceLocation pAtlasLocation, int pX1, int pX2, int pY1, int pY2, int pBlitOffset, float pMinU, float pMaxU, float pMinV, float pMaxV)
    {
        Matrix4f matrix4f = this.pose.last().pose();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.textSeeThrough(pAtlasLocation));
        consumer.vertex(matrix4f, (float)pX1, (float)pY1, (float)pBlitOffset).color(255, 255, 255, 255).uv(pMinU, pMinV).uv2(15728880).endVertex();
        consumer.vertex(matrix4f, (float)pX1, (float)pY2, (float)pBlitOffset).color(255, 255, 255, 255).uv(pMinU, pMaxV).uv2(15728880).endVertex();
        consumer.vertex(matrix4f, (float)pX2, (float)pY2, (float)pBlitOffset).color(255, 255, 255, 255).uv(pMaxU, pMaxV).uv2(15728880).endVertex();
        consumer.vertex(matrix4f, (float)pX2, (float)pY1, (float)pBlitOffset).color(255, 255, 255, 255).uv(pMaxU, pMinV).uv2(15728880).endVertex();
    }

}
