package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.api.IConnection;
import com.raven_cze.projt2.common.content.PT2Items;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;

public class ItemDetector extends PT2Item{
	int type;
	public ItemDetector(Properties properties) {
		super(properties);
	}
	
	public int getType(){return type;}
	public Item setType(int type){this.type=type;return this;}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack,@Nonnull UseOnContext context){
		Level level=context.getLevel();
		Player player=context.getPlayer();
		BlockPos pos=context.getClickedPos();
		BlockEntity ent=level.getBlockEntity(pos);

		if(ent instanceof IConnection ICe && !level.isClientSide){
			if(stack.getItem()==PT2Items.TAINT_DETECTOR.get()){
				//	Print to Chat [ Detected X Taint ]
				(Minecraft.getInstance()).gui.handleChat(ChatType.CHAT,new TextComponent(String.format("Detected %f Taint.",ICe.getTaintedVis())),player.getUUID());
				level.playSound(player,pos,SoundEvents.NOTE_BLOCK_HARP,SoundSource.BLOCKS,0.8F,1.0F);
				if(ICe.getTaintSuction(null)>0)
					(Minecraft.getInstance()).gui.handleChat(ChatType.CHAT,new TextComponent(ICe.getTaintSuction(null)+" Taint TCB"),player.getUUID());
				return InteractionResult.SUCCESS;
			}
			if(stack.getItem()==PT2Items.VIS_DETECTOR.get()){
				//	Print to Chat [ Detected X Vis ]
				(Minecraft.getInstance()).gui.handleChat(ChatType.CHAT,new TextComponent(String.format("Detected %f Vis.",ICe.getPureVis())),player.getUUID());
				level.playSound(player,pos,SoundEvents.NOTE_BLOCK_HARP,SoundSource.BLOCKS,0.8F,1.0F);
				if(ICe.getTaintSuction(null)>0)
					(Minecraft.getInstance()).gui.handleChat(ChatType.CHAT,new TextComponent(ICe.getVisSuction(null)+" Vis TCB"),player.getUUID());
				return InteractionResult.SUCCESS;
			}
			if(stack.getItem()==PT2Items.THAUMOMETER.get()){
				int cap=Math.round(Math.round(ICe.getPureVis()+ICe.getTaintedVis())/ICe.getMaxVis()*100.0F);
				int capP=Math.round(Math.round(ICe.getPureVis())/ICe.getMaxVis()*100.0F);
				int capT=Math.round(Math.round(ICe.getTaintedVis())/ICe.getMaxVis()*100.0F);
				//	Print to Chat [ Detected A Vis (B)% and C Taint (D)% ]
				//	Print to Chat [ The Object is at X% capacity. ]
				(Minecraft.getInstance()).gui.handleChat(ChatType.CHAT,new TextComponent("Detected "+Math.round(ICe.getPureVis())+" Vis ("+capP+"%) and "+Math.round(ICe.getTaintedVis())+" Taint("+capT+"%)."),player.getUUID());
				(Minecraft.getInstance()).gui.handleChat(ChatType.CHAT,new TextComponent(String.format("The object is at "+cap+"% capacity")),player.getUUID());
				level.playSound(player,pos,SoundEvents.NOTE_BLOCK_HARP,SoundSource.BLOCKS,0.8F,1.0F+1.0F*cap/100.0F);
				if(ICe.getTaintSuction(null)>0 || ICe.getVisSuction(null)>0)
					(Minecraft.getInstance()).gui.handleChat(ChatType.CHAT,new TextComponent(ICe.getVisSuction(null)+" Vis TCB, "+ICe.getTaintSuction(null)+" Taint TCB"),player.getUUID());
				return InteractionResult.SUCCESS;
			}
		}
		return super.onItemUseFirst(stack, context);
	}
}
