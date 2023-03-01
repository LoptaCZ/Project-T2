package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.PT2Sounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({})
public class ItemDawnStone extends PT2Item{
	public ItemDawnStone(Properties properties){
		super(properties);
	}
	
	@Override
	public @NotNull InteractionResultHolder<ItemStack>use(@NotNull Level level, @NotNull Player ply, @NotNull InteractionHand hand){
		if(ProjectT2.dawnInc==0){
			ProjectT2.dawnInc=999;
			long var=level.dayTime()+24000L;
			ProjectT2.dawnDest=var+var%24000L;
			switch(hand){
				case MAIN_HAND:
					ply.getMainHandItem().shrink(1);
				case OFF_HAND:
					ply.getOffhandItem().shrink(1);
			}
			level.playSound(ply,ply.getOnPos(),PT2Sounds.Recover.get(),SoundSource.AMBIENT,1.0F,1.0F);
		}
		return super.use(level,ply,hand);
	}
}
