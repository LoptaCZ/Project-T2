package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.client.gui.container.DiscoveryTomeMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@SuppressWarnings({"unused"})
public class ItemDiscoveryTome extends PT2Item {
	private String lastOpen;
	private static boolean isOpen;

	public ItemDiscoveryTome(Properties properties){
		super(properties);
		this.lastOpen="intro";
		isOpen=false;
	}

    public static boolean isOpen(){return isOpen;}

    @Override
	public void onCraftedBy(@NotNull ItemStack stack,@NotNull Level level,@NotNull Player player){
		super.onCraftedBy(stack,level,player);
		CompoundTag tag=player.serializeNBT();
		this.lastOpen="intro";
		isOpen=false;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack>use(@NotNull Level level,@NotNull Player player,@NotNull InteractionHand hand){
		openGUI(player,level,null);
		return InteractionResultHolder.sidedSuccess(new ItemStack(this),level.isClientSide());
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext context){
		Level level=context.getLevel();
		BlockPos pos=context.getClickedPos();
		BlockState state=level.getBlockState(pos);
		Block block=state.getBlock();
		Player player=context.getPlayer();

		if (player != null){
			if(player.isCrouching()){
				if(block.getRegistryName()!=null)
					if(block.getRegistryName().getNamespace().equals(ProjectT2.MODID))
						openGUI(player,level,block.defaultBlockState());
			}else{
				if(level.getBlockState(pos).is(Blocks.LECTERN))
					return LecternBlock.tryPlaceBook(player,level,pos,state,new ItemStack(this))? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
				else
					super.use(level,player,context.getHand());
			}
		}
		return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
	}

	private void openGUI(@NotNull Player player,@NotNull Level level,@Nullable BlockState clickedBlock){
		if(player.getServer()!=null){
			ServerPlayer sPlayer = player.getServer().getPlayerList().getPlayer(player.getUUID());
			if(sPlayer!=null)
				if(sPlayer.getUUID()==player.getUUID()){
					try{
						isOpen=!isOpen;
						NetworkHooks.openGui(sPlayer,new MenuProvider(){
							@Override
							public @NotNull Component getDisplayName(){return new TextComponent("some.random.pt2.text.title");}

							@Override
							public @NotNull AbstractContainerMenu createMenu(int cID, @NotNull Inventory inventory, @NotNull Player pPlayer) {
								FriendlyByteBuf buff=new FriendlyByteBuf(Unpooled.buffer());

								if(clickedBlock!=null){
									if(clickedBlock.getBlock().getRegistryName()!=null && clickedBlock.getBlock().getRegistryName().getNamespace().equals(ProjectT2.MODID)){
										buff.writeBlockPos(sPlayer.eyeBlockPosition());
										buff.writeItemStack(new ItemStack(clickedBlock.getBlock()), true);
										buff.writeComponent(new TextComponent(""+clickedBlock.getBlock().getRegistryName()));
									}
								}else{
									buff.writeBlockPos(sPlayer.blockPosition());
									buff.writeItemStack(ItemStack.EMPTY,true);
									buff.writeComponent(new TextComponent(""+sPlayer.getTabListDisplayName()));
								}
								return new DiscoveryTomeMenu(cID,inventory,buff);
							}
						});
					}catch(Exception e){
						isOpen=false;
						System.out.println();
						e.printStackTrace();
					}
				}
		}
	}

	private static class ContainerProvider implements MenuProvider{
		private final ItemStack stack;

		private ContainerProvider(ItemStack pStack){
			this.stack=pStack;
		}

		@Override
		public AbstractContainerMenu createMenu(int id,@Nonnull Inventory inv,@Nonnull Player ply){
			return (AbstractContainerMenu)DiscoveryTomeMenu.create(id,inv,this.stack);
		}

		@Override
		public @NotNull Component getDisplayName(){return this.stack.getDisplayName();}
	}
}
