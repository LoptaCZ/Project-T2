package com.raven_cze.projt2.api;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraftforge.client.model.data.ModelProperty;

@SuppressWarnings({"rawtypes","unchecked"})
public class PT2Properties {
	
	
	public static class Model{
		
		public static final ModelProperty<List<String>> CRYSTAL=new ModelProperty();
		public static final ModelProperty<BlockPos> SUBMODEL_OFFSET=new ModelProperty();
	}
}
