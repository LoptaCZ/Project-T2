package com.raven_cze.projt2.common.content.blocks;

import com.google.common.collect.Maps;
import com.raven_cze.projt2.api.EnumConduitConType;
import com.raven_cze.projt2.common.content.blocks.references.PT2Block;
import com.raven_cze.projt2.common.content.tiles.TileConduit;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

@SuppressWarnings({"deprecation","unused"})
public class BlockConduit extends PT2Block implements EntityBlock{
    public static final EnumProperty<EnumConduitConType>NORTH=EnumProperty.create("north",EnumConduitConType.class);
    public static final EnumProperty<EnumConduitConType>EAST=EnumProperty.create("east",EnumConduitConType.class);
    public static final EnumProperty<EnumConduitConType>SOUTH=EnumProperty.create("south",EnumConduitConType.class);
    public static final EnumProperty<EnumConduitConType>WEST=EnumProperty.create("west",EnumConduitConType.class);
    public static final EnumProperty<EnumConduitConType>UP=EnumProperty.create("up",EnumConduitConType.class);
    public static final EnumProperty<EnumConduitConType>DOWN=EnumProperty.create("down",EnumConduitConType.class);
    public static final BooleanProperty WATERLOGGED=BlockStateProperties.WATERLOGGED;
    public static final EnumMap<Direction,EnumProperty<EnumConduitConType>>FacingPropertyMap=Util.make(Maps.newEnumMap(Direction.class),(p)->{
        p.put(Direction.NORTH,NORTH);
        p.put(Direction.EAST,EAST);
        p.put(Direction.SOUTH,SOUTH);
        p.put(Direction.WEST,WEST);
        p.put(Direction.UP,UP);
        p.put(Direction.DOWN,DOWN);
    });
    public BlockConduit(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any()
                .setValue(NORTH,EnumConduitConType.NONE)
                .setValue(EAST,EnumConduitConType.NONE)
                .setValue(SOUTH,EnumConduitConType.NONE)
                .setValue(WEST,EnumConduitConType.NONE)
                .setValue(UP,EnumConduitConType.NONE)
                .setValue(DOWN,EnumConduitConType.NONE)
                .setValue(WATERLOGGED,false));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState>b)
    {b.add(NORTH,EAST,SOUTH,WEST,UP,DOWN,WATERLOGGED);}

    @Override
    public @NotNull FluidState getFluidState(@NotNull BlockState state){
        //return super.getFluidState(pState);
        return state.getValue(WATERLOGGED)?Fluids.WATER.getSource(false):super.getFluidState(state);
    }



    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state,@NotNull BlockGetter getter,@NotNull BlockPos pos,@NotNull CollisionContext context){
        VoxelShape shape=Shapes.box(5,5,5,11,11,11);
        VoxelShape SHAPE_NORTH=box(5,5,0,11,11,5);
        VoxelShape SHAPE_EAST =box(11,5,5,16,11,11);
        VoxelShape SHAPE_SOUTH=box(5,5,11,11,11,16);
        VoxelShape SHAPE_WEST =box(0,5,5,5,11,11);
        VoxelShape SHAPE_UP   =box(5,11,5,11,16,11);
        VoxelShape SHAPE_DOWN =box(5,0,5,11,5,11);

        if(state.getValue(UP).isConnected())shape= Utils.Voxel.combine(shape,SHAPE_UP);
        if(state.getValue(DOWN).isConnected())shape=Utils.Voxel.combine(shape,SHAPE_DOWN);
        if(state.getValue(NORTH).isConnected())shape=Utils.Voxel.combine(shape,SHAPE_NORTH);
        if(state.getValue(EAST).isConnected())shape=Utils.Voxel.combine(shape,SHAPE_EAST);
        if(state.getValue(SOUTH).isConnected())shape=Utils.Voxel.combine(shape,SHAPE_SOUTH);
        if(state.getValue(WEST).isConnected())shape=Utils.Voxel.combine(shape,SHAPE_WEST);
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx){
        BlockState state=this.defaultBlockState();
        FluidState fluidState=ctx.getLevel().getFluidState(ctx.getClickedPos());
        state.setValue(WATERLOGGED,fluidState.getType()==Fluids.WATER);
        return state;
    }

    @Override
    public void setPlacedBy(@NotNull Level level,@NotNull BlockPos pos,@NotNull BlockState state,@Nullable LivingEntity placer,@NotNull ItemStack stack){
        super.setPlacedBy(level,pos,state,placer,stack);
        BlockState facingState;
        for(Direction d:Direction.values()){
            BlockPos posOff=pos.relative(d);
            facingState=level.getBlockState(posOff);

            if(level.getBlockEntity(posOff)!=null){
                if(facingState.getBlock().getRegistryName()!=null){
                    switch(facingState.getBlock().getRegistryName().getPath()){
                        case"none"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.NONE);
                        case"unknown"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.UNKNOWN);
                        case"crucible_basic"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.CRUCIBLE_BASIC);
                        case"crucible_eyes"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.CRUCIBLE_EYES);
                        case"crucible_souls"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.CRUCIBLE_SOULS);
                        case"crucible_thaumium"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.CRUCIBLE_THAUMIUM);
                        case"dark_generator"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.DARK_GENERATOR);
                        case"furnace"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.FURNACE);
                        case"filter"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.FILTER);
                        case"infuser"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.INFUSER);
                        case"dinfuser"->state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.DINFUSER);
                    }
                    state=state.setValue(FacingPropertyMap.get(d),EnumConduitConType.DINFUSER);
                    level.setBlockAndUpdate(pos,state);
                }
            }
        }
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state,@NotNull Direction direction,@NotNull BlockState neighborState,@NotNull LevelAccessor level,@NotNull BlockPos pos,@NotNull BlockPos neighbor){
        EnumProperty<EnumConduitConType>property=FacingPropertyMap.get(direction);
        if(property.equals(EnumConduitConType.UNKNOWN))return state.setValue(property,EnumConduitConType.UNKNOWN);
        return state.setValue(property,EnumConduitConType.NONE);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState statetate){
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos,@NotNull BlockState pState){return new TileConduit(pPos,pState);}
}
