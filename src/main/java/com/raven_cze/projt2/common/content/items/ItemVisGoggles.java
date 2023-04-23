package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.client.overlay.VisDetector;
import com.raven_cze.projt2.common.content.world.aura.AuraChunk;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemVisGoggles extends PT2ArmorItem{
	public ItemVisGoggles(Properties properties){
		super(PT2ArmorItem.PT2ArmorMats.SPECIAL,EquipmentSlot.HEAD,properties);
	}
	@Override
	public @NotNull Rarity getRarity(@NotNull ItemStack stack){return Rarity.RARE;}

	@Override
	public void appendHoverText(@NotNull ItemStack stack,@Nullable Level level,@NotNull List<Component>components,@NotNull TooltipFlag advanced){
		AuraChunk ac=VisDetector.aura;
		if(ac!=null){
			TextComponent visValue = new TextComponent("Vis: " + Math.round(stack.getBarColor()));
			TextComponent taintValue = new TextComponent("Taint: " + Math.round(ac.getTaint()));

			visValue.withStyle(ChatFormatting.LIGHT_PURPLE);
			taintValue.withStyle(ChatFormatting.DARK_PURPLE);

			components.add(visValue);
			components.add(taintValue);
		}
		super.appendHoverText(stack,level,components,advanced);
	}
}
