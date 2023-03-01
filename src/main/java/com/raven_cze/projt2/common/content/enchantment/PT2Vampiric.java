package com.raven_cze.projt2.common.content.enchantment;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PT2Vampiric extends Enchantment {
	public PT2Vampiric(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slot) {
		super(rarity,category,slot);
		}
		@Override
		public int getMaxLevel(){return 3;}
		
		@Override
		public int getMinCost(int cost){return 25+20*(cost-1);}
		
		@Override
		public int getMaxCost(int cost){return super.getMinCost(cost)+50;}
		
		@Override
		public void doPostAttack(LivingEntity p_44686_,Entity p_44687_,int p_44688_){
			super.doPostAttack(p_44686_, p_44687_, p_44688_);
		}
}
