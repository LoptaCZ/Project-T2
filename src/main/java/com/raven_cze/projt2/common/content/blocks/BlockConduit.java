package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.api.IConnection;
import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.blocks.apparatus.BlockApparatusFragile;
import com.raven_cze.projt2.common.content.tiles.TileConduit;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
public class BlockConduit extends BlockApparatusFragile implements EntityBlock{
    public static final BooleanProperty CONNECT_NORTH=BooleanProperty.create("connect_north");
    public static final BooleanProperty CONNECT_EAST=BooleanProperty.create("connect_east");
    public static final BooleanProperty CONNECT_SOUTH=BooleanProperty.create("connect_south");
    public static final BooleanProperty CONNECT_WEST=BooleanProperty.create("connect_west");
    public static final BooleanProperty CONNECT_UP=BooleanProperty.create("connect_up");
    public static final BooleanProperty CONNECT_DOWN=BooleanProperty.create("connect_down");

    public BlockConduit(Properties properties){
        super(properties);

        registerDefaultState(getStateDefinition().any()
                .setValue(CONNECT_NORTH,false)
                .setValue(CONNECT_EAST,false)
                .setValue(CONNECT_SOUTH,false)
                .setValue(CONNECT_WEST,false)
                .setValue(CONNECT_UP,false)
                .setValue(CONNECT_DOWN,false)
        );
    }

    public BooleanProperty getProperty(Direction side){
        switch(side){
            case NORTH->{return CONNECT_NORTH;}
            case EAST->{return CONNECT_EAST;}
            case SOUTH->{return CONNECT_SOUTH;}
            case WEST->{return CONNECT_WEST;}
            case UP->{return CONNECT_UP;}
            default->{return CONNECT_DOWN;}
        }
    }

    public boolean isDisconnected(LevelAccessor level,BlockPos pos,Direction dir){
        TileConduit te=(TileConduit)level.getBlockEntity(pos);
        if(te==null)return false;
        return te.isDisconnected(dir);
    }

    public void setDisconnected(Level level,BlockPos pos,Direction side,boolean disconnected){
        TileConduit pipe=(TileConduit)level.getBlockEntity(pos);
        if(pipe==null){
            if(disconnected){
                //pipe= (TileConduit)level.getBlockEntity(pos);
                level.setBlockAndUpdate(pos,level.getBlockState(pos).setValue(getProperty(side),false));
            }
        }else{
            pipe.setDisconnected(side,disconnected);
            level.setBlockAndUpdate(pos,level.getBlockState(pos).setValue(getProperty(side),!disconnected));
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        return getState(context.getLevel(),context.getClickedPos(),null);
    }

    private BlockState getState(Level level,BlockPos pos,@Nullable BlockState ignoredOldState){
        return defaultBlockState().setValue(CONNECT_NORTH,isConnected(level,pos,Direction.NORTH))
                .setValue(CONNECT_EAST,isConnected(level,pos,Direction.EAST))
                .setValue(CONNECT_SOUTH,isConnected(level,pos,Direction.SOUTH))
                .setValue(CONNECT_WEST,isConnected(level,pos,Direction.WEST))
                .setValue(CONNECT_UP,isConnected(level,pos,Direction.UP))
                .setValue(CONNECT_DOWN,isConnected(level,pos,Direction.DOWN));
    }

    public boolean isConnected(LevelAccessor level,BlockPos pos,Direction facing){
        TileConduit conduit=(TileConduit)level.getBlockEntity(pos);
        if(level.getBlockEntity(pos.relative(facing))instanceof TileConduit other){
            if(!isAbleToConnect(level,pos,facing))return false;
            boolean canSelfConnect=conduit==null || !conduit.isDisconnected(facing);
            if(!canSelfConnect)return false;

            return other==null || !other.isDisconnected(facing.getOpposite());
        }else if(level.getBlockEntity(pos.relative(facing))instanceof IConnection other){
            return other==null;
        }
        return false;
    }

    public boolean isAbleToConnect(LevelAccessor level,BlockPos pos,Direction facing){
        return isConduit(level,pos,facing) || canConnectTo(level,pos,facing);
    }

    public boolean canConnectTo(LevelAccessor level,BlockPos pos,Direction facing){
        BlockEntity te=level.getBlockEntity(pos.relative(facing));
        if(te instanceof IConnection ICte){
            return ICte.isConnectable(facing);
        }
        return (te!=null);
    }

    public boolean isConduit(LevelAccessor level,BlockPos pos,Direction facing){
        BlockState state=level.getBlockState(pos.relative(facing));
        return state.getBlock().equals(this) || state.getBlock().equals(PT2Blocks.CRUCIBLE_BASIC.get());
    }

    //  SHAPES
    public static final VoxelShape SHAPE_CORE =box(5.0D,5.0D,5.0D,11.0D,11.0D,11.0D);
    public static final VoxelShape SHAPE_NORTH=box(5,5,0,11,11,5);
    public static final VoxelShape SHAPE_EAST =box(11,5,5,16,11,11);
    public static final VoxelShape SHAPE_SOUTH=box(5,5,11,11,11,16);
    public static final VoxelShape SHAPE_WEST =box(0,5,5,5,11,11);
    public static final VoxelShape SHAPE_UP   =box(5,11,5,11,16,11);
    public static final VoxelShape SHAPE_DOWN =box(5,0,5,11,5,11);

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state,@NotNull BlockGetter getter,@NotNull BlockPos pos,@NotNull CollisionContext context){
        TileConduit te=null;
        if(getter instanceof LevelAccessor)
            te=(TileConduit)getter.getBlockEntity(pos);
        VoxelShape shape=SHAPE_CORE;

        if(state.getValue(CONNECT_UP))
            if(te!=null)
                shape=Utils.Voxel.combine(shape,SHAPE_UP);
        if(state.getValue(CONNECT_DOWN))
            if(te!=null)
                shape=Utils.Voxel.combine(shape,SHAPE_DOWN);
        if(state.getValue(CONNECT_NORTH))
            if(te!=null)
                shape=Utils.Voxel.combine(shape,SHAPE_NORTH);
        if(state.getValue(CONNECT_EAST))
            if(te!=null)
                shape=Utils.Voxel.combine(shape,SHAPE_EAST);
        if(state.getValue(CONNECT_SOUTH))
            if(te!=null)
                shape=Utils.Voxel.combine(shape,SHAPE_SOUTH);
        if(state.getValue(CONNECT_WEST))
            if(te!=null)
                shape=Utils.Voxel.combine(shape,SHAPE_WEST);

        return shape;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block,BlockState>pBuilder){
        pBuilder.add(CONNECT_NORTH,CONNECT_EAST,CONNECT_SOUTH,CONNECT_WEST,CONNECT_UP,CONNECT_DOWN);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void neighborChanged(BlockState state,Level level,BlockPos pos,Block block,BlockPos pos1,boolean b){
        super.neighborChanged(state,level,pos,block,pos1,b);
        BlockState newState=getState(level,pos,state);
        if(!state.getProperties().stream().allMatch(property -> state.getValue(property).equals(newState.getValue(property)))) {
            level.setBlockAndUpdate(pos,newState);
            TileConduit.markPipesDirty(level,pos);
        }
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos,@NotNull BlockState pState){
        return new TileConduit(pPos,pState);
    }

    @Nullable
    public TileConduit getTileEntity(LevelAccessor level,BlockPos pos){
        BlockEntity te=level.getBlockEntity(pos);
        if(te instanceof TileConduit)return (TileConduit)te;
        return null;
    }
}
