package com.raven_cze.projt2.common.content.blocks.references;

import com.raven_cze.projt2.common.content.PT2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

public class BlockCustomWood extends RotatedPillarBlock {

	public BlockCustomWood(Properties properties){super(properties);}
	
	@Override
	public boolean isFlammable(BlockState state,BlockGetter level,BlockPos pos,Direction direction){return true;}
	
	@Override
	public int getFlammability(BlockState state,BlockGetter level,BlockPos pos,Direction direction){return 5;}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
	
	@Override
	public BlockState getToolModifiedState(BlockState state,Level level,BlockPos pos,Player player,ItemStack stack,ToolAction toolAction) {
		if(stack.getItem() instanceof AxeItem){
			if(state.is(PT2Blocks.GREATWOOD_LOG.get())){
				return PT2Blocks.STRIPPED_GREATWOOD_LOG.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
			}if(state.is(PT2Blocks.SILVERWOOD_LOG.get())){
				return PT2Blocks.STRIPPED_SILVERWOOD_LOG.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
			}if(state.is(PT2Blocks.TAINT_LOG.get())){
				return PT2Blocks.STRIPPED_TAINT_LOG.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
			}if(state.is(PT2Blocks.PETRIFIED_LOG.get())){
				return PT2Blocks.STRIPPED_PETRIFIED_LOG.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
			}if(state.is(PT2Blocks.GREATWOOD_WOOD.get())){
				return PT2Blocks.STRIPPED_GREATWOOD_WOOD.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
			}if(state.is(PT2Blocks.SILVERWOOD_WOOD.get())){
				return PT2Blocks.STRIPPED_SILVERWOOD_WOOD.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
			}if(state.is(PT2Blocks.PETRIFIED_WOOD.get())){
				return PT2Blocks.STRIPPED_PETRIFIED_WOOD.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
			}if(state.is(PT2Blocks.TAINT_WOOD.get())){
				return PT2Blocks.STRIPPED_TAINT_WOOD.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
			}
		}
		
		return super.getToolModifiedState(state, level, pos, player, stack, toolAction);
	}
}
