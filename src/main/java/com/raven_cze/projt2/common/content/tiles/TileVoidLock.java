package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.blocks.BlockVoidLock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public class TileVoidLock extends BlockEntity{
	public boolean unlocked;
	//	NORTH EAST SOUTH WEST
	private final String[]crystals=new String[]{"vis","air","water","earth","fire","taint"};
	public String[] runes={"vis","vis","vis","vis"};
	public byte random;

	public String reqCrystal="vis";

	private static int soundDelay;

	public TileVoidLock(BlockPos pos,BlockState state){
		super(PT2Tiles.TILE_VOID_LOCK.get(),pos,state);
		this.random=(byte)Math.round(new Random().nextInt(0,5));
		refreshRequest();
		runes[0]=reqCrystal;
		refreshRequest();
		runes[1]=reqCrystal;
		refreshRequest();
		runes[2]=reqCrystal;
		refreshRequest();
		runes[3]=reqCrystal;

		this.getBlockState().setValue(BlockVoidLock.NORTH_RUNE, Arrays.stream(runes).toList().indexOf(runes[0]));
		this.getBlockState().setValue(BlockVoidLock.EAST_RUNE, Arrays.stream(runes).toList().indexOf(runes[1]));
		this.getBlockState().setValue(BlockVoidLock.SOUTH_RUNE, Arrays.stream(runes).toList().indexOf(runes[2]));
		this.getBlockState().setValue(BlockVoidLock.WEST_RUNE, Arrays.stream(runes).toList().indexOf(runes[3]));

		saveAdditional(new CompoundTag());
		soundDelay=450;
	}

	public void refreshRequest(){
		this.random=(byte)Math.round(new Random().nextInt(0,5));
		reqCrystal=crystals[this.random];
	}

	@Override
	public void onLoad(){
		Direction[]dirs=new Direction[]{Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST};
		TileVoidKeyhole tvkh = null;
		if(this.level!=null){
			if(this.unlocked){
				BlockEntity[]tiles=new BlockEntity[4];
				//TODO Checking for surrounding blocks i guess
				//	Yes because this is not a keyhole, its the "door" to the treasure
				//	So you need to check for 4 Keyholes to change their lock symbol
				//	| NORTH | EAST | SOUTH | WEST |

				//	Checking for unlocked Keyholes then maybe doing something
				for(Direction d:dirs){
					switch(d){
						case NORTH->tiles[0]=this.level.getBlockEntity(this.worldPosition.north(1));
						case EAST->tiles[1]=this.level.getBlockEntity(this.worldPosition.east(1));
						case SOUTH->tiles[2]=this.level.getBlockEntity(this.worldPosition.south(1));
						case WEST->tiles[3]=this.level.getBlockEntity(this.worldPosition.west(1));
					}
				}
				if (tiles[0]!=null && tiles[1]!=null && tiles[2]!=null && tiles[3]!=null) {
					if(((TileVoidKeyhole)tiles[0]).placed){
						if(((TileVoidKeyhole)tiles[1]).placed){
							if(((TileVoidKeyhole)tiles[2]).placed){
								if(((TileVoidKeyhole)tiles[3]).placed){
									this.level.setBlockAndUpdate(this.worldPosition,Blocks.DIAMOND_BLOCK.defaultBlockState());
								}
							}
						}
					}
				}
			}else{
				for(Direction d:dirs){
					switch(d){
						case NORTH->{
							BlockEntity te=this.level.getBlockEntity(this.worldPosition.north());
							if(te instanceof TileVoidKeyhole)
								tvkh=(TileVoidKeyhole)te;
							if(tvkh!=null){
								runes[0]=reqCrystal;
								refreshRequest();
								tvkh.rune=this.runes[0];
								this.getBlockState().setValue(BlockVoidLock.NORTH_RUNE,Arrays.stream(runes).toList().indexOf(runes[0]));
							}
						}
						case EAST ->{
							BlockEntity te=this.level.getBlockEntity(this.worldPosition.east());
							if(te instanceof TileVoidKeyhole)
								tvkh=(TileVoidKeyhole)te;
							if(tvkh!=null){
								runes[1]=reqCrystal;
								refreshRequest();
								tvkh.rune=this.runes[1];
								this.getBlockState().setValue(BlockVoidLock.EAST_RUNE,Arrays.stream(runes).toList().indexOf(runes[1]));
							}
						}
						case SOUTH ->{
							BlockEntity te=this.level.getBlockEntity(this.worldPosition.south());
							if(te instanceof TileVoidKeyhole)
								tvkh=(TileVoidKeyhole)te;
							if(tvkh!=null){
								runes[2]=reqCrystal;
								refreshRequest();
								tvkh.rune=this.runes[2];
								this.getBlockState().setValue(BlockVoidLock.SOUTH_RUNE,Arrays.stream(runes).toList().indexOf(runes[2]));
							}
						}
						case WEST ->{
							BlockEntity te=this.level.getBlockEntity(this.worldPosition.west());
							if(te instanceof TileVoidKeyhole)
								tvkh=(TileVoidKeyhole)te;
							if(tvkh!=null){
								runes[3]=reqCrystal;
								refreshRequest();
								tvkh.rune=this.runes[3];
								this.getBlockState().setValue(BlockVoidLock.WEST_RUNE,Arrays.stream(runes).toList().indexOf(runes[3]));
							}
						}
					}
				}
			}
			//super.onLoad();
		}
	}//onLoad

	@Override
	protected void saveAdditional(@NotNull CompoundTag pTag){
		super.saveAdditional(pTag);

		pTag.putString("north_crystal",this.runes[0]);
		pTag.putString("east_crystal",this.runes[1]);
		pTag.putString("south_crystal",this.runes[2]);
		pTag.putString("west_crystal",this.runes[3]);
		pTag.putBoolean("unlocked",this.unlocked);

		this.setChanged();
	}

	@Override
	public void load(@NotNull CompoundTag pTag) {
		super.load(pTag);
		this.runes=new String[]{pTag.getString("north_crystal"),pTag.getString("east_crystal"),pTag.getString("south_crystal"),pTag.getString("west_crystal")};
		this.unlocked=pTag.getBoolean("unlocked");

		switch(runes[0]){
			case"vis"->this.getBlockState().setValue(BlockVoidLock.NORTH_RUNE,0);
			case"air"->this.getBlockState().setValue(BlockVoidLock.NORTH_RUNE,1);
			case"water"->this.getBlockState().setValue(BlockVoidLock.NORTH_RUNE,2);
			case"earth"->this.getBlockState().setValue(BlockVoidLock.NORTH_RUNE,3);
			case"fire"->this.getBlockState().setValue(BlockVoidLock.NORTH_RUNE,4);
			case"taint"->this.getBlockState().setValue(BlockVoidLock.NORTH_RUNE,5);
		}
		switch(runes[1]){
			case"vis"->this.getBlockState().setValue(BlockVoidLock.EAST_RUNE,0);
			case"air"->this.getBlockState().setValue(BlockVoidLock.EAST_RUNE,1);
			case"water"->this.getBlockState().setValue(BlockVoidLock.EAST_RUNE,2);
			case"earth"->this.getBlockState().setValue(BlockVoidLock.EAST_RUNE,3);
			case"fire"->this.getBlockState().setValue(BlockVoidLock.EAST_RUNE,4);
			case"taint"->this.getBlockState().setValue(BlockVoidLock.EAST_RUNE,5);
		}
		switch(runes[2]){
			case"vis"->this.getBlockState().setValue(BlockVoidLock.SOUTH_RUNE,0);
			case"air"->this.getBlockState().setValue(BlockVoidLock.SOUTH_RUNE,1);
			case"water"->this.getBlockState().setValue(BlockVoidLock.SOUTH_RUNE,2);
			case"earth"->this.getBlockState().setValue(BlockVoidLock.SOUTH_RUNE,3);
			case"fire"->this.getBlockState().setValue(BlockVoidLock.SOUTH_RUNE,4);
			case"taint"->this.getBlockState().setValue(BlockVoidLock.SOUTH_RUNE,5);
		}
		switch(runes[3]){
			case"vis"->this.getBlockState().setValue(BlockVoidLock.WEST_RUNE,0);
			case"air"->this.getBlockState().setValue(BlockVoidLock.WEST_RUNE,1);
			case"water"->this.getBlockState().setValue(BlockVoidLock.WEST_RUNE,2);
			case"earth"->this.getBlockState().setValue(BlockVoidLock.WEST_RUNE,3);
			case"fire"->this.getBlockState().setValue(BlockVoidLock.WEST_RUNE,4);
			case"taint"->this.getBlockState().setValue(BlockVoidLock.WEST_RUNE,5);
		}

		this.setChanged();
	}

    public boolean shouldRenderFace(Direction dir){
		return dir.equals(Direction.UP);
    }

	public static void tick(@NotNull Level level,@NotNull BlockPos pos,@NotNull BlockState ignoredState,@NotNull BlockEntity ignoredEnt){
		if(!level.isClientSide()){
			if(level.getBlockEntity(pos)!=null){
				soundDelay--;
				if(soundDelay==0){
					if(level.getBlockState(pos).getBlock() != PT2Blocks.VOID_CUBE.get())
						level.playSound(null,pos,PT2Sounds.Monolith.get(),SoundSource.BLOCKS,0.4F,1.0F);
					soundDelay = 450 + level.random.nextInt(150);
				}
			}
		}// is not CLIENTSIDE
	}
}
