package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.api.IUpgradable;
import com.raven_cze.projt2.api.TileVisUser;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileEnchanterAdvanced extends TileVisUser implements IUpgradable,MenuProvider{
    public TileEnchanterAdvanced(BlockPos pos, BlockState state) {
        super(null,pos,state);
    }

    @Override
    public boolean canAcceptUpgrade(byte parmByte) {
        return false;
    }

    @Override
    public boolean hasUpgrade(byte paramByte) {
        return false;
    }

    @Override
    public boolean setUpgrade(byte paramByte) {
        return false;
    }

    @Override
    public boolean clearUpgrade(byte paramByte) {
        return false;
    }

    @Override
    public int getUpgradeLimit() {
        return 0;
    }

    @Override
    public byte[] getUpgrades() {
        return new byte[0];
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return null;
    }
}
