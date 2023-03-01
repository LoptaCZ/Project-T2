package com.raven_cze.projt2.common.content.items;

import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ItemMaskCruelty extends PT2ArmorItem{
	public ItemMaskCruelty(Properties properties){
		super(PT2ArmorItem.PT2ArmorMats.SPECIAL,EquipmentSlot.HEAD,properties);
	}

	@Override
	public void onArmorTick(ItemStack stack,Level level,Player player){
		LivingEntity entity=(LivingEntity)getPointedEntity(player,24.0D);
		if(entity != null){
			switch (level.random.nextInt(4)) {
				case 0 -> entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN));
				case 1 -> entity.addEffect(new MobEffectInstance(MobEffects.POISON));
				case 2 -> entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS));
				case 3 -> entity.hurt(DamageSource.playerAttack(player), 1);
			}
		}
	}

	public Entity getPointedEntity(Player ply,Double range){
		EntityHitResult rayTrace=(EntityHitResult)Minecraft.getInstance().hitResult;
		Entity ent=rayTrace.getEntity();
		double dist=ent.position().distanceTo(ply.position());
		return dist<=range?ent:null;
	}
}
