package com.raven_cze.projt2.common.content.blocks.references;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

public class PT2SaplingBlock extends BushBlock implements BonemealableBlock {
	public static final IntegerProperty STAGE=BlockStateProperties.STAGE;
	protected static final VoxelShape SHAPE=Block.box(2,0,2,14,12,14);
	private final AbstractTreeGrower treeGrower;

	public PT2SaplingBlock(AbstractTreeGrower grower,BlockBehaviour.Properties properties){
		super(properties);
		this.treeGrower=grower;
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE,Integer.valueOf(0)));
	}
	
	public boolean isFull(BlockState state){return false;}
	
	public VoxelShape getShape(BlockState state,BlockGetter getter,BlockPos pos,CollisionContext collision){return SHAPE;}
	
	public void randomTick(BlockState state,ServerLevel world,BlockPos pos,Random random){
		if (world.getMaxLocalRawBrightness(pos.above())>=9 && random.nextInt(7) == 0){
			//if (!p_56004_.isAreaLoaded(p_56005_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
			this.advanceTree(world,pos,state,random);
		}
	}

	public void advanceTree(ServerLevel world, BlockPos pos, BlockState state, Random random){
		if(state.getValue(STAGE)==0) {
			world.setBlock(pos,state.cycle(STAGE),4);
		}else{
			if(!ForgeEventFactory.saplingGrowTree(world, random, pos)) return;
			this.treeGrower.growTree(world,world.getChunkSource().getGenerator(),pos,state,random);
		}
	}
	
	@Override
	public boolean isValidBonemealTarget(BlockGetter getter,BlockPos pos,BlockState state,boolean bool){return true;}

	@Override
	public boolean isBonemealSuccess(Level world,Random random,BlockPos pos,BlockState state){return (double)world.random.nextFloat()<0.45D;}

	@Override
	public void performBonemeal(ServerLevel world,Random random,BlockPos pos,BlockState state){
		this.advanceTree(world,pos,state,random);
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState>stateDef){stateDef.add(STAGE);}
}