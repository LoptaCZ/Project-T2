package com.raven_cze.projt2.common.util;

import java.util.function.Supplier;

import com.raven_cze.projt2.ProjectT2;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class BaseArmorMaterial implements ArmorMaterial{
	private final int enchantibility;
	private final int[] durability,damageReduction;
	private final float knockbackResistance,toughness;
	private final String name;
	private final SoundEvent equipSound;
	private final Supplier<Ingredient>repairMaterial;
	
	public BaseArmorMaterial(int enchantibility,int[]durability,int[]damageReduction,float knockbackResistance,float toughness,String name,SoundEvent equipSound,Supplier<Ingredient> repairMaterial){
		this.enchantibility=enchantibility;
		this.durability=durability;
		this.damageReduction=damageReduction;
		this.knockbackResistance=knockbackResistance;
		this.toughness=toughness;
		this.name=name;
		this.equipSound=equipSound;
		this.repairMaterial=repairMaterial;
		
	}
	@Override
	public int getDurabilityForSlot(EquipmentSlot slot){return this.durability[slot.getIndex()];}
	@Override
	public int getDefenseForSlot(EquipmentSlot slot){return this.damageReduction[slot.getIndex()];}
	@Override
	public int getEnchantmentValue(){return this.enchantibility;}
	@Override
	public SoundEvent getEquipSound(){return this.equipSound;}
	@Override
	public Ingredient getRepairIngredient(){return this.repairMaterial.get();}
	@Override
	public String getName(){return this.name;}
	@Override
	public float getToughness(){return this.toughness;}
	@Override
	public float getKnockbackResistance(){return this.knockbackResistance;}
	
	public enum ArmorMat implements ArmorMaterial{
		THAUMIUM("thaumium",25,new int[]{2,6,5,2},25,SoundEvents.ARMOR_EQUIP_IRON,0.0F,0.0F,()->{return Ingredient.of(Items.IRON_INGOT);}),
		VOID("void",45,new int[]{3,8,6,3},17,SoundEvents.ARMOR_EQUIP_IRON,0.0F,0.0F,()->{return Ingredient.of(Items.IRON_INGOT);}),
		SPECIAL("special",25,new int[]{1,3,2,1},10,SoundEvents.ARMOR_EQUIP_LEATHER,0.0F,0.0F,()->{return Ingredient.of(Items.IRON_INGOT);});
		
		private static final int[] HEALTH = new int[]{13,15,16,11};
		private final String name;
		private final int durabilityMul;
		private final int[]protection;
		private final int enchantValue;
		private final SoundEvent sound;
		private final float toughness;
		private final float knockback;
		private final Supplier<Ingredient>repairMat;

		ArmorMat(String name,int durabilityMultiplyer,int[]protection,int enchant,SoundEvent equipSound,float toughness,float knockbackResist,Supplier<Ingredient>repairMat){
			this.name=name;
			this.durabilityMul=durabilityMultiplyer;
			this.protection=protection;
			this.enchantValue=enchant;
			this.sound=equipSound;
			this.toughness=toughness;
			this.knockback=knockbackResist;
			this.repairMat=repairMat;
		}

		@Override
		public int getDurabilityForSlot(EquipmentSlot slot){return HEALTH[slot.getIndex()]*durabilityMul;}
		@Override
		public int getDefenseForSlot(EquipmentSlot slot){return this.protection[slot.getIndex()];}
		@Override
		public int getEnchantmentValue(){return this.enchantValue;}
		@Override
		public SoundEvent getEquipSound(){return this.sound;}
		@Override
		public Ingredient getRepairIngredient(){return this.repairMat.get();}
		@Override
		public String getName(){return ProjectT2.MODID+":"+this.name;}
		@Override
		public float getToughness(){return this.toughness;}
		@Override
		public float getKnockbackResistance(){return this.knockback;}
	}
}
