package com.raven_cze.projt2.common.recipes;

import java.util.Collections;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.raven_cze.projt2.ProjectT2;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class RecipesInfuser implements Recipe<SimpleContainer>{
	public static Map<ResourceLocation,RecipesInfuser>recipeList=Collections.emptyMap();
	private final ResourceLocation id;
	private final ItemStack output;
	
	private final NonNullList<Ingredient>recipeItems;

	private RecipesInfuser(ResourceLocation id,ItemStack out,NonNullList<Ingredient>input){
		this.id=id;
		this.output=out;
		this.recipeItems=input;
	}

	@Override
	public boolean matches(SimpleContainer container,Level level){return recipeItems.get(0).test(container.getItem(1));}
	
	public NonNullList<Ingredient>getIngredients(){return recipeItems;}
	
	@Override
	public ItemStack assemble(SimpleContainer container){return output;}
	
	@Override
	public boolean canCraftInDimensions(int width, int height){return true;}

	@Override
	public ItemStack getResultItem(){return this.output.copy();}

	@Override
	public ResourceLocation getId(){return id;}

	@Override
	public RecipeSerializer<?> getSerializer(){return Serializer.INSTANCE;}

	@Override
	public RecipeType<?> getType(){return Type.INSTANCE;}
	
	public static class Type implements RecipeType<RecipesInfuser>{
		private Type(){}
		public static final Type INSTANCE=new Type();
		public static final String ID="infusing";
	}
	public static class Serializer implements RecipeSerializer<RecipesInfuser>{
		public static final Serializer INSTANCE=new Serializer();
		public static final ResourceLocation ID=new ResourceLocation(ProjectT2.MODID,"infusing");
		@Override
		public RecipeSerializer<?>setRegistryName(ResourceLocation name){return INSTANCE;}
		@Override
		public ResourceLocation getRegistryName(){return ID;}
		@Override
		public Class<RecipeSerializer<?>> getRegistryType(){return Serializer.castClass(RecipeSerializer.class);}
		@Override
		public RecipesInfuser fromJson(ResourceLocation id,JsonObject json){
			ItemStack o=ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"output"));
			JsonArray ingredients=GsonHelper.getAsJsonArray(json,"ingredients");
			NonNullList<Ingredient>i=NonNullList.withSize(0,Ingredient.EMPTY);
			for(int x=0;x<i.size();x++){
				i.set(x,Ingredient.fromJson(ingredients.get(x)));
			}
			return new RecipesInfuser(id,o,i);
		}
		@Override
		public RecipesInfuser fromNetwork(ResourceLocation id,FriendlyByteBuf buf){
			NonNullList<Ingredient>inputs=NonNullList.withSize(buf.readInt(),Ingredient.EMPTY);
			for(int i=0;i<inputs.size();i++){inputs.set(i,Ingredient.fromNetwork(buf));}
			ItemStack output=buf.readItem();
			return new RecipesInfuser(id,output,inputs);
		}
		@Override
		public void toNetwork(FriendlyByteBuf buf,RecipesInfuser recipe){
			buf.writeInt(recipe.getIngredients().size());
			for(Ingredient in:recipe.getIngredients()){
				in.toNetwork(buf);
			}
			buf.writeItemStack(recipe.getResultItem(),false);
		}
		@SuppressWarnings("unchecked")
		private static <G>Class<G>castClass(Class<?>cl){return(Class<G>)cl;}
	}
}
