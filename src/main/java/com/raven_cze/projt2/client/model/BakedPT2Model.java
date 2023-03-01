package com.raven_cze.projt2.client.model;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.NotNull;

public abstract class BakedPT2Model implements BakedModel{
	@Nonnull
	@Override
	public abstract List<BakedQuad>getQuads(@Nullable BlockState state,@Nullable Direction side,@Nullable Random rand,@Nullable IModelData extraData);
	@Nonnull
	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, @NotNull Random rand){
		return getQuads(state,side,rand,EmptyModelData.INSTANCE);
	}
	@Override
	public boolean usesBlockLight(){return true;}
}
