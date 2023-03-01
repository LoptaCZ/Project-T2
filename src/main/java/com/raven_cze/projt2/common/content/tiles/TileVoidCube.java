package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.blocks.BlockVoidCube;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileVoidCube extends BlockEntity{
    public TileVoidCube(BlockPos pos, BlockState state) {
        super(PT2Tiles.TILE_VOID_CUBE.get(),pos,state);
    }
    public int cubeType=0;

    public boolean shouldRenderFace(Direction face){
        //return face.getAxis()==Direction.Axis.X;
        return true;
    }
    public int getCubeType(){
        if(this.level!=null){
            this.cubeType=((BlockVoidCube)this.level.getBlockState(this.worldPosition).getBlock()).type;
        }return this.cubeType;
    }
}
