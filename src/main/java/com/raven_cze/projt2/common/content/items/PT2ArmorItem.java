package com.raven_cze.projt2.common.content.items;

import java.util.Objects;
import java.util.function.Supplier;

import com.raven_cze.projt2.ProjectT2;

import com.raven_cze.projt2.common.content.PT2Items;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class PT2ArmorItem extends ArmorItem{
	boolean loggedTexture=false;
	boolean isSpecial=false;
	boolean isArmor=false;
	public PT2ArmorItem(ArmorMaterial material,EquipmentSlot slot,Properties properties){
		super(material,slot,properties);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack,Entity entity,EquipmentSlot slot,String type){
		String RS="";
		switch(Objects.requireNonNull(this.getRegistryName()).getPath()){
			case"goggles","mask","bootsseven","bootsstomp","bootsstriding":isSpecial=true;
			case"helmetthaumium","platethaumium","legsthaumium","bootsthaumium","helmetvoid","platevoid","legsvoid","bootsvoid":isArmor=true;
		}
		
		if(isSpecial)RS=ProjectT2.MODID+":textures/models/armor/"+Objects.requireNonNull(stack.getItem().getRegistryName()).getPath()+".png";

		if(isArmor){
			if (stack.getItem()==PT2Items.THAUMIUM_HELMET.get()||stack.getItem()==PT2Items.THAUMIUM_CHEST.get()||stack.getItem()==PT2Items.THAUMIUM_BOOTS.get())
				RS=ProjectT2.MODID + ":textures/models/armor/thaumium_1.png";
			if (stack.getItem()==PT2Items.THAUMIUM_LEGS.get())
				RS=ProjectT2.MODID + ":textures/models/armor/thaumium_2.png";
			if (stack.getItem()==PT2Items.VOID_HELMET.get()||stack.getItem()==PT2Items.VOID_PLATE.get()||stack.getItem()==PT2Items.VOID_BOOTS.get())
				RS=ProjectT2.MODID + ":textures/models/armor/void_1.png";
			if (stack.getItem()==PT2Items.VOID_LEGS.get())
				RS=ProjectT2.MODID + ":textures/models/armor/void_2.png";

		}
		else RS=super.getArmorTexture(stack,entity,slot,type);

		if (!isSpecial&&!isArmor){RS = super.getArmorTexture(stack, entity, slot, type);}
		if(!loggedTexture){
			loggedTexture=true;
			ProjectT2.LOGGER.info(RS);
		}
		return RS;
		//return super.getArmorTexture(stack,entity,slot,type);
	}

	public boolean isInSlot(EquipmentSlot slot){
		return new ItemStack(this).getEquipmentSlot()==slot;
	}

	public enum PT2ArmorMats implements ArmorMaterial{
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

		PT2ArmorMats(String name,int durabilityMultiplier,int[]protection,int enchant,SoundEvent equipSound,float toughness,float knockbackResist,Supplier<Ingredient>repairMat){
			this.name=name;
			this.durabilityMul=durabilityMultiplier;
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
		public @NotNull SoundEvent getEquipSound(){return this.sound;}
		@Override
		public @NotNull Ingredient getRepairIngredient(){return this.repairMat.get();}
		@Override
		public @NotNull String getName(){return ProjectT2.MODID+":"+this.name;}
		@Override
		public float getToughness(){return this.toughness;}
		@Override
		public float getKnockbackResistance(){return this.knockback;}
	}
}
