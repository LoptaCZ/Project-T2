package com.raven_cze.projt2.common.content.world.inventory;

import com.raven_cze.projt2.common.content.PT2Menus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GeneratorMenu extends AbstractContainerMenu implements Supplier<Map<Integer,Slot>>{

    public final Level level;
    public final Player ent;
    public int x,y,z;
    private final Map<Integer,Slot>customSlots=new HashMap<>(2);
    public GeneratorMenu(int ID, Inventory inv, FriendlyByteBuf extra) {
        super(PT2Menus.MENU_GENERATOR.get(),ID);
        this.ent=inv.player;
        this.level=inv.player.level;
        IItemHandler internal = new ItemStackHandler(2);
        BlockPos pos=null;
        if(extra!=null){
            pos=extra.readBlockPos();
            this.x=pos.getX();
            this.y=pos.getY();
            this.z=pos.getZ();
        }
        if(pos!=null){
            //Do Nothing
            for(int slot = 0; slot< internal.getSlots(); slot++){
                customSlots.put(slot,new Slot(inv,slot,0,(slot*10)));
            }
        }
    }

    @Override
    public Map<Integer,Slot>get(){return customSlots;}

    @Override
    public boolean stillValid(@NotNull Player player){return true;}
}
