package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.blocks.references.PT2Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class BlockEldritch extends PT2Block{
    public BlockEldritch(Properties properties,boolean isMonolith){
        super(properties);
        properties.strength(isMonolith?6000000.0F:10.0F,isMonolith?6000000.0F:10.0F);
        //properties.lootFrom(()->new BlockEldritch(properties,false) );
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(PT2Blocks.ELDRITCH.get());
    }
}
