package com.raven_cze.projt2.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileVisUser extends BlockEntity implements IConnection{
    public int visSuction=0;
    public int taintSuction=0;
    public TileVisUser(BlockEntityType<?> pType,BlockPos pos,BlockState state){
        super(pType,pos,state);
        setSuction(0);
    }

    public boolean getExactPureVis(float amount){
        setVisSuction(50);
        for(Direction side:Direction.values()){
            if(isConnectable(side) && this.level!=null){
                IConnection ic=(IConnection)getConnectableTile(this.level,side);
                if(ic!=null && (ic.isVisConduit() || ic.isVisSource()) && ic.getPureVis()>=amount ){
                    ic.setPureVis(ic.getPureVis()-amount);
                    return true;
                }
            }
        }
        return false;
    }
    public float getAvailablePureVis(float amount){
        setVisSuction(50);
        float gatheredVis=0.0F;
        for(Direction side:Direction.values()){
            if(isConnectable(side) && this.level!=null){
                IConnection ic=(IConnection)getConnectableTile(this.level,side);
                if(ic!=null && (ic.isVisConduit()||ic.isVisSource()) ){
                    float sucked=Math.min(amount-gatheredVis,ic.getPureVis());
                    if(sucked<0.001F)sucked=0.0F;
                    gatheredVis+=sucked;
                    ic.setPureVis(ic.getPureVis()-sucked);
                }
            }
        }
        return Math.min(gatheredVis,amount);
    }
    public float getAvailableTaintedVis(float amount){
        setTaintSuction(50);
        float gatheredVis=0.0F;
        for(Direction side:Direction.values()){
            if(isConnectable(side) && this.level!=null){
                IConnection ic=(IConnection)getConnectableTile(this.level,side);
                if(ic!=null && (ic.isVisConduit()||ic.isVisSource()) ){
                    float sucked=Math.min(amount-gatheredVis,ic.getTaintedVis());
                    if(sucked<0.001F)sucked=0.0F;
                    gatheredVis+=sucked;
                    ic.setTaintedVis(ic.getTaintedVis()-sucked);
                }
            }
        }
        return Math.min(gatheredVis,amount);
    }

    @Override
    public boolean isConnectable(Direction direction) {return false;}

    @Override
    public boolean isVisSource(){return false;}

    @Override
    public boolean isVisConduit(){return false;}

    @Override
    public float[]subtractVis(float visAmount){return new float[0];}

    @Override
    public float getPureVis(){return 0;}

    @Override
    public float getTaintedVis(){return 0;}

    @Override
    public float getMaxVis(){return 0;}

    @Override
    public int getVisSuction(Direction dir){return this.visSuction;}

    @Override
    public int getTaintSuction(Direction dir){return this.taintSuction;}

    @Override
    public int getSuction(Direction dir){return Math.max(this.visSuction,this.taintSuction);}

    @Override
    public void setPureVis(float pureAmount){}

    @Override
    public void setTaintedVis(float taintAmount){}

    @Override
    public void setVisSuction(int suctionAmount){this.visSuction=suctionAmount;}

    @Override
    public void setTaintSuction(int suctionAmount){this.taintSuction=suctionAmount;}

    @Override
    public void setSuction(int suctionAmount){this.visSuction=suctionAmount;this.taintSuction=suctionAmount;}

    protected boolean gettingPower(){
        if(this.level!=null)
            for(Direction dir:Direction.values()){
                return(this.getBlockState().getSignal(this.level,this.worldPosition,dir)>0);
            }
        return false;
    }
}
