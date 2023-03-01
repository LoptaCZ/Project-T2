package com.raven_cze.projt2.compat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.recipes.RecipesInfuser;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEICompat implements IModPlugin{
	@Override
	public @NotNull ResourceLocation getPluginUid(){return new ResourceLocation(ProjectT2.MODID,"jei_plugin");}
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration){
		registration.addRecipeCategories(new InfuserRecipeCat(registration.getJeiHelpers().getGuiHelper()));
	}
	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration){
		RecipeManager manager= null;
		if (Minecraft.getInstance().level != null) {
			manager = Objects.requireNonNull(Minecraft.getInstance().level.getRecipeManager());
		}
		//Thaumic Infuser
		if (manager != null) {
			List<RecipesInfuser>recipes=manager.getAllRecipesFor(RecipesInfuser.Type.INSTANCE);
		}
		registration.addRecipes(new ArrayList<>(RecipesInfuser.recipeList.values()),InfuserRecipeCat.UID);
	}
	
	public static class InfuserRecipeCat implements IRecipeCategory<RecipesInfuser>{
		public final static ResourceLocation UID=new ResourceLocation(ProjectT2.MODID,"infusing");
		public final static ResourceLocation TEXTURE=new ResourceLocation(ProjectT2.MODID,"textures/gui/guiinfuser.png");
		private final IDrawable background;
		private final IDrawable icon;
		
		public InfuserRecipeCat(IGuiHelper helper){
			this.background=helper.createDrawable(TEXTURE,0,0,176,85);
			this.icon=helper.createDrawableIngredient(VanillaTypes.ITEM,new ItemStack(Items.ENCHANTED_BOOK));
		}
		@Override
		public @NotNull ResourceLocation getUid(){return UID;}
		@Override
		public @NotNull Class<?extends RecipesInfuser>getRecipeClass(){return RecipesInfuser.class;}
		@Override
		public @NotNull Component getTitle(){return new TextComponent("Thaumic Infusing");}
		@Override
		public @NotNull IDrawable getBackground(){return this.background;}
		@Override
		public @NotNull IDrawable getIcon(){return this.icon;}
		
		@Override
		public void setRecipe(@Nonnull IRecipeLayoutBuilder builder,@Nonnull RecipesInfuser recipe,@Nonnull IFocusGroup ingredients){
			builder.addSlot(RecipeIngredientRole.INPUT,0,0  ).addIngredients(recipe.getIngredients().get(0));
			builder.addSlot(RecipeIngredientRole.INPUT,25,0 ).addIngredients(Ingredient.of((Items.MUTTON).getDefaultInstance()));
			builder.addSlot(RecipeIngredientRole.INPUT,50,0 ).addIngredients(Ingredient.of((Items.MUTTON).getDefaultInstance()));
			builder.addSlot(RecipeIngredientRole.INPUT,75,0 ).addIngredients(Ingredient.of((Items.MUTTON).getDefaultInstance()));
			builder.addSlot(RecipeIngredientRole.INPUT,100,0).addIngredients(Ingredient.of((Items.MUTTON).getDefaultInstance()));
			builder.addSlot(RecipeIngredientRole.INPUT,125,0).addIngredients(Ingredient.of((Items.MUTTON).getDefaultInstance()));
			
			builder.addSlot(RecipeIngredientRole.OUTPUT,50,50).addItemStack(recipe.getResultItem());
			
			builder.addSlot(RecipeIngredientRole.CATALYST,50,50).addItemStack(recipe.getResultItem());
		}
	}
}
