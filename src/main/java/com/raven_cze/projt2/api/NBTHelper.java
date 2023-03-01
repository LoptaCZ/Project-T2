package com.raven_cze.projt2.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTHelper {
	public static ItemStack readItemStack(CompoundTag cmp, String tag) {
		if (cmp != null && cmp.contains(tag)) {
			return ItemStack.of(cmp.getCompound(tag));
		}
		return ItemStack.EMPTY;
	}
  
	public static CompoundTag writeItemStack(CompoundTag cmp, String tag, ItemStack stack) {
		if (cmp != null){
			if (stack != null){
				CompoundTag stackNbt = new CompoundTag();
				stack.save(cmp);
				cmp.put(tag, cmp);
				return stackNbt;
			}if (cmp.contains(tag)){
				cmp.remove(tag);
			}
		} 
		return null;
	}
  
	public static CompoundTag getECTag(ItemStack stack){
		CompoundTag nbt = stack.getOrCreateTag();
		if (nbt == null || !nbt.contains("elementalcraft")){return null;}
		return nbt.getCompound("elementalcraft");
	}
  
	public static CompoundTag getOrCreateECTag(ItemStack stack){
		CompoundTag nbt = stack.getOrCreateTag();
		if (nbt == null) {
			nbt = new CompoundTag();
			stack.setTag(nbt);
		} 
		if (!nbt.contains("elementalcraft")) {
			nbt.put(null, nbt);
		}
		return nbt.getCompound("elementalcraft");
	}
}