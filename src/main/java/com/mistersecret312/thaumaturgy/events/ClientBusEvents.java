package com.mistersecret312.thaumaturgy.events;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mistersecret312.thaumaturgy.items.RevelationGogglesItem;
import com.mistersecret312.thaumaturgy.tooltipcomponents.AspectTooltipComponent;
import com.mistersecret312.thaumaturgy.util.RenderBlitUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = ArcaneThaumaturgyMod.MODID, value = Dist.CLIENT)
public class ClientBusEvents
{

    @SubscribeEvent
    public static void renderThaumometerData(RenderGuiEvent event)
    {
        final PoseStack pose = event.getGuiGraphics().pose();
        Minecraft minecraft = Minecraft.getInstance();
        int x = minecraft.getWindow().getGuiScaledWidth()/2;
        int y = minecraft.getWindow().getGuiScaledHeight()/2;
        LocalPlayer player = minecraft.player;
        if(player == null)
            return;

        if(!minecraft.options.getCameraType().isFirstPerson())
            return;

        if(!player.getOffhandItem().is(ItemInit.THAUMOMETER.get()) && !player.getMainHandItem().is(ItemInit.THAUMOMETER.get()))
            return;

        ClientLevel level = (ClientLevel) player.level();
        BlockHitResult rayTrace = level.clip(new ClipContext(player.getEyePosition(1F), player.getEyePosition(1F).add(player.getLookAngle().scale(5F)),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, null));

        BlockPos pos = rayTrace.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        if(!blockState.isAir())
        {
            ClientPacketListener packetListener = minecraft.getConnection();
            RegistryAccess access = packetListener.registryAccess();
            Registry<AspectComposition> aspectCompositions = access.registryOrThrow(AspectComposition.REGISTRY_KEY);
            Registry<Aspect> aspects = access.registryOrThrow(Aspect.REGISTRY_KEY);

            Stream<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> filtered = aspectCompositions.entrySet().stream().filter(key -> {
                Item item = key.getValue().getItem();
                return blockState.getBlock().asItem().equals(item);
            });
            Optional<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> object = filtered.findFirst();
            if(object.isPresent())
            {
                for (int i = 0; i < object.get().getValue().getAspects().size(); i++)
                {
                    AspectComposition.AspectStack stack = object.get().getValue().getAspects().get(i);
                    Aspect aspect = aspects.get(stack.getAspect());
                    if (aspect != null)
                    {
                        pose.pushPose();
                        pose.translate(x, y, 0);

                        pose.pushPose();
                        MutableComponent name = blockState.getBlock().getName();
                        pose.scale(1.5f, 1.5f, 0f);
                        minecraft.font.drawInBatch(name, -30, -50, -1, false, pose.last().pose(), event.getGuiGraphics().bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                        pose.popPose();

                        pose.translate((object.get().getValue().getAspects().size()*-11)+(22*i), 15, 0);
                        RenderBlitUtil.blit(aspect.getTexture(), pose,0, 0, 0, 0, 18, 18, 18, 18);
                        minecraft.font.drawInBatch(String.valueOf(stack.getAmount()), 15, 12, -1, false, pose.last().pose(), event.getGuiGraphics().bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                        pose.popPose();
                    }
                }
            }
        }
    }


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
        boolean reveal = player.getInventory().armor.stream().anyMatch(item -> item.getItem() instanceof RevelationGogglesItem);
        if(object.isPresent() && reveal)
        {
            AspectComposition composition = object.get().getValue();
            event.getTooltipElements().add(Either.right(new AspectTooltipComponent(composition)));
        }
    }

    private static void bobView(PoseStack pPoseStack, float pPartialTicks) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player) {
            float f = player.walkDist - player.walkDistO;
            float f1 = -(player.walkDist + f * pPartialTicks);
            float f2 = Mth.lerp(pPartialTicks, player.oBob, player.bob);
            pPoseStack.translate(2*Mth.sin(f1 * (float)Math.PI) * f2 * 0.5F, -Math.abs(Mth.cos(f1 * (float)Math.PI) * f2), 0.0F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(2*Mth.sin(f1 * (float)Math.PI) * f2 * 3.0F));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(Math.abs(2*Mth.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F));
        }
    }

}
