package com.raven_cze.projt2.common.recipes;

import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused"})
public class RecipesCrucible {
	public static RecipesCrucible smelting(){return smeltingBase;}

	private RecipesCrucible(){
		int depth = 0;
		this.smeltingList= new HashMap<>();
		this.cacheList= new HashMap<>();
	}
	
	public void addSmelting(ItemStack item,float vis){this.smeltingList.put(item,vis);}
	
	public float getSmeltingResult(ItemStack src,boolean first,boolean basicOnly){
		if(src==null)return 0.0F;
		
		if(this.smeltingList.get(src)!=null)
			return (this.smeltingList.get(src));
		float rc = 0.5F;
		//if(rc>0.0F)return rc;
		if(basicOnly)return 0.0F;
		if(this.cacheList.get(src)!=null)
			return(this.cacheList.get(src));
		if(first)rc=recipeCost(src);
		if(first && rc>0.0F && this.cacheList.get(src)!=null)
			this.cacheList.put(src, rc);
		return rc;
	}
	
	private float recipeCost(ItemStack src){
		/*
		if(RecipesInfuser.infusing().getInfusingCost(src)>0){
			totalCost+=RecipesInfuser.infusing().getInfusingCost(src);
			ItemStack[] comps=RecipesInfuser.infusing().getInfusingComponents(src);
			for(int q=0;q<comps.length;q++) totalCost+=getSmeltingResult(comps[q],true,false);
		}else if(RecipesInfuser.infusing().getInfusingCost(src,false)>0){
			totalCost+=RecipesInfuser.infusing().getInfusingCost(src,true);
			ItemStack[] comps=RecipesInfuser.infusing().getInfusingComponents(src,true);
			for(int q=0;q<comps.length;q++) totalCost+=getSmeltingResult(comps[q],true,false);
		}else{*/
			//TODO recipeCost(ItemStack src) Like WTF?!
			//	Reversing Shaped/Shapeless Recipes??
			/*
			for(int q=0;q<PT2Api.recipeList.size();q++){
				if(PT2Api.recipeList.get(q) instanceof ShapedRecipe){
					ShapedRecipe shaped=PT2Api.recipeList.get(q);
					
				}
			}
			
		}*/
		return 0.0F;
	}
	
	public Map<ItemStack,Float> getSmeltingList(){return this.smeltingList;}

	private static final RecipesCrucible smeltingBase= new RecipesCrucible();
	private final Map<ItemStack,Float> smeltingList;
	private final Map<ItemStack,Float> cacheList;
}
