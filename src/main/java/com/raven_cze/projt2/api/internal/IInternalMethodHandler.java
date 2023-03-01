package com.raven_cze.projt2.api.internal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IInternalMethodHandler{
    boolean progressResearch(Player paramEntityPlayer, String paramString);
    boolean completeResearch(Player paramEntityPlayer, String paramString);
    boolean doesPlayerHaveRequisites(Player paramEntityPlayer, String paramString);
    float drainVis(Level paramWorld, BlockPos paramBlockPos, float paramFloat, boolean paramBoolean);
    float drainFlux(Level paramWorld, BlockPos paramBlockPos, float paramFloat, boolean paramBoolean);
    void addVis(Level paramWorld, BlockPos paramBlockPos, float paramFloat);
    void addFlux(Level paramWorld, BlockPos paramBlockPos, float paramFloat, boolean paramBoolean);
    float getTotalAura(Level paramWorld, BlockPos paramBlockPos);
    float getVis(Level paramWorld, BlockPos paramBlockPos);
    float getFlux(Level paramWorld, BlockPos paramBlockPos);
    int getAuraBase(Level paramWorld, BlockPos paramBlockPos);
    boolean shouldPreserveAura(Level paramWorld, Player paramEntityPlayer, BlockPos paramBlockPos);
}
