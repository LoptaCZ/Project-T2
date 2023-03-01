package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.blocks.apparatus.BlockApparatusMetal;
import com.raven_cze.projt2.common.content.tiles.TileCrucible;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({ "unused","deprecation"})
public class BlockCrucible extends BlockApparatusMetal implements EntityBlock{
	public static final BooleanProperty SOUL_ACTIVE=BooleanProperty.create("soul_active");
	private short tier;
	private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), BooleanOp.ONLY_FIRST);
	public BlockCrucible(BlockBehaviour.Properties properties){
		super(properties);

		registerDefaultState(getStateDefinition().any().setValue(SOUL_ACTIVE,false));
	}
	
	public Block setTETier(int tier){this.tier=(short)tier;return this;}
	
	public boolean isFull(BlockState state){return false;}
	
	@Override
	public @NotNull RenderShape getRenderShape(@NotNull BlockState state){return RenderShape.MODEL;}
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context){
		if(state.getBlock()==PT2Blocks.CRUCIBLE_SOULS.get())return box(0.0D,0.0D,0.0D,16.0D,16.0D,16.0D);
		return SHAPE;
	}
	public @NotNull VoxelShape getInteractionShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos){return INSIDE;}
	
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state){
		TileCrucible te=new TileCrucible(pos,state);
		te.setTier(this.tier);
		return te;
	}
	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state2, boolean bool){
		super.onPlace(state,level,pos,state2,bool);
		BlockEntity ent=level.getBlockEntity(pos);
		if(ent instanceof TileCrucible cruc){
			cruc.setTier(tier);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block,BlockState>builder){builder.add(SOUL_ACTIVE);}
}
