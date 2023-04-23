package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.tiles.TileHole;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockHole extends BaseEntityBlock implements EntityBlock{
    public BlockHole(Properties properties){
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state,@NotNull BlockGetter level,@NotNull BlockPos pos,@NotNull CollisionContext ctx){
        return Shapes.box(0.0F,0.01F,0.0F,0.99F,1.0F,0.99F);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.getBlockEntity(pPos)instanceof TileHole){
            pLevel.removeBlockEntity(pPos);
            pLevel.removeBlock(pPos,false);
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public<T extends BlockEntity>BlockEntityTicker<T>getTicker(@NotNull Level level,@NotNull BlockState state,@NotNull BlockEntityType<T>type){
        //return createTickerHelper( type,PT2Content.TILE_HOLE.get(),TileHole::tick );
        return type== PT2Tiles.TILE_HOLE.get() ? TileHole::tick : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){
        return new TileHole(pos,state);
    }
}
