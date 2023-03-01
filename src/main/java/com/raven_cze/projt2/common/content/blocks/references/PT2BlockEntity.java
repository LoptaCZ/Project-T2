package com.raven_cze.projt2.common.content.blocks.references;

import com.raven_cze.projt2.common.content.blocks.references.PT2BlockInterfaces.BlockstateProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings({"unused"})
public abstract class PT2BlockEntity extends BlockEntity implements BlockstateProvider{
	private boolean isUnloaded=false;
	public BlockState oldBlock=Blocks.AIR.defaultBlockState();
	
	public PT2BlockEntity(BlockEntityType<?>entType,BlockPos pos,BlockState state){super(entType,pos,state);}
	
	@Override
	public void load(@NotNull CompoundTag nbtIN){
		super.load(nbtIN);
		this.readCustomNBT(nbtIN,false);
	}
	public abstract void readCustomNBT(CompoundTag nbtIN,boolean descPacket);
	
	@Override
	protected void saveAdditional(@NotNull CompoundTag nbtOUT){
		super.saveAdditional(nbtOUT);
		this.writeCustomNBT(nbtOUT,false);
	}
	public abstract void writeCustomNBT(CompoundTag nbtIN,boolean descPacket);
	
	//TODO ClientboundBlockEntityDataPacket getUpdatePacket()
	//TODO void onDataPacket(Connection net,ClientboundBlockEntityDataPacket packet)
	
	@Override
	public void handleUpdateTag(CompoundTag tag){this.readCustomNBT(tag,true);}
	
	@Override
	public @NotNull CompoundTag getUpdateTag(){
		CompoundTag nbt=super.getUpdateTag();
		writeCustomNBT(nbt,true);
		return nbt;
	}
	
	public void receiveMessageFromClient(CompoundTag msg){}
	public void receiveMessageFromServer(CompoundTag msg){}
	public void onEntityCollision(Level level,Entity entity){}
	
	@Override
	public boolean triggerEvent(int id,int type){
		if(id==0||id==255){
			markContainingBlockForUpdate(null);
			return true;
		}
		else if(id==254){
			BlockState state=Objects.requireNonNull(level).getBlockState(worldPosition);
			level.sendBlockUpdated(worldPosition,state,state,3);
			return true;
		}
		return super.triggerEvent(id,type);
	}
	
	public void markContainingBlockForUpdate(@Nullable BlockState newState){if(this.level!=null)markBlockForUpdate(getBlockPos(),newState);}

	public void markBlockForUpdate(BlockPos pos,@Nullable BlockState newState){
		BlockState state=Objects.requireNonNull(level).getBlockState(pos);
		if(newState==null)newState=state;
		level.sendBlockUpdated(pos,state,newState,3);
		level.updateNeighborsAt(pos,newState.getBlock());
	}
	
	@Override
	public void onLoad(){
		super.onLoad();
		isUnloaded=false;
	}
	
	@Override
	public void onChunkUnloaded(){
		super.onChunkUnloaded();
		isUnloaded=true;
	}
	
	@Nonnull
	public Level getLevelNonnull(){return Objects.requireNonNull(super.getLevel());}
	
	
}
