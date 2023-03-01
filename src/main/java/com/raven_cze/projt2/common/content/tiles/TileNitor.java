package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.common.content.PT2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileNitor extends BlockEntity{
    int count=0;
    public TileNitor(BlockPos pWorldPosition,BlockState pBlockState){
        super(PT2Tiles.TILE_NITOR.get(),pWorldPosition,pBlockState);
    }
}
