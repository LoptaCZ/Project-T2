package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.api.IConnection;
import com.raven_cze.projt2.api.ITickableBlockEntity;
import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TileConduit extends BlockEntity implements IConnection,ITickableBlockEntity{
    public Direction facing;
    public float pureVis;
    public float taintedVis;
    public float maxVis;
    float fillAmount=4.0F;
    public float displayPure;
    public float displayTaint;
    public float displayPurePrev;
    public float displayTaintPrev;
    public int visSuction;
    public int taintSuction;

    public TileConduit(BlockPos pos,BlockState state){
        super(PT2Tiles.TILE_CONDUIT.get(),pos,state);
        this.visSuction=0;
        this.taintSuction=0;
        this.pureVis=0.0F;
        this.taintedVis=0.0F;
        this.maxVis=4.0F;
    }

    @Override
    public void tickServer(){
        if(Objects.requireNonNull(this.level).isClientSide())return;
        if(this.displayPurePrev!=this.displayPure || this.displayTaintPrev!=this.displayTaint){
            this.level.setBlockAndUpdate(this.worldPosition,this.getBlockState());
            this.displayPurePrev=this.displayPure;
            this.displayTaintPrev=this.displayTaint;
        }
        calculateSuction();
        if(getSuction(null)>0)equalizeWithNeighbours();
        this.displayPure=Math.max(this.displayPure,Utils.Math.clamp(this.pureVis,0.0F,this.maxVis));
        this.displayTaint=Math.max(this.displayTaint,Utils.Math.clamp(this.taintedVis,0.0F,this.maxVis));
    }

    @Override
    public void tickClient() {

    }

    protected void calculateSuction(){
        setSuction(0);
        for(Direction dir:Direction.values()){
            BlockEntity te=getConnectableTile(Objects.requireNonNull(this.level),dir);
            if(te instanceof IConnection ic){
                if(getVisSuction(null)<ic.getVisSuction(dir)-1)setVisSuction(ic.getVisSuction(dir)-1);
                if(getTaintSuction(null)<ic.getTaintSuction(dir)-1)setTaintSuction(ic.getTaintSuction(dir)-1);
            }
        }
    }
    protected void equalizeWithNeighbours(){
        for(Direction dir:Direction.values()){
            if(isConnectable(dir)){
                BlockEntity te=getConnectableTile(Objects.requireNonNull(this.level),dir);
                if(te!=null){
                    IConnection ent=(IConnection)te;
                    if(this.pureVis+this.taintedVis<this.maxVis){
                        float[]results;
                        float qq=Math.min( (ent.getPureVis()+ ent.getTaintedVis())/4.0F,this.fillAmount);
                        results=ent.subtractVis(Math.min(qq,this.maxVis-this.pureVis+this.taintedVis));
                        if(getVisSuction(null)>ent.getVisSuction(this.facing))this.pureVis+=results[0];
                        else ent.setPureVis(results[0]+ent.getPureVis());
                        if(getTaintSuction(null)>ent.getTaintSuction(this.facing))this.pureVis+=results[0];
                        else ent.setTaintedVis(results[0]+ent.getTaintedVis());
                    }
                }//TE NOT NULL
            }//IS_CONNECTABLE
        }//FOR
        this.pureVis=Utils.Math.clamp(this.pureVis,0.0F,this.maxVis);
        this.taintedVis=Utils.Math.clamp(this.taintedVis,0.0F,this.maxVis);
    }//EQUALIZE
    @Override
    public void load(@NotNull CompoundTag compound){
        super.load(compound);
        this.pureVis=compound.getFloat("pureVis");
        this.taintedVis=compound.getFloat("pureVis");
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putFloat("pureVis",this.pureVis);
        compound.putFloat("taintedVis",this.taintedVis);
    }
    @Override
    public boolean isConnectable(Direction direction){return true;}
    @Override
    public boolean isVisSource(){return false;}
    @Override
    public boolean isVisConduit(){return true;}
    @Override
    public float[] subtractVis(float amount){
        float pureAmount=amount/2.0F;
        float taintAmount=amount/2.0F;
        float[]result={0.0F,0.0F};

        if(amount<0.001F)return result;

        if(this.pureVis<pureAmount)pureAmount=this.pureVis;
        if(this.taintedVis<taintAmount)taintAmount=this.taintedVis;
        if(pureAmount<amount/2.0F && taintAmount==amount/2.0F){
            taintAmount=Math.min(amount-pureAmount,this.taintedVis);
        }else if(taintAmount<amount/2.0F && pureAmount==amount/2.0F){
            pureAmount=Math.min(amount-taintAmount,this.pureVis);
        }

        this.pureVis-=pureAmount;
        this.taintedVis-=taintAmount;
        result[0]=pureAmount;
        result[1]=taintAmount;
        return result;
    }
    @Override
    public float getPureVis(){return this.pureVis;}
    @Override
    public float getTaintedVis(){return this.taintedVis;}
    @Override
    public float getMaxVis(){return this.maxVis;}
    @Override
    public int getVisSuction(Direction dir){return this.visSuction;}
    @Override
    public int getTaintSuction(Direction dir){return this.taintSuction;}
    @Override
    public int getSuction(Direction dir){return Math.max(this.visSuction,this.taintSuction);}
    @Override
    public void setPureVis(float pureAmount){this.pureVis=pureAmount;}
    @Override
    public void setTaintedVis(float taintAmount){this.taintedVis=taintAmount;}
    @Override
    public void setVisSuction(int suctionAmount){this.visSuction=suctionAmount;}
    @Override
    public void setTaintSuction(int suctionAmount){this.taintSuction=suctionAmount;}
    @Override
    public void setSuction(int suctionAmount){this.taintSuction=this.visSuction=suctionAmount;}

    @Override
    public BlockEntity getConnectableTile(Level level,Direction direction){return IConnection.super.getConnectableTile(level, direction);}

}
