package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.blocks.references.PT2Block;
import com.raven_cze.projt2.common.content.tiles.TileSeal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"deprecation"})
public class BlockSeal extends PT2Block implements EntityBlock{
    public static final DirectionProperty DIRECTION=DirectionProperty.create("direction");
    private Direction orientation;
    public BlockSeal(Properties props){
        super(props);

        registerDefaultState(getStateDefinition().any().setValue(DIRECTION,Direction.DOWN));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState>pBuilder){
        pBuilder.add(DIRECTION);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx){
        Level lvl           =   ctx.getLevel();
        BlockPos pos        =   ctx.getClickedPos().below();
        Direction dir       =   ctx.getClickedFace();
        BlockState block;
        BlockPos offset=BlockPos.ZERO;
        if(!lvl.isClientSide())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.DEBUG,dir);
        if(!lvl.isClientSide())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.DEBUG,this.orientation);
        switch(dir){
            default->offset=pos;
            case NORTH->offset.south();
            case EAST->offset.west();
            case SOUTH->offset.north();
            case WEST->offset.east();
            case UP->offset.below();
            case DOWN->offset.above(2);
        }
        block=lvl.getBlockState(offset);
        if( block.getBlock().equals(PT2Blocks.SEAL.get()))return null;
        BlockState state=this.defaultBlockState();
        this.orientation=dir;
        state.setValue(DIRECTION,dir);
        return state;
    }

    VoxelShape NORTH=Shapes.box(0F,0F,0F,1F,1F,1F);
    VoxelShape EAST=Shapes.box(0F,0F,0F,1F,1F,1F);
    VoxelShape SOUTH=Shapes.box(0F,0F,0F,1F,1F,1F);
    VoxelShape WEST=Shapes.box(0F,0F,0F,1F,1F,1F);
    VoxelShape UP=Shapes.box(0F,0F,0F,1F,0.9F,1F);
    VoxelShape DOWN=Shapes.box(4.0D,0.0D,4.0D,12.0D,1.0D,12.0D);

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context){
        VoxelShape shape;
        //if(getter.getBlockEntity(pos)instanceof TileSeal){
            switch(orientation){
                default->shape=Shapes.create(0,0,0,16,16,16);
                case NORTH->shape=NORTH;
                case EAST->shape=EAST;
                case WEST->shape=SOUTH;
                case SOUTH->shape=WEST;
                case UP->shape=UP;
                case DOWN->shape=DOWN;
            }
        //}
        return shape;
    }

    @Override
    public@NotNull VoxelShape getCollisionShape(@NotNull BlockState state,@NotNull BlockGetter level,@NotNull BlockPos pos,@NotNull CollisionContext context){return getShape(state,level,pos,context);}


    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state){
        TileSeal tco=new TileSeal(pos,state);
        tco.orientation=orientation;
        return tco;
    }
}
