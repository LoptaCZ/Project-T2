package com.raven_cze.projt2.api.internal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DummyInternalMethodHandler implements IInternalMethodHandler{
    @Override
    public boolean progressResearch(Player paramEntityPlayer, String paramString) {
        return false;
    }

    @Override
    public boolean completeResearch(Player paramEntityPlayer, String paramString) {
        return false;
    }

    @Override
    public boolean doesPlayerHaveRequisites(Player paramEntityPlayer, String paramString) {
        return false;
    }

    @Override
    public float drainVis(Level paramWorld, BlockPos paramBlockPos, float paramFloat, boolean paramBoolean) {
        return 0;
    }

    @Override
    public float drainFlux(Level paramWorld, BlockPos paramBlockPos, float paramFloat, boolean paramBoolean) {
        return 0;
    }

    @Override
    public void addVis(Level paramWorld, BlockPos paramBlockPos, float paramFloat) {

    }

    @Override
    public void addFlux(Level paramWorld, BlockPos paramBlockPos, float paramFloat, boolean paramBoolean) {

    }

    @Override
    public float getTotalAura(Level paramWorld, BlockPos paramBlockPos) {
        return 0.0F;
    }

    @Override
    public float getVis(Level paramWorld, BlockPos paramBlockPos) {
        return 0.0F;
    }

    @Override
    public float getFlux(Level paramWorld, BlockPos paramBlockPos) {
        return 0.0F;
    }

    @Override
    public int getAuraBase(Level paramWorld, BlockPos paramBlockPos) {
        return 0;
    }

    @Override
    public boolean shouldPreserveAura(Level paramWorld, Player paramEntityPlayer, BlockPos paramBlockPos) {
        return false;
    }
}
