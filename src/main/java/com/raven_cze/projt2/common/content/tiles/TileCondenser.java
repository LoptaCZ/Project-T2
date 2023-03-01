package com.raven_cze.projt2.common.content.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileCondenser extends BlockEntity {
    public float degredation=0.0F;

    public TileCondenser(BlockPos pWorldPosition, BlockState pBlockState) {
        super(null, pWorldPosition, pBlockState);
    }
}
