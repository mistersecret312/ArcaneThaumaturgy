package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.block_entities.CrucibleBlockEntity;
import com.mistersecret312.thaumaturgy.client.ThaumaturgyRenderTypes;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.items.RevelationGogglesItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CrucibleRenderer implements BlockEntityRenderer<CrucibleBlockEntity>
{
    public CrucibleRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(CrucibleBlockEntity crucible, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int pPackedOverlay)
    {
        LocalPlayer player = Minecraft.getInstance().player;
        ClientLevel level = (ClientLevel) player.level();
        BlockHitResult rayTrace = level.clip(new ClipContext(player.getEyePosition(1F), player.getEyePosition(1F).add(player.getLookAngle().scale(5F)),
                ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null));

        BlockPos pos = rayTrace.getBlockPos();
        BlockState blockState = level.getBlockState(pos);

        boolean reveal = player.getInventory().armor.stream().anyMatch(item -> item.getItem() instanceof RevelationGogglesItem);

        if(blockState.is(BlockInit.CRUCIBLE.get()) && reveal)
        {
            poseStack.translate(0.2*crucible.handler.getSize()-((crucible.handler.getSize()-1)*0.3), 0 ,0);
            for (int i = 0; i < crucible.handler.getSize(); i++)
            {
                AspectStack stack = crucible.handler.getStackInSlot(i);
                Aspect aspect = stack.getAspect();

                ResourceLocation texture = aspect.getTexture();
                if (Minecraft.getInstance().getResourceManager().getResource(texture).isEmpty())
                    texture = ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "textures/aspect/error.png");

                poseStack.pushPose();

                poseStack.translate(0.5f, 1.3f, 0.5f);
                poseStack.scale(0.25f, 0.25f, 0.25f);

                poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                poseStack.mulPose(Axis.YP.rotationDegrees(180));

                poseStack.translate((float) -crucible.handler.getSize() /2, 0, 0);
                poseStack.translate(i, 0, 0);

                VertexConsumer consumerA = buffer.getBuffer(ThaumaturgyRenderTypes.aspect(texture));
                consumerA.vertex(poseStack.last().pose(), 0f, 0f, 0).uv(0, 1).endVertex();
                consumerA.vertex(poseStack.last().pose(), 1f, 0f, 0).uv(1, 1).endVertex();
                consumerA.vertex(poseStack.last().pose(), 1f, 1f, 0).uv(1, 0).endVertex();
                consumerA.vertex(poseStack.last().pose(), 0f, 1f, 0).uv(0, 0).endVertex();

                poseStack.mulPose(Axis.YN.rotationDegrees(180));
                poseStack.mulPose(Axis.ZP.rotationDegrees(180));
                poseStack.scale(0.05f, 0.05f, 0.05f);
                poseStack.translate(3f, -2f, 0f);
                int size = Minecraft.getInstance().font.width(String.valueOf(stack.getAmount()));
                poseStack.translate(-2.5*(size > 10 ? (double) size /10 : 0), 0, 0);
                Minecraft.getInstance().font.drawInBatch(String.valueOf(stack.getAmount()), (float) 1, 10, -1, false, poseStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);

                poseStack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(CrucibleBlockEntity pBlockEntity)
    {
        return true;
    }
}
