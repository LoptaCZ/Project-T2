package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.client.gui.container.CrystalBallMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemCrystalBall extends PT2Item implements MenuProvider {
	public ItemCrystalBall(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull Component getDisplayName(){return new TranslatableComponent("item.projt2.crystalball");}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
		FriendlyByteBuf FBB=new FriendlyByteBuf(Unpooled.buffer());
		return new CrystalBallMenu(pContainerId,pInventory,FBB);
	}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context){
		if(!context.getLevel().isClientSide()){
			Player ply=context.getPlayer();
			if(ply!=null)
				createMenu(ply.inventoryMenu.containerId,ply.getInventory(),ply);
			else{
				ply=Minecraft.getInstance().player;
				if(ply!=null) createMenu(0,new Inventory(ply),ply);
			}
		}//	SERVER SIDE
		return super.onItemUseFirst(stack, context);
	}
}
