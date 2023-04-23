package com.raven_cze.projt2.common.content.capability;

import com.raven_cze.projt2.api.IConnection;
import net.minecraft.core.Direction;

public class CapabilityConnectable implements IConnection{
    float pureVis;
    float taintVis;
    float maxVis;
    int visSuction;
    int taintSuction;
    @Override
    public boolean isConnectable(Direction direction) {return false;}

    @Override
    public boolean isVisSource(){return false;}

    @Override
    public boolean isVisConduit(){return false;}

    @Override
    public float[]subtractVis(float visAmount){return new float[0];}

    @Override
    public float getPureVis(){return this.pureVis;}

    @Override
    public float getTaintedVis(){return this.taintVis;}

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
    public void setTaintedVis(float taintAmount){this.taintVis=taintAmount;}

    @Override
    public void setVisSuction(int suctionAmount){this.visSuction=suctionAmount;}

    @Override
    public void setTaintSuction(int suctionAmount){this.taintSuction=suctionAmount;}

    @Override
    public void setSuction(int suctionAmount){this.taintSuction=this.visSuction=suctionAmount;}
}
