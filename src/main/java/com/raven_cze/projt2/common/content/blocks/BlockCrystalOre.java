package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Items;
import com.raven_cze.projt2.common.content.PT2Particles;
import com.raven_cze.projt2.common.content.particles.FXWisp;
import com.raven_cze.projt2.common.content.tiles.TileCrystalOre;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
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

@SuppressWarnings("ALL")
public class BlockCrystalOre extends HalfTransparentBlock implements EntityBlock{
    public static final DirectionProperty DIRECTION=DirectionProperty.create("direction");
    private String type="vis";
    public BlockCrystalOre(Properties properties){
        super(properties);
        //  idk 2.5F
        //  idk 0.75F
        //  0 - 15  | 0F - 1F
        properties.sound(SoundType.STONE);
        //  Registry Name
        properties.randomTicks();// I Guess?!
        //  idk true
        //  idk 0.8F
        registerDefaultState(this.getStateDefinition().any());
    }

    public Block setType(String type){this.type=type;return this;}

    private int typeAsInt(String type){
        int returner =-1;
        switch(type){
            default->returner=7;
            case "vis"->returner=0;
            case "air"->returner=1;
            case "water"->returner=2;
            case "earth"->returner=3;
            case "fire"->returner=4;
            case "taint"->returner=5;
        }
        return returner;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(@NotNull BlockState state,@NotNull Level level,@NotNull BlockPos pos,@NotNull Random random){
        if(random.nextInt(9)!=0)return;

        //  CREATE PARTICLE { FXSpark }
        int x=pos.getX();
        int y=pos.getY();
        int z=pos.getZ();

        ParticleOptions WISP=PT2Particles.FX_WISP.get();
        ((FXWisp)WISP).setType(typeAsInt(this.type));
        level.addParticle(WISP,(x+level.random.nextFloat()),(y+level.random.nextFloat()),(z+level.random.nextFloat()),0,0,0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block,BlockState>pBuilder){pBuilder.add(DIRECTION);}

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx){
        if(ctx.getLevel().getBlockState(ctx.getClickedPos().below()).getBlock().getRegistryName().getPath().equals("arcane_seal"))return null;

        return this.defaultBlockState().setValue(DIRECTION,ctx.getClickedFace());
    }

    VoxelShape CORE=Shapes.box(0.25F,0.25F,0.25F,0.75F,0.75F,0.75F);
    VoxelShape NORTH=Shapes.box(0.25F,0.25F,0.25F,0.75F,0.75F,0.75F);
    VoxelShape EAST=Shapes.box(0.25F,0.25F,0.25F,0.75F,0.75F,0.75F);
    VoxelShape SOUTH=Shapes.box(0.25F,0.25F,0.25F,0.75F,0.75F,0.75F);
    VoxelShape WEST=Shapes.box(0.25F,0.25F,0.25F,0.75F,0.75F,0.75F);
    VoxelShape UP=Shapes.box(0.25F,0F,0.25F,0.75F,0.5F,0.75F);
    VoxelShape DOWN=Shapes.box(0.25F,0.5F,0.25F,0.75F,1F,0.75F);

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state,@NotNull BlockGetter getter,@NotNull BlockPos pos,@NotNull CollisionContext context){
        VoxelShape shape=Shapes.box(0.25F,0.25F,0.25F,0.25F,0.25F,0.25F);
        if(getter.getBlockEntity(pos)instanceof TileCrystalOre){
            switch(state.getValue(DIRECTION)){
                case NORTH->shape=Shapes.or(CORE,Shapes.box(0.25F,0.25F,0.5F,0.75F,0.75F,1.0F));
                case SOUTH->shape=Shapes.or(CORE,Shapes.box(0.25F,0.25F,0.0F,0.75F,0.75F,1.0F));

                case EAST->shape=Shapes.or(CORE,Shapes.box(0.0F,0.25F,0.25F,0.5F,0.75F,0.75F));
                case WEST->shape=Shapes.or(CORE,Shapes.box(0.5F,0.25F,0.25F,1.0F,0.75F,0.75F));

                case UP->shape=Shapes.or(CORE,Shapes.box(0.25F,0.0F,0.25F,0.75F,0.5F,0.75F));
                case DOWN->shape=Shapes.or(CORE,Shapes.box(0.25F,0.5F,0.25F,0.75F,1.0F,0.75F));
            }
        }
        return shape;
    }

    @Override
    public@NotNull VoxelShape getCollisionShape(@NotNull BlockState state,@NotNull BlockGetter level,@NotNull BlockPos pos,@NotNull CollisionContext context){return CORE;}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){
        TileCrystalOre tco=new TileCrystalOre(pos,state);
        tco.crystals=2;
        tco.rune=type;
        tco.orientation=state.getValue(DIRECTION);
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
