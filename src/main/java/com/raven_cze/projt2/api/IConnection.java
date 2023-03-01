package com.raven_cze.projt2.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IConnection {
	double x = 0,y = 0,z = 0;

	boolean isConnectable(Direction direction);
	
	boolean isVisSource();
	boolean isVisConduit();
	
	float[] subtractVis(float visAmount);
	float getPureVis();
	float getTaintedVis();
	float getMaxVis();
	
	int getVisSuction(Direction dir);//			(HelperLocation)
	int getTaintSuction(Direction dir);//		(HelperLocation)
	int getSuction(Direction dir);//				(HelperLocation)
	
	void setPureVis(float pureAmount);
	void setTaintedVis(float taintAmount);
	void setVisSuction(int suctionAmount);
	void setTaintSuction(int suctionAmount);
	void setSuction(int suctionAmount);

	default BlockEntity getConnectableTile(Level level,Direction direction){
		BlockEntity te=level.getBlockEntity(new BlockPos(this.x,this.y,this.z));
		if(te instanceof IConnection && ((IConnection)te).isConnectable(direction.getClockWise()) )return te;
		return null;
	}
}
