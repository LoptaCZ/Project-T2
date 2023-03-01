package com.raven_cze.projt2.common.content.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ItemTheory extends PT2Item{
	public ItemTheory(Properties properties){
		super(properties);
	}

	@Override
	public @NotNull Optional<TooltipComponent>getTooltipImage(@NotNull ItemStack stack){
		CompoundTag tag=stack.getTagElement("Research");
		if(tag!=null && tag.contains("Result")){
			return Optional.of(new TheoryTooltip(tag.getString("Result")));
		}
		return Optional.empty();
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack,@Nullable Level level,@NotNull List<Component>components,@NotNull TooltipFlag advanced){
		super.appendHoverText(stack,level,components,advanced);
	}

	public record TheoryTooltip(String packed)implements TooltipComponent{

	}
}
