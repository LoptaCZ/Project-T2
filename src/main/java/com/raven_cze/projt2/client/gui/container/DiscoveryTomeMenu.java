package com.raven_cze.projt2.client.gui.container;

import com.raven_cze.projt2.common.content.PT2Menus;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused"})
public class DiscoveryTomeMenu extends AbstractContainerMenu{
    private static Level level = null;
    private static Block block = null;
    private static BlockPos blockPos = null;
    private static Inventory plyInv = null;
    private boolean initialized;
    private Object[] failedEntries;
    public DiscoveryTomeMenu(int id,Inventory inv,FriendlyByteBuf extraData){
        super(PT2Menus.MENU_DISCOVERY_TOME.get(),id);
        checkContainerSize(inv,1);
        level=inv.player.level;
        plyInv=inv;
        if(extraData!=null){
            //  ==================================  //
            //  Player Position | Block Position    //
            //  Clicked Block [ as ItemStack ]      //
            //  Clicked Block Registry Name         //
            //  ==================================  //
            blockPos=extraData.readBlockPos();
            if(! (level.getBlockState(extraData.readBlockPos()).getBlock()instanceof AirBlock) ){
                block=level.getBlockState(extraData.readBlockPos()).getBlock();
                System.out.println(extraData.readComponent());
            }
        }
        //  Slots & other shit

    }

    public static Object create(int id,Inventory inv,ItemStack stack){
        FriendlyByteBuf extraData=new FriendlyByteBuf(Unpooled.buffer());
        extraData.writeBlockPos(BlockPos.ZERO);
        extraData.writeItemStack(stack,true);
        extraData.writeComponent(new TranslatableComponent("research.projt2.first"));
        return new DiscoveryTomeMenu(id,inv,extraData);
    }

    @Override
    public boolean stillValid(@NotNull Player player){
        return stillValid(ContainerLevelAccess.create(level,blockPos),player,block);
    }
}
