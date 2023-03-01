package com.raven_cze.projt2.common.content.items;

import java.util.function.Supplier;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class PT2Tool extends PT2Item{
	public PT2Tool(Properties properties){
		super(properties);
	}
	public static class PT2ToolSword extends SwordItem{
		public PT2ToolSword(Tier tier,int maxDamage,float attackDamage,Properties properties){
			super(tier, maxDamage, attackDamage, properties);
		}
		
	}
	public static class PT2ToolPickaxe extends PickaxeItem{
		public PT2ToolPickaxe(Tier tier,int maxDamage,float attackDamageBaseline,Properties properties){
			super(tier,maxDamage,attackDamageBaseline,properties);
		}
	}
	public static class PT2ToolShovel extends ShovelItem{
		public PT2ToolShovel(Tier tier,int maxDamage,float attackDamageBaseline,Properties properties){
			super(tier,maxDamage,attackDamageBaseline,properties);
		}
	}
	public static class PT2ToolAxe extends AxeItem{
		public PT2ToolAxe(Tier tier,int maxDamage,float attackDamageBaseline,Properties properties){
			super(tier,maxDamage,attackDamageBaseline,properties);
		}
	}
	public static class PT2ToolHoe extends HoeItem{
		public PT2ToolHoe(Tier tier,int maxDamage,float attackDamageBaseline,Properties properties){
			super(tier,maxDamage,attackDamageBaseline,properties);
		}
	}
	public static class PT2ToolBow extends BowItem{
		public PT2ToolBow(Tier tier,int maxDamage,float attackDamageBaseline,Properties properties){
			super(properties);
		}
	}
	
	public enum PT2Tiers implements Tier{
		ELEMENTAL(0,8192,8.0F,3.0F,22,()->{return Ingredient.of(Items.LEATHER);}),//TODO Repair Materials
		THAUMIUM(0,8192,8.0F,3.0F,22,()->{return Ingredient.of(Items.LEATHER);}),
		VOID(0,8192,8.0F,3.0F,22,()->{return Ingredient.of(Items.LEATHER);});
		
		private final int level;
		private final int uses;
		private final float speed;
		private final float damage;
		private final int enchantmentValue;
		private final Supplier<Ingredient> repairMaterial;

		PT2Tiers(int level,int durability,float speed,float damage,int enchantValue,Supplier<Ingredient> repairMaterial) {
			this.level=level;
			this.uses=durability;
			this.speed=speed;
			this.damage=damage;
			this.enchantmentValue=enchantValue;
			this.repairMaterial=repairMaterial;
		}
		@Override
		public int getUses(){return this.uses;}
		@Override
		public float getSpeed(){return this.speed;}
		@Override
		public float getAttackDamageBonus(){return this.damage;}
		@Override
		public int getLevel(){return this.level;}
		@Override
		public int getEnchantmentValue(){return this.enchantmentValue;}
		@Override
		public @NotNull Ingredient getRepairIngredient(){return this.repairMaterial.get();}
		
	}
}
