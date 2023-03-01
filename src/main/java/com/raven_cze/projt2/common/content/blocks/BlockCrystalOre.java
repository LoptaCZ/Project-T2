package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.PT2Items;
import com.raven_cze.projt2.common.content.tiles.TileCrystalOre;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockCrystalOre extends HalfTransparentBlock implements EntityBlock{

    public static final DirectionProperty DIRECTION=DirectionProperty.create("direction");
    private String type="vis";
    private Direction orientation=Direction.UP;
    public BlockCrystalOre(Properties properties) {
        super(properties);
        //  idk 2.5F
        //  idk 0.75F
        //  0 - 15  | 0F - 1F
        properties.sound(SoundType.STONE);
        //  Registry Name
        properties.randomTicks();// I Guess?!
        //  idk true
        //  idk 0.8F
        registerDefaultState(getStateDefinition().any().setValue(DIRECTION,Direction.UP));
    }

    public Block setType(String type){this.type=type;return this;}

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(@NotNull BlockState state,@NotNull Level level,@NotNull BlockPos pos,@NotNull Random random){
        if(random.nextInt(9)!=0)return;

        //  CREATE PARTICLE { FXSpark }
        int x=pos.getX();
        int y=pos.getY();
        int z=pos.getZ();

        level.addParticle(ParticleTypes.END_ROD,(x+level.random.nextFloat()),(y+level.random.nextFloat()),(z+level.random.nextFloat()),0,0,0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block,BlockState>pBuilder){
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
        if(Block.isFaceFull(this.getCollisionShape(block,lvl,pos,CollisionContext.of(lvl.players().get(lvl.players().size()-1))),dir)){
            orientation=dir;
        }else return null;
        if( block.getBlock().equals(PT2Blocks.CRYSTAL_ORE_VIS.get()) || block.getBlock().equals(PT2Blocks.CRYSTAL_ORE_WATER.get()) ||
                block.getBlock().equals(PT2Blocks.CRYSTAL_ORE_EARTH.get()) || block.getBlock().equals(PT2Blocks.CRYSTAL_ORE_FIRE.get()) ||
        block.getBlock().equals(PT2Blocks.CRYSTAL_ORE_AIR.get()) || block.getBlock().equals(PT2Blocks.CRYSTAL_ORE_TAINT.get())
        )return null;
        BlockState state=this.defaultBlockState();
        state.setValue(DIRECTION,dir);
        return state;
    }

    VoxelShape CORE=Shapes.box(0.25F,0.25F,0.25F,0.75F,0.75F,0.75F);
    VoxelShape NORTH=Shapes.box(0F,0F,0F,0F,0F,0F);
    VoxelShape EAST=Shapes.box(0F,0F,0F,0F,0F,0F);
    VoxelShape SOUTH=Shapes.box(0F,0F,0F,0F,0F,0F);
    VoxelShape WEST=Shapes.box(0F,0F,0F,0F,0F,0F);
    VoxelShape UP=Shapes.box(0F,0F,0F,0F,0F,0F);
    VoxelShape DOWN=Shapes.box(0F,0F,0F,0F,0F,0F);

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state,@NotNull BlockGetter getter,@NotNull BlockPos pos,@NotNull CollisionContext context){
        VoxelShape shape=Shapes.box(0.0F,0.0F,0.0F,1.0F,1.0F,1.0F);
        if(getter.getBlockEntity(pos)instanceof TileCrystalOre){
            float th = 0.25F;
            switch(orientation){
                case NORTH->shape=Utils.Voxel.combine(CORE,NORTH);
                case EAST->shape=Utils.Voxel.combine(CORE,EAST);
                case WEST->shape=Utils.Voxel.combine(CORE,SOUTH);
                case SOUTH->shape=Utils.Voxel.combine(CORE,WEST);
                case UP->shape=Utils.Voxel.combine(CORE,UP);
                case DOWN->shape=Utils.Voxel.combine(CORE,DOWN);
            }
        }
        return shape;
    }

    @Override
    public@NotNull VoxelShape getCollisionShape(@NotNull BlockState state,@NotNull BlockGetter level,@NotNull BlockPos pos,@NotNull CollisionContext context){return getShape(state,level,pos,context);}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){
        TileCrystalOre tco=new TileCrystalOre(pos,state);
        tco.crystals=2;
        tco.rune=type;
        tco.orientation=orientation;
        return tco;
    }

    @Override
    public @NotNull List<ItemStack>getDrops(@NotNull BlockState state,LootContext.@NotNull Builder builder){
        List<ItemStack>drops=new ArrayList<>();
        BlockPos pos=BlockPos.ZERO;
        try{

        }catch(Exception e){
            try{
                drops.add(new ItemStack(PT2Items.CRYSTAL_VIS.get(), ((TileCrystalOre)builder.getLevel().getBlockEntity(pos)).crystals) );
            }catch(Exception ignored){}
        }
        return drops;
    }
}
