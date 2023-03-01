package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.common.content.PT2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileVoidKeyhole extends BlockEntity{
    public String rune;
    public boolean placed;
    public TileVoidKeyhole(BlockPos pos,BlockState state){
        super(PT2Tiles.TILE_VOID_KEYHOLE.get(),pos,state);
        rune="";

        //  RUNE = 0 | VIS
        //  RUNE = 1 | AIR
        //  RUNE = 2 | WATER
        //  RUNE = 3 | EARTH
        //  RUNE = 4 | FIRE
        //  RUNE = 5 | TAINT
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag){
        super.load(compoundTag);
        this.rune=compoundTag.getString("rune");
        this.placed=compoundTag.getBoolean("placed");

        this.setChanged();
        this.getUpdatePacket();
        this.requestModelDataUpdate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag){
        super.saveAdditional(pTag);
        pTag.putString("rune",this.rune);
        pTag.putBoolean("placed",this.placed);
    }

    public boolean shouldRenderFace(Direction dir){
        return dir.equals(Direction.UP);
    }
}
