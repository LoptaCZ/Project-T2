package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.world.aura.AuraChunk;
import com.raven_cze.projt2.common.content.world.aura.AuraHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileTotem extends BlockEntity{
    private boolean isBad=false;

    public TileTotem(BlockPos pos, BlockState state){
        super(PT2Tiles.TOTEM.get(),pos,state);
    }

    public void setBad(){this.isBad=true;}

    //@Override
    public void tick(){
        if(this.level.isClientSide())return;

        AuraChunk ac=AuraHandler.getAuraChunk(this.level.dimension(),this.worldPosition.getX(),this.worldPosition.getZ());

        if(ac!=null){
            if(isBad){
                //  increaseTaintedPlants
                ac.setTaint((short)(ac.getTaint()+1));
            }else{
                //  decreaseTaintedPlants
                ac.setTaint((short)(ac.getTaint()-1));
            }
        }
    }
    //@Override
    public @NotNull BlockPos getPos(){return this.worldPosition;}
}
