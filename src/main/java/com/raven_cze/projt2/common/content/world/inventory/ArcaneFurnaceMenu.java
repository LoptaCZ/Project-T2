package com.raven_cze.projt2.common.content.world.inventory;

import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.PT2Menus;
import com.raven_cze.projt2.common.content.tiles.TileArcaneFurnace;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ArcaneFurnaceMenu extends AbstractContainerMenu{
    private final TileArcaneFurnace tile;
    private final Level level;
    private final ContainerData data;
    public ArcaneFurnaceMenu(int id,Inventory inv,FriendlyByteBuf extra){
        this(id,inv,inv.player.level.getBlockEntity(extra.readBlockPos()),new SimpleContainerData(2));
    }
    public ArcaneFurnaceMenu(int id,Inventory inv,BlockEntity te,ContainerData data){
        super(PT2Menus.MENU_FURNACE.get(),id);
        checkContainerSize(inv,4);

        this.tile=(TileArcaneFurnace)te;
        this.level=inv.player.getLevel();
        this.data=data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler->{
            //      INPUT SLOTS
            this.addSlot(new SlotItemHandler(handler,0,34,40));
            this.addSlot(new SlotItemHandler(handler,1,57,18));
            this.addSlot(new SlotItemHandler(handler,2,103,18));
            this.addSlot(new SlotItemHandler(handler,3,80,60));
            this.addSlot(new SlotItemHandler(handler,4,80,60));
            this.addSlot(new SlotItemHandler(handler,5,80,60));
            this.addSlot(new SlotItemHandler(handler,6,80,60));
            this.addSlot(new SlotItemHandler(handler,7,80,60));
            this.addSlot(new SlotItemHandler(handler,8,80,60));
            //  OUTPUT SLOTS
            this.addSlot(new SlotItemHandler(handler,10,34,40));
            this.addSlot(new SlotItemHandler(handler,11,57,18));
            this.addSlot(new SlotItemHandler(handler,12,103,18));
            this.addSlot(new SlotItemHandler(handler,13,80,60));
            this.addSlot(new SlotItemHandler(handler,14,80,60));
            this.addSlot(new SlotItemHandler(handler,15,80,60));
            this.addSlot(new SlotItemHandler(handler,16,80,60));
            this.addSlot(new SlotItemHandler(handler,17,80,60));
            this.addSlot(new SlotItemHandler(handler,18,80,60));
            // FUEL SLOT?
            this.addSlot(new SlotItemHandler(handler,9,80,60));
        });

        addDataSlots(data);
    }

    public boolean isCrafting(){return data.get(0)>0;}

    @Override
    public boolean stillValid(@NotNull Player pPlayer){
        return stillValid(ContainerLevelAccess.create(level,tile.getBlockPos()),pPlayer,PT2Blocks.FURNACE.get());
    }
    private void addPlayerInventory(Inventory inv){
        for(int i=0;i<3;i++){
            for(int l=0;l<9;l++){
                this.addSlot(new Slot(inv,l+i*9+9,8+l*18,86+i*18) );
            }
        }
    }
    private void addPlayerHotbar(Inventory inv){
        for(int x=0;x<9;x++)
            this.addSlot(new Slot(inv,x,8+x*18,144));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player,int index){
        ItemStack stack=ItemStack.EMPTY;
        Slot slot=this.slots.get(index);
        ItemStack last=slot.getItem();

        if(index==9){// Fuel Slot
            if(!slot.hasItem()){
                if(stack.getBurnTime(null)>0){
                    slot.set(last);
                    return ItemStack.EMPTY;
                }
            }
        }else if(index >= 8){//Input
            return ItemStack.EMPTY;
        }else{// Output
            return ItemStack.EMPTY;
        }

        return stack;
    }
}
