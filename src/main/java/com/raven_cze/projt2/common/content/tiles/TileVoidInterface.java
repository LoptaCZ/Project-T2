package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.common.content.world.inventory.VoidChestMenu;
import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.blocks.BlockVoidInterface;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class TileVoidInterface extends BlockEntity implements MenuProvider{
    public byte network=0;  //  Can be even a String | Maybe later
    public ArrayList<BlockPos>links=new ArrayList<>();
    public boolean linked=false;
    public int current=0;

    public TileVoidInterface(BlockPos pWorldPosition, BlockState pBlockState) {
        super(PT2Tiles.TILE_VOID_INTERFACE.get(),pWorldPosition,pBlockState);
    }

    @Override
    public @NotNull Component getDisplayName(){
        return new TextComponent(I18n.get("block.projt2.void_interface"));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId,@NotNull Inventory pInventory,@NotNull Player pPlayer){
        this.network=this.getBlockState().getValue(BlockVoidInterface.FREQUENCY).byteValue();
        if (this.level != null) {
            return VoidChestMenu.create(pContainerId,pInventory,null);
        }
        return null;
    }

    private@NotNull NonNullList<ItemStack>getInventory(BlockPos pos){
        BlockEntity te= null;
        if (this.level != null) {
            te = this.level.getBlockEntity(pos);
        }
        if(te instanceof TileVoidChest)
            return((TileVoidChest)te).getItems();
        return new NonNullList<ItemStack>(Lists.newArrayList(),ItemStack.EMPTY){};
    }

    public ItemStack getStackInSlot(int slotIndex){
        try{
            return getInventory(this.links.get(this.current)).get(slotIndex);
        }catch(Exception e){return null;}
    }

    public ItemStack shrinkStackInSlot(int slotIndex,int count){
        ItemStack stack=getStackInSlot(slotIndex);
        stack.shrink(count);
        return stack;
    }
    public void setStackInSlot(int slotIndex,ItemStack stack){
        try{getInventory(this.links.get(this.current)).set(slotIndex%72,stack);}catch(Exception ignored){}
    }
}
