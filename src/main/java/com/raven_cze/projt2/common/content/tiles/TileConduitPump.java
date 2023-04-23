package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.api.IConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileConduitPump extends BlockEntity implements IConnection {
    public TileConduitPump(BlockPos pWorldPosition, BlockState pBlockState) {
        super(null, pWorldPosition, pBlockState);
    }

    @Override
    public boolean isConnectable(Direction direction){return false;}

    @Override
    public boolean isVisSource(){return false;}

    @Override
    public boolean isVisConduit(){return false;}

    @Override
    public float[] subtractVis(float visAmount){return new float[0];}

    @Override
    public float getPureVis(){return 0;}

    @Override
    public float getTaintedVis(){return 0;}

    @Override
    public float getMaxVis(){return 0;}

    @Override
    public int getVisSuction(Direction dir){return 0;}

    @Override
    public int getTaintSuction(Direction dir){return 0;}

    @Override
    public int getSuction(Direction dir){return 0;}

    @Override
    public void setPureVis(float pureAmount){}

    @Override
    public void setTaintedVis(float taintAmount){}

    @Override
    public void setVisSuction(int suctionAmount){}

    @Override
    public void setTaintSuction(int suctionAmount){}

    @Override
    public void setSuction(int suctionAmount){}
}
