package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.blocks.apparatus.BlockApparatusMetal;
import com.raven_cze.projt2.common.content.tiles.TileVoidChest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockVoidChest extends BlockApparatusMetal implements EntityBlock{
    public BlockVoidChest(Properties properties){super(properties);}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){
        return new TileVoidChest(pos,state);
    }

    @Override
    protected void blockBounds(float startX, float startY, float startZ, float endX, float endY, float endZ){
        super.blockBounds(0,0,0,1,1,1);
    }
}
