package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.tiles.TileVoidLock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BlockVoidLock extends Block implements EntityBlock{
	public static IntegerProperty NORTH_RUNE=IntegerProperty.create("north_rune",0,5);
	public static IntegerProperty EAST_RUNE=IntegerProperty.create("east_rune",0,5);
	public static IntegerProperty SOUTH_RUNE=IntegerProperty.create("south_rune",0,5);
	public static IntegerProperty WEST_RUNE=IntegerProperty.create("west_rune",0,5);

	public BlockVoidLock(Properties properties){
		super(properties);
		properties.strength(10.0F);

		registerDefaultState(getStateDefinition().any()
				.setValue(NORTH_RUNE,0)
				.setValue(EAST_RUNE,0)
				.setValue(SOUTH_RUNE,0)
				.setValue(WEST_RUNE,0)
		);
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState){return new TileVoidLock(pPos,pState);}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block,BlockState>b){
		b.add(NORTH_RUNE,EAST_RUNE,SOUTH_RUNE,WEST_RUNE);
	}

	@Override
	public void neighborChanged(@NotNull BlockState pState,@NotNull Level pLevel,@NotNull BlockPos pPos,@NotNull Block pBlock,@NotNull BlockPos pFromPos,boolean pIsMoving) {
		super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
		if(pLevel.getBlockEntity(pFromPos)!=null)
			Objects.requireNonNull(pLevel.getBlockEntity(pFromPos)).setChanged();
	}

	@Nullable
	@Override
	public<T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type){
		//return createTickerHelper( type,PT2Content.TILE_HOLE.get(),TileHole::tick );
		return type==PT2Tiles.TILE_VOID_LOCK.get() ? TileVoidLock::tick : null;
	}
}
