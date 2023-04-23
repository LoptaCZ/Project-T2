package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.api.IUpgradable;
import com.raven_cze.projt2.api.TileVisUser;
import com.raven_cze.projt2.common.content.PT2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileInfuser extends TileVisUser implements IUpgradable{
    public TileInfuser(BlockPos pos, BlockState state) {
        super(PT2Tiles.TILE_INFUSER.get(),pos,state);
    }

    @NotNull
    @Override
    public<T>LazyOptional<T>getCapability(@NotNull Capability<T>cap,@Nullable Direction side){
        return super.getCapability(cap,side);
    }

    @Override
    public boolean canAcceptUpgrade(byte upgradeID){return false;}

    @Override
    public boolean hasUpgrade(byte upgradeID){return false;}

    @Override
    public boolean setUpgrade(byte upgradeID){return false;}

    @Override
    public boolean clearUpgrade(byte index){return false;}

    @Override
    public int getUpgradeLimit(){return 0;}

    @Override
    public byte[] getUpgrades(){return new byte[0];}
}
