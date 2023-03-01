package com.raven_cze.projt2.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AuraHelper{
    public static float drainVis(Level level, BlockPos pos, float amount, boolean simulate){
        return PT2Api.internalMethods.drainVis(level,pos,amount,simulate);
    }
    public static float drainFlux(Level world, BlockPos pos, float amount, boolean simulate) {
        return PT2Api.internalMethods.drainFlux(world, pos, amount, simulate);
    }

    public static void addVis(Level world, BlockPos pos, float amount) {
        PT2Api.internalMethods.addVis(world, pos, amount);
    }

    public static float getVis(Level world, BlockPos pos) {
        return PT2Api.internalMethods.getVis(world, pos);
    }

    public static void polluteAura(Level world, BlockPos pos, float amount, boolean showEffect) {
        PT2Api.internalMethods.addFlux(world, pos, amount, showEffect);
    }

    public static float getFlux(Level world, BlockPos pos) {
        return PT2Api.internalMethods.getFlux(world, pos);
    }

    public static int getAuraBase(Level world, BlockPos pos) {
        return PT2Api.internalMethods.getAuraBase(world, pos);
    }

    public static boolean shouldPreserveAura(Level world, Player player, BlockPos pos) {
        return PT2Api.internalMethods.shouldPreserveAura(world, player, pos);
    }
}
