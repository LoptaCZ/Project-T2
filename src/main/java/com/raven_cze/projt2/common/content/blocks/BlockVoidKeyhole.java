package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.tiles.TileVoidKeyhole;
import com.raven_cze.projt2.common.content.blocks.references.PT2Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockVoidKeyhole extends PT2Block implements EntityBlock{
    public BlockVoidKeyhole(Properties properties) {
        super(properties);
        properties.strength(10.0F);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState){return new TileVoidKeyhole(pPos,pState);}
}
