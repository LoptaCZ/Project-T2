package com.raven_cze.projt2.common.util;

import com.raven_cze.projt2.common.config.ClientCFG;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@SuppressWarnings({"unused"})
public class Utils {
	static Object changedVar=null;
	
	public static int idk(int pInt1,int pInt2,int pInt3){
		if(pInt1<pInt2)return pInt2;
		return java.lang.Math.min(pInt1, pInt3);
	}

	public static boolean isVisibleTo(float fov,Entity ent,BlockPos pos){
		double dist=ent.distanceToSqr(pos.getX(),pos.getY(),pos.getZ());
		if(dist<2.0D)return true;
		Minecraft mc= Minecraft.getInstance();
		double vT=(fov+mc.options.fov/2.0F);
		int j=64<<3-mc.options.renderDistance;
		if(j>400)j=400;
		double rD=ClientCFG.lowFX.get()?(double)(j/2):j;
		float f1=0;//gk.b(-ent.u*0.01745329F-3.141593F)
		float f3=0;
		float f5=0;
		float f7=0;
		double lx=f3*f5;
		double ly=f7;
		double lz=f1*f5;
		double dx=pos.getX()+0.5D-ent.getX();
		double dy=pos.getY()+0.5D-ent.getY()-0.0F;
		double dz=pos.getZ()+0.5D-ent.getZ();
		double len=Mth.sqrt((float)(dx*dx+dy*dy+dz*dz));
		double dot=dx/len*lx+dy/len*ly+dz/len*lz;
		double angle=Mth.cos((float)dot);
		//return ((angle<vT && /*mc.options.*/0==0 && dist<rD || (/*mc.options.*/0>0 && dist <rD) ));
		return ((angle < vT && dist < rD));
	}

	public static boolean changed(Object o){return o == changedVar;}

	public ListTag getEnchantments(ItemStack item){
		if(item.hasTag()||item.getTag()==null){
			CompoundTag NBT=item.getTag();
			if(NBT!=null&&NBT.contains("Enchantments")){
				ListTag list=NBT.getList("Enchantments",Tag.TAG_LIST);
				if(list.isEmpty())return new ListTag();

				return list;
			}
		}
		return new ListTag();
	}
	public void getEnchantment(ItemStack item,Enchantment enchant){
		ListTag enchs = getEnchantments(item);
	}
	public boolean hasEnchantment(ItemStack item,Enchantment enchant){
		ListTag enchs = getEnchantments(item);
		if (enchs.isEmpty()) return false;
		for (Tag ench : enchs) {
			if (ench.getType() instanceof CompoundTag e) {
				String str = e.getString("id");
				return enchant.getRegistryName().toString().equals(str);
			}
		}
		return false;
	}

	public static class Math{
		public static int clamp(int input,int min,int max){return java.lang.Math.min(java.lang.Math.max(input,min),max);}
		public static float clamp(float input,float min,float max){return java.lang.Math.min(java.lang.Math.max(input,min),max);}
		public static double clamp(double input,double min,double max){return java.lang.Math.min(java.lang.Math.max(input,min),max);}
		public static long clamp(long input,long min,long max){return java.lang.Math.min(java.lang.Math.max(input,min),max);}
		public static short clamp(short input,short min,short max){return(short)java.lang.Math.min(java.lang.Math.max(input,min),max);}

		public static int lerp(int a,int b,int amt){
			amt=clamp(amt,0,1);
			return a*amt+b*(1-amt);
		}
		public static float lerp(float a,float b,float amt){
			amt=clamp(amt,0.0F,1.0F);
			return a*amt+b*(1-amt);
		}
		public static double lerp(double a,double b,double amt){
			amt=clamp(amt,0.0D,1.0D);
			return a*amt+b*(1-amt);
		}
		public static long lerp(long a,long b,long amt){
			amt=clamp(amt,0,1);
			return a*amt+b*(1-amt);
		}
	}//	Math
	
	@OnlyIn(Dist.CLIENT)
	public static class FX{

	}//	FX
	public static class Voxel{
		public static VoxelShape combine(VoxelShape...shapes){
			if(shapes.length==0)return Shapes.empty();
			VoxelShape combined=shapes[0];
			for(int i=1;i<shapes.length;i++){combined=Shapes.or(combined,shapes[i]);}
			return combined;
		}
	}//	Voxel
}//	Utils
