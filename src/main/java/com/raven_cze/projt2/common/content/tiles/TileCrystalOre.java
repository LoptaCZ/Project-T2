package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.common.content.PT2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileCrystalOre extends BlockEntity {
    public Direction orientation=Direction.NORTH;
    public short crystals=2;
    public String rune="vis";
    public TileCrystalOre(BlockPos pos, BlockState state){
        super(PT2Tiles.TILE_CRYSTAL.get(),pos,state);
    }

    @Override
    public void load(@NotNull CompoundTag tag){
        super.load(tag);
        this.orientation=Direction.byName(tag.getString("orientation"));
        this.crystals=tag.getShort("crystals");
        this.rune=tag.getString("rune");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag){
        super.saveAdditional(tag);
        tag.putString("orientation",this.orientation.toString());
        tag.putShort("crystals",this.crystals);
        tag.putString("rune",this.rune);
    }
}
