package com.mistersecret312.thaumaturgy.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import java.util.Random;

public class HoveringItemRenderer extends ItemEntityRenderer
{
    private static final float HALF_SQRT_3 = (float) (Math.sqrt(3.0D) / 2.0D);

    public HoveringItemRenderer(EntityRendererProvider.Context pContext)
    {
        super(pContext);
    }

    @Override
    public void render(ItemEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack poseStack,
                       MultiBufferSource pBuffer, int pPackedLight)
    {
        Random random = new Random();
        {
            poseStack.pushPose();
            VertexConsumer vertexBuilder = pBuffer.getBuffer(RenderType.lightning());

            random = new Random(432L);

            float f5 = (150) / 200.0F;

            float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);

            float sine = 0;
            //sine = (float) ((Math.sin(0.1 * (pEntity.level().dayTime()) + pEntity.getOnPos().asLong()) * 0.25f)) + 1f;
            sine = 1;
            if (sine < 0)
            {
                sine = 0;
            }

            boolean shouldRotateOtherWay = pEntity.getOnPos().asLong() % 3 == 0;

            RenderSystem.disableBlend();
            poseStack.mulPose(Axis.YP.rotationDegrees(pEntity.getAge() * (shouldRotateOtherWay ? -0.5f : 0.5f)));
            poseStack.scale(0.075F * sine, 0.075F * sine, 0.075F * sine);
            for (int x = 0; (float) x < (f5 + f5 * f5) / 2.0F * 60.0F; ++x)
            {
                poseStack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360.0F + f5 * 90.0F));
                float randomFloat = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
                float randomFloat2 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
                poseStack.pushPose();
                poseStack.scale(0.3f, 0.3f, 0.3f);
                Matrix4f matrix4f = poseStack.last().pose();
                int k = (int) (255.0F * (1.0F - f7));
                vertex01(vertexBuilder, matrix4f, k);
                vertex2(vertexBuilder, matrix4f, randomFloat, randomFloat2);
                vertex3(vertexBuilder, matrix4f, randomFloat, randomFloat2);
                vertex01(vertexBuilder, matrix4f, k);
                vertex3(vertexBuilder, matrix4f, randomFloat, randomFloat2);
                vertex4(vertexBuilder, matrix4f, randomFloat, randomFloat2);
                vertex01(vertexBuilder, matrix4f, k);
                vertex4(vertexBuilder, matrix4f, randomFloat, randomFloat2);
                vertex2(vertexBuilder, matrix4f, randomFloat, randomFloat2);
                poseStack.popPose();
            }
            poseStack.popPose();
        }

        poseStack.pushPose();
        ItemStack itemstack = pEntity.getItem();
        int i = itemstack.isEmpty() ? 187 : Item.getId(itemstack.getItem()) + itemstack.getDamageValue();
        random.setSeed((long) i);
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(itemstack, pEntity.level(), (LivingEntity) null, pEntity.getId());
        boolean flag = bakedmodel.isGui3d();
        int j = this.getRenderAmount(itemstack);
        float f = 0.25F;
        float f1 = 0;
        float f2 = bakedmodel.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
        poseStack.translate(0.0F, f1 + 0.25F * f2, 0.0F);
        float f3 = pEntity.getSpin(pPartialTicks);
        poseStack.mulPose(Axis.YP.rotation(f3));
        if (!flag)
        {
            float f7 = -0.0F * (float) (j - 1) * 0.5F;
            float f8 = -0.0F * (float) (j - 1) * 0.5F;
            float f9 = -0.09375F * (float) (j - 1) * 0.5F;
            poseStack.translate(f7, f8, f9);
        }

        for (int k = 0; k < j; ++k)
        {
            poseStack.pushPose();
            if (k > 0)
            {
                if (flag)
                {
                    float f11 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f13 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f10 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    poseStack.translate(shouldSpreadItems() ? f11 : 0, shouldSpreadItems() ? f13 : 0, shouldSpreadItems() ? f10 : 0);
                } else
                {
                    float f12 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    float f14 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    poseStack.translate(shouldSpreadItems() ? f12 : 0, shouldSpreadItems() ? f14 : 0, 0.0D);
                }
            }

            poseStack.translate(0f, -0.225f, 0f);

            RenderSystem.disableBlend();
            Minecraft.getInstance().getItemRenderer().render(itemstack, ItemDisplayContext.GROUND, false, poseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, bakedmodel);
            poseStack.popPose();
            if (!flag)
            {
                poseStack.translate(0.0, 0.0, 0.09375F);
            }
        }

        poseStack.popPose();
        //super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
    }

    private static void vertex01(VertexConsumer iVertexBuilder, Matrix4f matrix4f, int p_229061_2_) {
        iVertexBuilder.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_229061_2_).endVertex();
        iVertexBuilder.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_229061_2_).endVertex();
    }

    private static void vertex2(VertexConsumer iVertexBuilder, Matrix4f matrix4f, float p_229060_2_, float p_229060_3_) {
        iVertexBuilder.vertex(matrix4f, -HALF_SQRT_3 * p_229060_3_, p_229060_2_, -0.5F * p_229060_3_).color(255, 0, 255, 0).endVertex();
    }

    private static void vertex3(VertexConsumer iVertexBuilder, Matrix4f matrix4f, float p_229062_2_, float p_229062_3_) {
        iVertexBuilder.vertex(matrix4f, HALF_SQRT_3 * p_229062_3_, p_229062_2_, -0.5F * p_229062_3_).color(255, 0, 255, 0).endVertex();
    }

    private static void vertex4(VertexConsumer iVertexBuilder, Matrix4f matrix4f, float p_229063_2_, float p_229063_3_) {
        iVertexBuilder.vertex(matrix4f, 0.0F, p_229063_2_, 1.0F * p_229063_3_).color(255, 0, 255, 0).endVertex();
    }
}
