package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.api.IConnection;
import com.raven_cze.projt2.api.ITickableBlockEntity;
import com.raven_cze.projt2.common.content.PT2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings({"unused"})
public class TileCrucible extends BlockEntity implements IConnection,ITickableBlockEntity{
	public int smeltDelay;
	public float pureVis;
	public float taintedVis;
	public float maxVis;
	public int face=3;
	private short type;
	public float conversion;
	public float speed;
	public int bellows;
	
	public int soundDelay=25;
	public float pPure;
	public float pTaint;
	public int wait;
	public boolean updateNextPeriod;
	public boolean isPowering;

	public TileCrucible(BlockPos pos,BlockState state){
		super(PT2Tiles.TILE_CRUCIBLE.get(),pos,state);
		this.isPowering=false;
		this.pureVis=0.0F;
		this.taintedVis=0.0F;
		this.bellows=0;
	}
	
	@Override
	public void load(@NotNull CompoundTag compound){
		super.load(compound);
		this.pureVis=compound.getFloat("pureVis");
		this.taintedVis=compound.getFloat("taintedVis");
		this.type=compound.getShort("type");
		setTier(this.type);
		this.bellows=compound.getInt("bellows");
	}
	
	@Override
	public void saveAdditional(@NotNull CompoundTag compound){
		super.saveAdditional(compound);
		compound.putFloat("pureVis",this.pureVis);
		compound.putFloat("taintedVis",this.taintedVis);
		compound.putShort("type",this.type);
		compound.putInt("bellows",this.bellows);
	}
	
	public void setTier(short t){
		t=(short)Mth.clamp(t,1,4);
		switch(t){
			case 1 -> {        //	Default
				this.maxVis = 500.0F;
				this.conversion = 0.5F;
				this.speed = 0.25F;
				this.type = 1;
			}
			case 2 -> {        //	Eye
				this.maxVis = 600.0F;
				this.conversion = 0.6F;
				this.speed = 0.5F;
				this.type = 2;
			}
			case 3 -> {        //	Thaumium
				this.maxVis = 750.0F;
				this.conversion = 0.7F;
				this.speed = 0.75F;
				this.type = 3;
			}
			case 4 -> {        //	Souls
				this.maxVis = 750.0F;
				this.conversion = 0.4F;
				this.speed = 0.75F;
				this.type = 4;
			}
		}
	}

	//TODO Tick Method
	@Override
	public void tickServer(){

	}
	@Override
	public void tickClient(){}

	private List<ItemEntity>getContents(){
		double x=this.worldPosition.getX();
		double y=this.worldPosition.getY();
		double z=this.worldPosition.getZ();
		if(this.level!=null){
			return this.level.getEntitiesOfClass(ItemEntity.class,new AABB(x,y,z,x+1,y+1,z+1));
		}return null;
	}
	public InteractionResult ejectContents(Player player){
		return InteractionResult.FAIL;
	}
	private boolean canCook(ItemStack item){
		return false;
	}

	@Override
	public boolean isConnectable(Direction direction){
		return direction!=Direction.UP;
	}
	@Override
	public boolean isVisSource(){return true;}
	@Override
	public boolean isVisConduit(){return false;}
	@Override
	public float[] subtractVis(float amount){
		float pureAmount = amount / 2.0F;
	    float taintAmount = amount / 2.0F;
	    float[] result = { 0.0F, 0.0F };
	    if (amount < 0.001F)
	      return result; 
	    if (this.pureVis < pureAmount)
	      pureAmount = this.pureVis; 
	    if (this.taintedVis < taintAmount)
	      taintAmount = this.taintedVis; 
	    if (pureAmount < amount / 2.0F && taintAmount == amount / 2.0F) {
	      taintAmount = Math.min(amount - pureAmount, this.taintedVis);
	    } else if (taintAmount < amount / 2.0F && pureAmount == amount / 2.0F) {
	      pureAmount = Math.min(amount - taintAmount, this.pureVis);
	    } 
	    this.pureVis -= pureAmount;
	    this.taintedVis -= taintAmount;
	    result[0] = pureAmount;
	    result[1] = taintAmount;
	    return result;
	}
	@Override
	public float getPureVis(){return this.pureVis;}
	@Override
	public float getTaintedVis(){return this.taintedVis;}
	@Override
	public float getMaxVis(){return this.maxVis;}
	@Override
	public int getVisSuction(Direction dir){return 0;}
	@Override
	public int getTaintSuction(Direction dir){return 0;}
	@Override
	public int getSuction(Direction dir){return 0;}
	@Override
	public void setPureVis(float pureAmount){}
	@Override
	public void setTaintedVis(float taintAmount){}
	@Override
	public void setVisSuction(int suctionAmount){}

	@Override
	public void setTaintSuction(int suctionAmount){}

	@Override
	public void setSuction(int suctionAmount){}
}
