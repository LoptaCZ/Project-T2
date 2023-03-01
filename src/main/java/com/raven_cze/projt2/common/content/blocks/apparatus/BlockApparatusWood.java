package com.raven_cze.projt2.common.content.blocks.apparatus;

import com.raven_cze.projt2.common.content.tiles.TileCondenser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockApparatusWood extends BlockApparatus{
	public BlockApparatusWood(BlockBehaviour.Properties properties){
		super(properties);
		// TODO BlockApparatusWood
	}
	//	addCreativeItems(ArrayList<ItemStack>list)
	
	//	All Tile Entities | Block Entities
	//	0	Condenser			[ TileCondenser ]
	//	1	Duplicator			[ TileDuplicator ]
	//	2	Repairer			[ TileRepairer ]
	//	3	Totem Block Good	[ TileTotem ]
	//	4	Totem Block Bad		[ TileTotem ]

	//###########################################################

	//	Custom Bounding Box for Condenser
	
	//	Some IBlockAccess for Condenser
	
	//	Collision for Condenser??
	
	//	Orientation for Duplicator
	
	//	Totem things
	
	//	Condenser smoke

	//TODO Change Particle Appearance
	@Override
	public void animateTick(@NotNull BlockState state,@NotNull Level level,@NotNull BlockPos pos,@NotNull Random rand){
		String regName=state.getBlock().getRegistryName().getPath();
		int x=pos.getX(),y=pos.getY(),z=pos.getZ();
		if(regName.equals("totem_vis") && rand.nextInt(10)==0){
			level.addParticle(ParticleTypes.HAPPY_VILLAGER,x+rand.nextFloat(),y+rand.nextFloat(),z+rand.nextFloat(),0.5F,0.5F,0.5F);
		}
		if(regName.equals("totem_taint") && rand.nextInt(10)==0){
			level.addParticle(ParticleTypes.ANGRY_VILLAGER,x+rand.nextFloat(),y+rand.nextFloat(),z+rand.nextFloat(),0.5F,0.5F,0.5F);
		}
		if(regName.equals("condenser")){
			if(level.getBlockEntity(pos)instanceof TileCondenser condenser){
				if(condenser.degredation>0.0F){
					int at=(int)(25.0F*(4550.0F-condenser.degredation)/4550.0F);

					level.playSound(null,pos,SoundEvents.GENERIC_BURN,SoundSource.BLOCKS,0.2F,2.0F+level.random.nextFloat()*0.4F);
					for(int a=0;a<at;a++){
						level.addParticle(ParticleTypes.LARGE_SMOKE,x+rand.nextFloat(),y+rand.nextFloat(),z+rand.nextFloat(),0,0,0);
					}
				}
			}
		}
		super.animateTick(state,level,pos,rand);
	}
}
