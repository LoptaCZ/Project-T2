package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Particles;
import com.raven_cze.projt2.common.content.blocks.references.SimpleBlockEntityTicker;
import com.raven_cze.projt2.common.content.tiles.*;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockApparatusFragile extends BlockApparatus implements EntityBlock{
	public static final BooleanProperty WATERLOGGED=BlockStateProperties.WATERLOGGED;

	public BlockApparatusFragile(BlockBehaviour.Properties properties){
		super(properties);// TODO BlockApparatusFragile
		//	Some Boolean
		registerDefaultState(getStateDefinition().any().setValue(WATERLOGGED,false));
	}

	//	All Tile Entities | Block Entities
	//	0	Conduit				[ TileConduit ]
	//	1	Filter				[ TileFilter ]
	//	2	Bellows				[ TileBellows ]
	//	3	Conduit Tank		[ TileConduitTank ]
	//	4	Brain				[ TileBrain ]
	//	5	Conduit Valve		[ TileConduitValve ]
	//	6	Purifier			[ TilePurifier ]
	//	7	Trunk Summon		[ EntityTrunk ]
	//	8	Conduit Valve Adv	[ TileConduitValveAdvanced ]
	//	9	Conduit Pump		[ TileConduitPump ]
	//	10	Nitor				[ XXX ]
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState>b){b.add(WATERLOGGED);}

	@Override
	public void animateTick(@NotNull BlockState s,@NotNull Level l,@NotNull BlockPos p,@NotNull Random r){
		if(s.getBlock().getRegistryName()!=null){
			String name=s.getBlock().getRegistryName().getPath();
			double x=p.getX()+0.5D;
			double y=p.getY()+0.5D;
			double z=p.getZ()+0.5D;

			switch(name){
				case"nitor"->{
					try{
						l.addParticle(PT2Particles.SPARKLE.get(),x,y,z,0,0,0);
						//Minecraft.getInstance().particleEngine.createParticle(PT2Particles.SPARKLE.get(),x,y,z,0,0,0);
					}catch(Exception e){if(Utils.changed(e))e.printStackTrace();}
				}
				case"ah"->{}
			}
		}
		super.animateTick(s,l,p,r);
	}

	@Nullable
	@Override
	public<T extends BlockEntity>BlockEntityTicker<T>getTicker(Level level,BlockState state,BlockEntityType<T>ent){
		String name=state.getBlock().getRegistryName().getPath();
		BlockEntityTicker<T>returner=null;
		switch(name){
			case"conduit"->returner=(BlockEntityTicker<T>) new SimpleBlockEntityTicker();
			case"filter"->returner=(BlockEntityTicker<T>) new SimpleBlockEntityTicker();
			case"bellows"->returner=(BlockEntityTicker<T>) new SimpleBlockEntityTicker();
			case"tank"->returner=(BlockEntityTicker<T>) new SimpleBlockEntityTicker();
			case"brain"->returner=(BlockEntityTicker<T>) new SimpleBlockEntityTicker();
			case"conduit_valve"->returner=(BlockEntityTicker<T>) new SimpleBlockEntityTicker();
			case"purifier"->returner=(BlockEntityTicker<T>) new SimpleBlockEntityTicker();
			case"conduit_valve_adv"->returner=(BlockEntityTicker<T>) new SimpleBlockEntityTicker();
			case"conduit_pump"->returner=(BlockEntityTicker<T>) new SimpleBlockEntityTicker();
		}
		return returner;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos p,@NotNull BlockState s){
		BlockEntity tile=null;
		if(s.getBlock().getRegistryName()!=null){
			String name=s.getBlock().getRegistryName().getPath();
			switch(name){
				//case"conduit"->tile=new TileConduit(p,s);
				case"filter"->tile=new TileFilter(p,s);
				case"bellows"->tile=new TileBellows(p,s);
				case"tank"->tile=new TileConduitTank(p,s);
				case"brain"->tile=new TileBrain(p,s);
				case"conduit_valve"->tile=new TileConduitValve(p,s);
				case"purifier"->tile=new TilePurifier(p,s);
				case"conduit_valve_adv"->tile=new TileConduitValveAdv(p,s);
				case"conduit_pump"->tile=new TileConduitPump(p,s);
			}
		}
		return tile;
	}
}
