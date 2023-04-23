package com.raven_cze.projt2.common.content.blocks.references;

import com.raven_cze.projt2.api.ITickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleBlockEntityTicker<T extends BlockEntity>implements BlockEntityTicker<T>{
    @Override
    public void tick(Level lvl,BlockPos pos,BlockState state,T ent){
        if(ent instanceof ITickableBlockEntity tbe){
            if(lvl.isClientSide)tbe.tickClient();
            tbe.tickServer();
        }
    }
}
