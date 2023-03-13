package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.world.inventory.DiscoveryTomeMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.client.resources.language.I18n;
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
import net.minecraft.world.entity.HumanoidArm;
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
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
						openGUI(player,level,pos);
			}else{
				if(level.getBlockState(pos).is(Blocks.LECTERN))
					return LecternBlock.tryPlaceBook(player,level,pos,state,new ItemStack(this))? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
				else
					super.use(level,player,context.getHand());
			}
		}
		return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
	}

	private void openGUI(@NotNull Player entity,@NotNull Level level,@Nullable BlockPos clickedBlock){
		if(entity instanceof ServerPlayer ply){
			NetworkHooks.openGui(ply, new MenuProvider() {
				@Override
				public @NotNull Component getDisplayName(){return new TextComponent(I18n.get("item.projt2.discovery_tome"));}
				@Override
				public @NotNull AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player){
					FriendlyByteBuf packetBuff=new FriendlyByteBuf(Unpooled.buffer());
					BlockPos pos=ply.getOnPos();
					if(clickedBlock!=null)pos=clickedBlock;
					packetBuff.writeBlockPos(pos);
					packetBuff.writeByte(player.getMainArm()==HumanoidArm.RIGHT?0:1);
					return new DiscoveryTomeMenu(id,inv,packetBuff);
				}
			},buf->{
				buf.writeBlockPos(ply.getOnPos());
				buf.writeByte(ply.getMainArm()==HumanoidArm.RIGHT?0:1);
			});
		}
	}
}
