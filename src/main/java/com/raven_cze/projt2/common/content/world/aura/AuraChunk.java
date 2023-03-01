package com.raven_cze.projt2.common.content.world.aura;

import java.lang.ref.WeakReference;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

public class AuraChunk{
	ChunkPos loc;
	short base;
	float vis;
	float taint;
	WeakReference<LevelChunk>chunkRef;

	public AuraChunk(ChunkPos pos,short base,float vis,float taint){
		if(pos!=null){
			loc=pos;
		}
		this.base=base;
		this.vis=vis;
		this.taint=taint;

	}

	public AuraChunk(LevelChunk chunk,short base,float vis,float taint){
		if(chunk!=null){
			loc=chunk.getPos();
			chunkRef=new WeakReference<>(chunk);
		}
		this.base=base;
		this.vis=vis;
		this.taint=taint;
	}
	
	public boolean isModified(){
		if(this.chunkRef!=null && this.chunkRef.get()!=null)
			return ((LevelChunk)this.chunkRef.get()).isUnsaved();
		return false;
	}
	public short getBase(){return this.base;}
	public float getVis(){return this.vis;}
	public float getTaint(){return this.taint;}
	public ChunkPos getLoc(){return this.loc;}
	
	public void setBase(short base){this.base=base;}
	public void setVis(float vis){this.vis=vis;}
	public void setTaint(float taint){this.taint=taint;}
	public void setLoc(ChunkPos pos){this.loc=pos;}

}
