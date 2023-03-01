package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.common.content.Research;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ItemDiscovery extends PT2Item{
	private boolean hasName;
	private byte category;
	private String project;
	public ItemDiscovery(Properties properties){
		super(properties);
		this.category=0;
		this.project="null";
		this.hasName=false;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack,@Nullable Level pLevel,@NotNull List<Component>tooltip,@NotNull TooltipFlag adv){
		if(stack.getTag()!=null){
			tooltip.add(new TranslatableComponent("item.projt2.alumentum"));
		}
	}

	@Override
	public @NotNull Optional<TooltipComponent>getTooltipImage(@NotNull ItemStack stack){
		CompoundTag tag=stack.getTag();
		if(tag!=null && tag.contains("Research")){
			return Optional.of(new ItemTheory.TheoryTooltip(tag.getString("Result")));
		}
		return Optional.empty();
	}

	@Override
	public @NotNull Component getName(@NotNull ItemStack stack){
		TranslatableComponent text=new TranslatableComponent("item.projt2.discovery");
		if(!stack.equals(ItemStack.EMPTY) && !hasName){
			//text.append(stack.getDisplayName());
		}
		else text.append(new TranslatableComponent("itemGroup.projt2"));
		return text.append("NULL");
	}

	public void setCategory(byte category){this.category=category;}
	public void setProject(String name){this.project=name;}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack,UseOnContext context){
		if(context.getPlayer()!=null){
			Player ply= context.getPlayer();
			CompoundTag tag=ply.getPersistentData();

			if(tag.contains("projt2")){
				tag=tag.getCompound("projt2");
				if(Research.validResearch(this.project)) {
					if(tag.getList("unlockedResearch",Tag.TAG_LIST).isEmpty())
						ply.displayClientMessage(new TranslatableComponent("research.projt2.first"), false);

					if(tag.getList("unlockedResearch",Tag.TAG_LIST).contains(StringTag.valueOf(this.project)))
						ply.displayClientMessage(new TranslatableComponent("research.projt2.have"), true);
					else{
						tag.getList("unlockedResearch",Tag.TAG_LIST).add(StringTag.valueOf(this.project));
						TranslatableComponent text=new TranslatableComponent("research.projt2.learn");
						ply.displayClientMessage(text.append(this.project),true);
					}
				}else ply.displayClientMessage(new TranslatableComponent("research.projt2.fail"),true);
			}
		}

		return super.onItemUseFirst(stack, context);
	}
}
