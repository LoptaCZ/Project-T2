package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.common.content.entities.Singularity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class ItemSingularity extends PT2Item{
	public ItemSingularity(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack>use(Level lvl,Player ply,InteractionHand hand) {
		lvl.addFreshEntity(new Singularity(lvl,ply));
		ply.getItemInHand(hand).shrink(1);
		return InteractionResultHolder.consume(ply.getItemInHand(hand));
	}

}
