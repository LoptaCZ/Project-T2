package com.raven_cze.projt2.common.content.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PT2Repair extends Enchantment{
	public PT2Repair(Rarity rarity,EnchantmentCategory category,EquipmentSlot[]slot){
		super(rarity,category,slot);
	}
	@Override
	public int getMaxLevel(){return 1;}
	@Override
	public boolean canEnchant(ItemStack stack){
		if(stack.getItem()instanceof com.raven_cze.projt2.api.IVisRepairable||stack.isRepairable()){
			stack.getItem();
			return true;
		}
		return false;
	}
	
	@Override
	public int getMinCost(int cost){return 35;}
	
	@Override
	public int getMaxCost(int cost){return super.getMinCost(cost)+50;}
}
