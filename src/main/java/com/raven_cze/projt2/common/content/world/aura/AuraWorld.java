package com.raven_cze.projt2.common.content.world.aura;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.concurrent.ConcurrentHashMap;

public class AuraWorld{
	ResourceKey<Level>dim;
	static ConcurrentHashMap<BlockPos,AuraChunk>auraChunks=new ConcurrentHashMap<>();
	public AuraWorld(ResourceKey<Level> dim){
		this.dim=dim;
	}
	public ConcurrentHashMap<BlockPos,AuraChunk>getAuraChunks(){return auraChunks;}
	public void setAuraChunks(ConcurrentHashMap<BlockPos,AuraChunk>auraChunks){AuraWorld.auraChunks=auraChunks;}
	public static AuraChunk getAuraChunkAt(BlockPos pos){
		return auraChunks.get(pos);
	}
}
