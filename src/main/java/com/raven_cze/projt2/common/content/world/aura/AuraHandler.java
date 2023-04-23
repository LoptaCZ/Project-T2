package com.raven_cze.projt2.common.content.world.aura;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.util.Utils;
import com.raven_cze.projt2.common.content.world.biome.BiomeHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AuraHandler {
	public static final int AURA_CEILING = 500;
	  
	  static ConcurrentHashMap<String,AuraWorld>auras=new ConcurrentHashMap<>();
	  
	  public static ConcurrentHashMap<ResourceKey<Level>,CopyOnWriteArrayList<ChunkPos>>dirtyChunks=new ConcurrentHashMap<>();
	  
	  public static AuraWorld getAuraWorld(ResourceKey<Level>dim){
	    return auras.get(dim.toString());
	  }
	  
	  public static AuraChunk getAuraChunk(ResourceKey<Level> dim, int x, int y){
	    if (auras.containsKey(dim.toString()))
	      return AuraWorld.getAuraChunkAt(new BlockPos(x,0,y));
	    addAuraWorld(dim);
	    if (auras.containsKey(dim.toString())) {
			auras.get(dim.toString());
			return AuraWorld.getAuraChunkAt(new BlockPos(x,0,y));
		}
	    return null;
	  }
	  
	  public static void addAuraWorld(ResourceKey<Level>dim){
	    if (!auras.containsKey(dim.toString())){
	      auras.put(dim.toString(),new AuraWorld(dim));
	      ProjectT2.LOGGER.info("Creating aura cache for world " + dim);
	    } 
	  }
	  
	  public static void removeAuraWorld(ResourceKey<Level>dim) {
	    auras.remove(dim.toString());
	    ProjectT2.LOGGER.info("Removing aura cache for world "+dim);
	  }
	  
	  public static void addAuraChunk(ResourceKey<Level>dim,LevelChunk chunk,short base,float vis,float flux){
	    AuraWorld aw=auras.get(dim.toString());
		ProjectT2.LOGGER.debug("Aura HashCode: "+dim.toString());
	    if (aw == null)
	      aw = new AuraWorld(dim); 
	    aw.getAuraChunks().put(new ChunkPos(chunk.getPos().x,chunk.getPos().z).getWorldPosition(),new AuraChunk(chunk, base, vis, flux));
	    auras.put(dim.toString(),aw);
	  }
	  
	  public static void removeAuraChunk(ResourceKey<Level>dim,int x,int y) {
	    AuraWorld aw = auras.get(dim.toString());
	    if (aw != null)
	      aw.getAuraChunks().remove(new BlockPos(x,0,y)); 
	  }
	  
	  public static float getTotalAura(Level world, BlockPos pos) {
	    AuraChunk ac = getAuraChunk(world.dimension(), pos.getX() >> 4, pos.getZ() >> 4);
	    return (ac != null) ? (ac.getVis() + ac.getTaint()) : 0.0F;
	  }
	  
	  public static float getTaintSaturation(Level world, BlockPos pos) {
	    AuraChunk ac = getAuraChunk(world.dimension(), pos.getX() >> 4, pos.getZ() >> 4);
	    return (ac != null) ? (ac.getTaint() / ac.getBase()) : 0.0F;
	  }
	  
	  public static float getVis(Level world, BlockPos pos) {
	    AuraChunk ac = getAuraChunk(world.dimension(), pos.getX() >> 4, pos.getZ() >> 4);
	    return (ac != null) ? ac.getVis() : 0.0F;
	  }
	  
	  public static float getTaint(Level world, BlockPos pos) {
	    AuraChunk ac = getAuraChunk(world.dimension(), pos.getX() >> 4, pos.getZ() >> 4);
	    return (ac != null) ? ac.getTaint() : 0.0F;
	  }
	  
	  public static int getAuraBase(Level world, BlockPos pos) {
	    AuraChunk ac = getAuraChunk(world.dimension(), pos.getX() >> 4, pos.getZ() >> 4);
	    return (ac != null) ? ac.getBase() : 0;
	  }
	  
	  public static boolean shouldPreserveAura(Level world,Player player, BlockPos pos) {
	    return ((player == null ) && (
	      getVis(world, pos) / getAuraBase(world, pos)) < 0.1D);
	  }
	  
	  public static void addVis(Level world, BlockPos pos, float amount) {
	    if (amount < 0.0F)
	      return; 
	    try {
	      AuraChunk ac = getAuraChunk(world.dimension(), pos.getX() >> 4, pos.getZ() >> 4);
	      modifyVisInChunk(ac, amount, true);
	    } catch (Exception ignored) {}
	  }
	  
	  public static void addFlux(Level world, BlockPos pos, float amount) {
	    if (amount < 0.0F)
	      return; 
	    try {
	      AuraChunk ac = getAuraChunk(world.dimension(), pos.getX() >> 4, pos.getZ() >> 4);
	      modifyFluxInChunk(ac, amount, true);
	    } catch (Exception ignored) {}
	  }
	  
	  public static float drainVis(Level world,BlockPos pos,float amount,boolean simulate){
	    boolean didit = false;
	    try {
	      AuraChunk ac = getAuraChunk(world.dimension(), pos.getX() >> 4, pos.getZ() >> 4);
			if (ac != null && amount > ac.getVis()) amount = ac.getVis();
			didit = modifyVisInChunk(ac, -amount, !simulate);
	    } catch (Exception ignored) {}
	    return didit ? amount : 0.0F;
	  }
	  
	  public static float drainTaint(Level world, BlockPos pos, float amount, boolean simulate){
	    boolean didit = false;
	    try {
	      AuraChunk ac = getAuraChunk(world.dimension(), pos.getX() >> 4, pos.getZ() >> 4);
			if (ac != null && amount > ac.getTaint()) amount = ac.getTaint();
			didit = modifyFluxInChunk(ac, -amount, !simulate);
	    } catch (Exception ignored) {}
	    return didit ? amount : 0.0F;
	  }
	  
	  public static boolean modifyVisInChunk(AuraChunk ac,float amount,boolean doit){
	    if (ac != null) {
	      if (doit)
	        ac.setVis((short)Math.max(0.0F,ac.getVis() + amount)); 
	      return true;
	    } 
	    return false;
	  }
	  
	  private static boolean modifyFluxInChunk(AuraChunk ac,float amount,boolean doit){
	    if (ac != null) {
	      if (doit)
	        ac.setTaint((short)Math.max(0.0F,ac.getTaint()+amount));
	      return true;
	    } 
	    return false;
	  }
	  
	public static void generateAura(LevelChunk chunk,Random rand){ProjectT2.LOGGER.debug("generateAura({},{}) method called.",chunk,rand);
		ProjectT2.LOGGER.debug("ChunkPos: {}, Random: {}",chunk.getPos(),rand);
		Biome bgb=null;
		ProjectT2.LOGGER.debug("BGB value: {}, before assignment",bgb);
		//bgb = chunk.getLevel().getBiome(new BlockPos(chunk.getPos().x * 16 + 8, 50, chunk.getPos().z * 16 + 8));//THIS JUNK WON'T DO ANYTHING

		//ProjectT2.LOGGER.debug("BGB value: {}, after assignment",bgb);

		if(bgb!=null){
			//ProjectT2.LOGGER.debug("Biome: {}", bgb);
			if(BiomeHandler.getBiomeBlacklist(bgb))return;
			float life = BiomeHandler.getBiomeAuraModifier(bgb);
			ProjectT2.LOGGER.debug("Life Value: {}",life);
			for(int a=0;a<4;a++){
				Direction dir = Direction.from3DDataValue(a);
				Biome bgb2 =chunk.getLevel().getBiome(new BlockPos((chunk.getPos().x + dir
						  .getStepX()) * 16 + 8, 50, (chunk.getPos().z + dir
						  .getStepZ()) * 16 + 8));
				life += BiomeHandler.getBiomeAuraModifier(bgb2);
			}
			life/=5.0F;
			float noise = (float)(1.0D+rand.nextGaussian()*0.10000000149011612D);
			short base = (short)(int)(life*500.0F*noise);
			base = Utils.Math.clamp(base,(short)0,(short)500);
			ProjectT2.LOGGER.debug("Adding Aura Chunk " + chunk);
			addAuraChunk((chunk.getLevel()).dimension(), chunk, base, base, 0.0F);
		}else{
			ProjectT2.LOGGER.debug("Biome cannot be loaded! ... Somehow");
			AuraThread at=new AuraThread(Objects.requireNonNull(chunk.getWorldForge()).dimension());
			at.stop();
		}
	  }
}
