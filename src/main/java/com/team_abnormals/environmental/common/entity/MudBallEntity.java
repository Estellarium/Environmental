package com.team_abnormals.environmental.common.entity;

import com.team_abnormals.environmental.common.entity.util.SlabfishOverlay;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MudBallEntity extends ProjectileItemEntity {
   public MudBallEntity(EntityType<? extends SnowballEntity> p_i50159_1_, World p_i50159_2_) {
      super(p_i50159_1_, p_i50159_2_);
   }

   public MudBallEntity(World worldIn, LivingEntity throwerIn) {
      super(EntityType.SNOWBALL, throwerIn, worldIn);
   }

   public MudBallEntity(World worldIn, double x, double y, double z) {
      super(EntityType.SNOWBALL, x, y, z, worldIn);
   }

   protected Item getDefaultItem() {
      return Items.SNOWBALL;
   }

   @OnlyIn(Dist.CLIENT)
   private IParticleData makeParticle() {
      ItemStack itemstack = this.func_213882_k();
      return (IParticleData)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleData(ParticleTypes.ITEM, itemstack));
   }

   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 3) {
         IParticleData iparticledata = this.makeParticle();

         for(int i = 0; i < 8; ++i) {
            this.world.addParticle(iparticledata, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
         }
      }

   }

   protected void onImpact(RayTraceResult result) {
      if (result.getType() == RayTraceResult.Type.ENTITY) {
         Entity entity = ((EntityRayTraceResult)result).getEntity();
         entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), (float)0);
         if (entity instanceof SlabfishEntity) {
        	 SlabfishEntity slabby = (SlabfishEntity)entity;
        	 if(slabby.getSlabfishOverlay() != SlabfishOverlay.MUDDY) slabby.setSlabfishOverlay(SlabfishOverlay.MUDDY);
         }
      }

      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)3);
         this.remove();
      }

   }
}