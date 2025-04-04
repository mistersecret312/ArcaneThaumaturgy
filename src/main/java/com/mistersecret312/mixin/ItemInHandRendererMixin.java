package com.mistersecret312.mixin;

import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin
{
    @Shadow private ItemStack offHandItem;

    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract void renderMapHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight,
                                                  HumanoidArm pSide);

    @Shadow public abstract void renderItem(LivingEntity pEntity, ItemStack pItemStack,
                                            ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack,
                                            MultiBufferSource pBuffer, int pSeed);

    @Inject(method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
    at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER), cancellable = true)
    public void thaumometerRender(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch,
                                  InteractionHand pHand, float pSwingProgress, ItemStack pStack,
                                  float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer,
                                  int pCombinedLight, CallbackInfo ci)
    {
        if(renderThaumometer(pPlayer, pHand, pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pPitch))
            ci.cancel();
    }

    private boolean renderThaumometer(AbstractClientPlayer pPlayer, InteractionHand pHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, float pEquippedProgress, float pSwingProgress, float pPitch)
    {
        if(pPlayer.getItemInHand(pHand).is(ItemInit.THAUMOMETER.get()))
        {
            if(pHand == InteractionHand.MAIN_HAND && this.offHandItem.isEmpty())
            {
                if (!this.minecraft.player.isInvisible()) {
                    pPoseStack.pushPose();

                    float f = 0;
                    float f1 = -0.2F * Mth.sin(pSwingProgress * (float)Math.PI);
                    float f2 = -0.4F * Mth.sin(f * (float)Math.PI);
                    pPoseStack.translate(0.0F, -f1 / 2.0F, f2);
                    float f3 = 0f;
                    pPoseStack.translate(0.0F, 0.04F + pEquippedProgress * -1.2F + f3 * -0.5F, -0.72F);
                    pPoseStack.mulPose(Axis.XP.rotationDegrees(f3 * -85.0F));
                    if (!this.minecraft.player.isInvisible()) {
                        pPoseStack.pushPose();
                        pPoseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                        this.renderMapHand(pPoseStack, pBuffer, pCombinedLight, HumanoidArm.RIGHT);
                        this.renderMapHand(pPoseStack, pBuffer, pCombinedLight, HumanoidArm.LEFT);
                        pPoseStack.popPose();
                    }

                    float f4 = Mth.sin(f * (float)Math.PI);
                    pPoseStack.mulPose(Axis.XP.rotationDegrees(f4*20.0F));
                    pPoseStack.translate(0f, -0.17f, 0f);
                    renderItem(pPlayer, pPlayer.getItemInHand(pHand), ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, false, pPoseStack, pBuffer, pCombinedLight);

                    pPoseStack.popPose();
                    return true;
                }
            }
        }
        return false;
    }
}
