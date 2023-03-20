package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.tiles.TileTotem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({})
public class BlockTotem extends BlockApparatusWood implements EntityBlock{
	private boolean isBad;
	public BlockTotem(Properties properties){
		super(properties);
		isBad=false;
	}
	public BlockTotem(Properties properties,boolean isBad){
		super(properties);
		isBad=isBad;
	}
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){
		TileTotem te=new TileTotem(pos,state);
		te.setBad();
		return te;
	}
}
