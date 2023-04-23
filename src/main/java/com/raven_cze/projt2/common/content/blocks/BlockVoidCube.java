package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.tiles.TileVoidCube;
import com.raven_cze.projt2.common.content.blocks.references.PT2Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"deprecation"})
public class BlockVoidCube extends PT2Block implements EntityBlock{
    public static final IntegerProperty CUBE_TYPE=IntegerProperty.create("cube_type",0,3);
    public Integer type;

    public BlockVoidCube(Properties properties){
        super(properties);
        CUBE_TYPE.value(3);
        this.type=0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState>pBuilder){pBuilder.add(CUBE_TYPE);}

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state,@NotNull BlockGetter level,@NotNull BlockPos pos,@NotNull CollisionContext ctx){return Shapes.box(0F,0F,0F,1F,1F,1F);}

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {return getShape(pState, pLevel, pPos, pContext);}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){return new TileVoidCube(pos,state);}

}
