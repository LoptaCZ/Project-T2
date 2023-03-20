package com.raven_cze.projt2.common.content.blocks.apparatus;

import com.raven_cze.projt2.common.content.tiles.TileArcaneFurnace;
import com.raven_cze.projt2.common.content.tiles.TileCrucible;
import com.raven_cze.projt2.common.content.tiles.TileGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockApparatusMetal extends BlockApparatus implements EntityBlock{
	public static final BooleanProperty ACTIVE=BooleanProperty.create("active");
	public BlockApparatusMetal(Properties properties){
		super(properties);

		registerDefaultState(getStateDefinition().any().setValue(ACTIVE,false));//,""
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState>b){b.add(ACTIVE);}
	//	All Tile Entities | Block Entities
	//	0	Crucible			[ TileCrucible ]
	//	1	Crucible of Eyes	[ TileCrucible ]
	//	2	Crucible Thaumium	[ TileCrucible ]
	//	3	Crucible of Souls	[ TileCrucible ]
	//	4	Arcane Furnace		[ TileArcaneFurnace ]
	//	5	Generator			[ TileGenerator ]
	//	6	Crystalizer			[ TileCrystalizer ]
	//	7	Bore				[ TileBore ]
	//	8	Void Chest			[ TileVoidChest ]
	//	9	Void Interface		[ TileVoidInterface ]
	//	10	Conduit Tank		[ TileConduitTank ]
	//	11	Soul Brazier		[ TileSoulBrazier ]

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){
		BlockEntity tile=null;
		if(this.getRegistryName()!=null){
			String name=this.getRegistryName().getPath();
			switch(name){
				case"crucible_basic"->{
					tile=new TileCrucible(pos,state);
					((TileCrucible)tile).setTier((short)1);
				}
				case"crucible_eyes"->{
					tile=new TileCrucible(pos,state);
					((TileCrucible)tile).setTier((short)2);
				}
				case"crucible_souls"->{
					tile=new TileCrucible(pos,state);
					((TileCrucible)tile).setTier((short)4);
				}
				case"crucible_thaumium"->{
					tile=new TileCrucible(pos,state);
					((TileCrucible)tile).setTier((short)3);
				}
				case"furnace"->tile=new TileArcaneFurnace(pos,state);
				case"generator"->tile=new TileGenerator(pos,state);
				/*
				case"crystalizer"->{}
				case"bore"->{}
				case"void_chest"->{}
				case"void_interface"->{}
				case"conduit_tank"->{}
				case"brazier"->{}*/
			}
		}
		return tile;
	}
}
