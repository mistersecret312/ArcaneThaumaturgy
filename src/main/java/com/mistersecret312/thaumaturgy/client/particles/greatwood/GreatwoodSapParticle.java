package com.mistersecret312.thaumaturgy.client.particles.greatwood;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GreatwoodSapParticle extends TextureSheetParticle
{
   protected boolean isGlowing;

   GreatwoodSapParticle(ClientLevel pLevel, double pX, double pY, double pZ)
   {
      super(pLevel, pX, pY, pZ);
      this.setSize(0.01F, 0.01F);
      this.gravity = 0.06F;
   }

   public void setGravity(float gravity)
   {
      this.gravity = gravity;
   }

   public float getGravity()
   {
      return gravity;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public int getLightColor(float pPartialTick) {
      return this.isGlowing ? 240 : super.getLightColor(pPartialTick);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.preMoveUpdate();
      if (!this.removed) {
         this.yd -= (double)this.gravity;
         this.move(this.xd, this.yd, this.zd);
         this.postMoveUpdate();
         if (!this.removed) {
            this.xd *= (double)0.98F;
            this.yd *= (double)0.98F;
            this.zd *= (double)0.98F;
         }
      }
   }

   protected void preMoveUpdate() {
      if (this.lifetime-- <= 0) {
         this.remove();
      }

   }

   protected void postMoveUpdate()
   {
   }

   @OnlyIn(Dist.CLIENT)
   static class DripHangParticleGreatwood extends GreatwoodSapParticle
   {
      private final ParticleOptions fallingParticle;

      DripHangParticleGreatwood(ClientLevel pLevel, double pX, double pY, double pZ, ParticleOptions pFallingParticle) {
         super(pLevel, pX, pY, pZ);
         this.fallingParticle = pFallingParticle;
         this.gravity *= 0.02F;
         this.lifetime = 40;
      }

      protected void preMoveUpdate() {
         if (this.lifetime-- <= 0) {
            this.remove();
            this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
         }

      }

      protected void postMoveUpdate() {
         this.xd *= 0.02D;
         this.yd *= 0.02D;
         this.zd *= 0.02D;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class DripLandParticleGreatwood extends GreatwoodSapParticle
   {
      DripLandParticleGreatwood(ClientLevel p_106102_, double p_106103_, double p_106104_, double p_106105_) {
         super(p_106102_, p_106103_, p_106104_, p_106105_);
         this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class FallAndLandParticleGreatwood extends FallingParticleGreatwood
   {
      protected final ParticleOptions landParticle;

      FallAndLandParticleGreatwood(ClientLevel pLevel, double pX, double pY, double pZ, ParticleOptions pLandParticle) {
         super(pLevel, pX, pY, pZ);
         this.landParticle = pLandParticle;
      }

      protected void postMoveUpdate() {
         if (this.onGround) {
            this.remove();
            this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class FallingParticleGreatwood extends GreatwoodSapParticle
   {
      FallingParticleGreatwood(ClientLevel pLevel, double pX, double pY, double pZ) {
         this(pLevel, pX, pY, pZ, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
      }

      FallingParticleGreatwood(ClientLevel pLevel, double pX, double pY, double pZ, int pLifetime) {
         super(pLevel, pX, pY, pZ);
         this.lifetime = pLifetime;
      }

      protected void postMoveUpdate() {
         if (this.onGround) {
            this.remove();
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class HoneyFallAndLandParticleGreatwood extends FallAndLandParticleGreatwood
   {
      HoneyFallAndLandParticleGreatwood(ClientLevel p_106146_, double p_106147_, double p_106148_, double p_106149_, ParticleOptions p_106151_) {
         super(p_106146_, p_106147_, p_106148_, p_106149_, p_106151_);
      }

      protected void postMoveUpdate() {
         if (this.onGround) {
            this.remove();
            this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            float f = Mth.randomBetween(this.random, 0.3F, 1.0F);
            this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.BEEHIVE_DRIP, SoundSource.BLOCKS, f, 1.0F, false);
         }

      }
   }
}