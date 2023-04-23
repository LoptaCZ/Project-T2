package com.raven_cze.projt2.common.content.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileBellows extends BlockEntity{
    public TileBellows(BlockPos pWorldPosition,BlockState pBlockState){
        super(null,pWorldPosition,pBlockState);
    }

    public boolean isBoosting(TileConduitTank tileConduitTank){
        return false;
    }
}
