package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.common.content.world.inventory.VoidChestMenu;
import com.raven_cze.projt2.common.content.PT2Tiles;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.EmptyByteBuf;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TileVoidChest extends RandomizableContainerBlockEntity{
    public boolean hasInterface;
    private final NonNullList<ItemStack>items=NonNullList.withSize(72,ItemStack.EMPTY);
    public TileVoidChest(BlockPos pWorldPosition, BlockState pBlockState){
        super(PT2Tiles.TILE_VOID_CHEST.get(),pWorldPosition,pBlockState);
        this.hasInterface=false;
    }

    @Override
    public void load(@NotNull CompoundTag tag){
        super.load(tag);
        ListTag list=tag.getList("Items",Tag.TAG_LIST);
        for(int slot0=0;slot0<list.size();slot0++){
            CompoundTag com=list.getCompound(slot0);
            byte slot=com.getByte("SlotVoidChest");

            //  New way of creating custom ItemStacks?
            //  trough NBT ... at least for me
            CompoundTag custom=new CompoundTag();
            custom.putString("id",com.getString("id"));
            custom.putInt("Count",com.getInt("Count"));

            if(com.get("tag")!=null)custom.put("tag",Objects.requireNonNull(com.get("tag")));

            this.getItems().get(slot).setTag(custom);

            if(this.level!=null)
                if(this.level.getBlockEntity(this.worldPosition.above())instanceof TileVoidInterface)
                    hasInterface=true;
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag){
        super.saveAdditional(tag);
        ListTag list=new ListTag();
        for(int slot=0;slot<this.getItems().size();slot++){
            CompoundTag com=new CompoundTag();
            com.putByte("SlotVoidChest",(byte)slot);
            this.getItems().get(slot).setTag(com);
            list.add(0,com);
        }
        tag.put("Items",list);
    }

    @Override
    protected @NotNull Component getDefaultName(){return new TextComponent(I18n.get("block.projt2.void_chest"));}

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int id,@NotNull Inventory inv){
        FriendlyByteBuf data=new FriendlyByteBuf(new EmptyByteBuf(ByteBufAllocator.DEFAULT));
        data.writeBlockPos(this.worldPosition);
        return hasInterface?VoidChestMenu.create(id,inv,data,this):VoidChestMenu.createNoInterface(id,inv,data,this);
    }

    @Override
    protected @NotNull NonNullList<ItemStack>getItems(){return this.items;}

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack>stacks){

    }

    @Override
    public int getContainerSize(){return 72;}
}
