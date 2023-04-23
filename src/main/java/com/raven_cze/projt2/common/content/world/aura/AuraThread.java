package com.raven_cze.projt2.common.content.world.aura;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.event.ServerEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AuraThread implements Runnable{
    public ResourceKey<Level> dim;
    private boolean stop=false;
    Random rand=new Random(System.currentTimeMillis());
    private float phaseVis;
    private float phaseTaint;
    private float phaseMax;
    private long lastWorldTime;
    private final float[]phaseTable;
    private final float[]maxTable;

    public AuraThread(ResourceKey<Level>dim){
        this.phaseVis=0.0F;
        this.phaseTaint=0.0F;
        this.phaseMax=0.0F;
        this.lastWorldTime=0L;
        this.phaseTable=new float[]{0.25F,0.15F,0.1F,0.05F,0.0F,0.05F,0.1F,0.15F};
        this.maxTable=new float[]{0.15F,0.05F,0.0F,-0.05F,-0.15F,-0.05F,0.0F,0.05F};
        this.dim=dim;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        ProjectT2.LOGGER.info("Starting aura thread for dimension "+this.dim);
        while(!this.stop){
            if(AuraHandler.auras.isEmpty()){
                ProjectT2.LOGGER.warn("No auras found!");
                break;
            }
            long startTime=System.currentTimeMillis();
            AuraWorld auraWorld=AuraHandler.getAuraWorld(this.dim);
            if(auraWorld!=null){
                ServerLevel sLevel=null;
                if(Minecraft.getInstance().getSingleplayerServer()!=null)
                    sLevel=Minecraft.getInstance().getSingleplayerServer().getLevel(this.dim);
                if(sLevel != null && this.lastWorldTime != sLevel.getGameTime()){
                    this.lastWorldTime = sLevel.getGameTime();
                    this.phaseVis = this.phaseTable[ sLevel.random.nextInt(sLevel.getRandom().nextInt()) ];
                    this.phaseMax = 1.0F + this.maxTable[ sLevel.random.nextInt(sLevel.getRandom().nextInt()) ];
                    this.phaseTaint = 0.25F - this.phaseVis;
                    for (AuraChunk aura : AuraWorld.auraChunks.values())
                        processAuraChunk(auraWorld, aura);
                }
            }else{
                stop();
            }
            long executionTime=System.currentTimeMillis()-startTime;
            try{
                if(executionTime>1000L)
                    ProjectT2.LOGGER.warn("Auras taking {} ms longer than normal in {}",(executionTime-1000L),this.dim);
                Thread.sleep(Math.max(1L,1000L-executionTime));
            }catch(InterruptedException interruptedException){interruptedException.printStackTrace();}
        }
        ProjectT2.LOGGER.info("Stopping aura thread for dimension {}",this.dim);
        try{
            ServerEvents.auraThreads.remove(this.dim);
        }catch(Exception e){e.printStackTrace();}
    }
    public void processAuraChunk(AuraWorld auraWorld,AuraChunk auraChunk){
        List<Direction>directions= Arrays.asList(Direction.EAST,Direction.WEST,Direction.NORTH,Direction.SOUTH);
        Collections.shuffle(directions,this.rand);
        int x=auraChunk.loc.x;
        int z=auraChunk.loc.z;
        float base=auraChunk.getBase()*this.phaseMax;
        boolean dirty=false;
        float currentVis=auraChunk.getVis();
        float currentTaint=auraChunk.getTaint();
        AuraChunk neighbourVisChunk=null;
        AuraChunk neighbourTaintChunk=null;
        float lowestVis=Float.MAX_VALUE;
        float lowestTaint=Float.MAX_VALUE;
        for(Direction a:directions){
            AuraChunk n=AuraWorld.getAuraChunkAt(new BlockPos(x+a.get3DDataValue(),0,z+a.get3DDataValue()));
            if(n!=null){
                if( (neighbourVisChunk==null)||lowestVis>n.getVis() && n.getVis()+n.getTaint()<n.getBase()*this.phaseMax ){
                    neighbourVisChunk=n;
                    lowestVis=n.getVis();
                }
                if( (neighbourTaintChunk==null)||lowestTaint>n.getTaint() ){
                    neighbourTaintChunk=n;
                    lowestTaint=n.getVis();
                }
            }
        }
        if(neighbourVisChunk!=null && lowestVis<currentVis && (lowestVis/currentVis)<0.75D ){
            float inc=Math.min(currentVis-lowestVis,1.0F);
            currentVis-=inc;
            neighbourVisChunk.setVis((short)(lowestVis+inc));
            dirty=true;
            markChunkAsDirty(neighbourVisChunk,auraWorld.dim);
        }
        if(neighbourTaintChunk!=null && currentTaint>Math.max(5.0F,auraChunk.getBase()/10.0F)&&lowestTaint<currentTaint/1.75D ){
            float inc=Math.min(currentTaint-lowestTaint,1.0F);
            currentTaint-=inc;
            neighbourTaintChunk.setTaint((short)(lowestTaint+inc));
            dirty=true;
            markChunkAsDirty(neighbourTaintChunk,auraWorld.dim);
        }
        if(currentVis+currentTaint<base){
            float inc=Math.min(base-currentVis+currentTaint,this.phaseVis);
            currentVis+=inc;
            dirty=true;
        }else if(currentVis>base*1.25D&&this.rand.nextFloat()<0.1D){
            currentTaint+=this.phaseTaint;
            currentVis-=this.phaseVis;
            dirty=true;
        }else if(currentVis<=base*0.1D&&currentVis>=currentTaint&&this.rand.nextFloat()<0.1D){
            currentTaint+=this.phaseTaint;
            dirty=true;
        }
        if(dirty){
            auraChunk.setVis((short) currentVis);
            auraChunk.setTaint((short) currentTaint);
            markChunkAsDirty(auraChunk,auraWorld.dim);
        }
    }
    private void markChunkAsDirty(AuraChunk chunk,ResourceKey<Level>dim){
        if(chunk.isModified())return;
        ChunkPos pos=new ChunkPos(chunk.loc.x,chunk.loc.z);
        if( !AuraHandler.dirtyChunks.containsKey(dim) ){
            AuraHandler.dirtyChunks.put(dim, new CopyOnWriteArrayList<>());
            CopyOnWriteArrayList<ChunkPos>dc=AuraHandler.dirtyChunks.get(dim);
            if(!dc.contains(pos))dc.add(pos);
        }
    }
    public void stop(){this.stop=true;}
}
