package com.raven_cze.projt2.common.content.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockApparatusFragile extends BlockApparatus {
	public BlockApparatusFragile(BlockBehaviour.Properties properties){
		super(properties);
		// TODO BlockApparatusFragile
		//	Hardness | Resistance
		properties.sound(SoundType.WOOD);//		Step Sound
		//	Registry Name
		//	Some Boolean
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

}
