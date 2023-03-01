package com.raven_cze.projt2.common.content.blocks.apparatus;

import com.raven_cze.projt2.common.content.blocks.references.PT2Block;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public abstract class BlockApparatus extends PT2Block{

	private AABB shape=new AABB(0.0F,0.0F,0.0F,1.0F,1.0F,1.0F);
	
	public BlockApparatus(BlockBehaviour.Properties properties){
		super(properties);
		blockBounds(0.0F,0.0F,0.0F,1.0F,1.0F,1.0F);
		//registerDefaultState(getStateDefinition().any().setValue(DIRECTION, Direction.DOWN));
	}
	//@Override
	//protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block,BlockState>pBuilder){pBuilder.add(DIRECTION);}

	protected void blockBounds(float startX,float startY,float startZ,float endX,float endY,float endZ){shape=new AABB(startX,startY,startZ,endX,endY,endZ);}
	
	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state,@NotNull BlockGetter getter,@NotNull BlockPos pos,@NotNull CollisionContext context){return Shapes.create(shape);}


	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit){
		if(level.isClientSide() || player.isAlive())return InteractionResult.FAIL;
		if(level.getBlockEntity(pos) instanceof MenuProvider tile){
			tile.createMenu(tile.hashCode(),player.getInventory(),player);
			return InteractionResult.CONSUME;
		}
		return super.use(state, level, pos, player, hand, hit);
	}
}
