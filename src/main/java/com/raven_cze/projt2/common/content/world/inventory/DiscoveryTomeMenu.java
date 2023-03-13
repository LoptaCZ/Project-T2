package com.raven_cze.projt2.common.content.world.inventory;

import com.raven_cze.projt2.common.content.PT2Menus;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class DiscoveryTomeMenu extends AbstractContainerMenu implements Supplier<Map<Integer,Slot>>{
    public final static HashMap<String,Object>guiState=new HashMap<>();
    public final Level level;
    public final Player ent;
    public int x,y,z;
    private final IItemHandler internal;
    private final Map<Integer,Slot>customSlots=new HashMap<>();
    private final boolean bound=false;
    public DiscoveryTomeMenu(int ID,Inventory inv,FriendlyByteBuf extra){
        super(PT2Menus.MENU_DISCOVERY_TOME.get(),ID);
        this.ent=inv.player;
        this.level=inv.player.level;
        this.internal=new ItemStackHandler(0);
        BlockPos pos=null;
        if(extra!=null){
            pos=extra.readBlockPos();
            this.x=pos.getX();
            this.y=pos.getY();
            this.z=pos.getZ();
        }
        if(pos!=null){
            //Do Nothing
        }
    }
    @Override
    public boolean stillValid(@NotNull Player ply){return true;}

    @Override
    public void removed(@NotNull Player ply){super.removed(ply);}

    @Override
    public Map<Integer,Slot>get(){return customSlots;}
}
