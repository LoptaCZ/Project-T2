package com.raven_cze.projt2.common.content.world.inventory;

import com.raven_cze.projt2.client.gui.slot.VoidInterface;
import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.PT2Menus;
import com.raven_cze.projt2.common.content.tiles.TileVoidChest;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class VoidChestMenu extends AbstractContainerMenu{
    private final Container container;
    private final int containerRows;
    private final TileVoidChest chest;

    public static VoidChestMenu create(int id,Inventory inv,FriendlyByteBuf data){
        return new VoidChestMenu(id,inv,inv.player.level.getBlockEntity(data.readBlockPos()),new SimpleContainerData(72));
    }
    public static VoidChestMenu create(int id,Inventory inv,FriendlyByteBuf data,Container container){
        return new VoidChestMenu(id,inv,inv.player.level.getBlockEntity(data.readBlockPos()),(ContainerData)container);
    }

    public static VoidChestMenu createNoInterface(int id,Inventory inv,FriendlyByteBuf data){
        return new VoidChestMenu(id,inv,inv.player.level.getBlockEntity(data.readBlockPos()),new SimpleContainerData(72));
    }
    public static VoidChestMenu createNoInterface(int id,Inventory inv,FriendlyByteBuf data,Container container){
        return new VoidChestMenu(id,inv,inv.player.level.getBlockEntity(data.readBlockPos()),(ContainerData)container);
    }

    public VoidChestMenu(int id,Inventory inv,BlockEntity te,ContainerData data){
        super(PT2Menus.MENU_VOID_CHEST.get(),id);
        checkContainerSize(inv,72);
        this.container=inv;
        this.containerRows=8;
        this.chest=(TileVoidChest)te;
        int i = (this.containerRows - 4) * 18;

        //Void Chest Inventory
        for(int j = 0; j < this.containerRows; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new VoidInterface(container, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        //  Player Inventory
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(container, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }
        //  Player Hotbar
        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(container, i1, 8 + i1 * 18, 161 + i));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer){
        Level level=chest.getLevel();
        if(level!=null)
            return stillValid(ContainerLevelAccess.create(level,chest.getBlockPos()),pPlayer,PT2Blocks.VOID_CHEST.get());

        return this.container.stillValid(pPlayer);
    }

    public int getRowCount(){return this.containerRows;}

}
