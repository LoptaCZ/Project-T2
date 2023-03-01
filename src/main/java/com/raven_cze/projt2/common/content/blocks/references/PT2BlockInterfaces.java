package com.raven_cze.projt2.common.content.blocks.references;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface PT2BlockInterfaces{
	
	public interface IRedstoneOutput{
		default int getWeakRSOutput(Direction side){
			return getStrongRSOutput(side);
		}
		int getStrongRSOutput(Direction side);
		boolean canConnectSide(Direction side);
	}
	
	public interface BlockstateProvider{
		BlockState getState();
		void setState(BlockState newState);
	}

    public interface SoundBE{

    }
}
