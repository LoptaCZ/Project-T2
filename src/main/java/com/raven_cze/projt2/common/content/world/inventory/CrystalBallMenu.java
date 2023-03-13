package com.raven_cze.projt2.common.content.world.inventory;

import com.raven_cze.projt2.common.content.PT2Menus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public class CrystalBallMenu extends AbstractContainerMenu{
    public CrystalBallMenu(int id, Inventory inv, FriendlyByteBuf buf){
        this(id,inv,InteractionHand.MAIN_HAND,1);
    }
    public CrystalBallMenu(int id,Inventory plyInv,InteractionHand hand,int selected){
        super(PT2Menus.MENU_CRYSTALBALL.get(),id);
    }

    @Override
    public boolean stillValid(@NotNull Player player){return false;}

}
