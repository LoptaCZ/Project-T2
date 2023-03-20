package com.raven_cze.projt2.common.content.blocks.references;

import com.raven_cze.projt2.common.content.PT2Blocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import org.jetbrains.annotations.NotNull;

public class PT2Leaves extends LeavesBlock{
    public PT2Leaves(Properties properties){
        super(properties);
    }
    @Override
    public int getLightBlock(@NotNull BlockState pState,@NotNull BlockGetter pLevel,@NotNull BlockPos pPos){return 1;}

    @OnlyIn(Dist.CLIENT)
    public static void blockColorLoad(ColorHandlerEvent.Block event){
        event.getBlockColors().register((state,world,pos,index)->( world!=null && pos!=null )? BiomeColors.getAverageFoliageColor(world,pos): FoliageColor.getDefaultColor(),PT2Blocks.GREATWOOD_LEAVES.get(),PT2Blocks.SILVERWOOD_LEAVES.get(),PT2Blocks.TAINT_LEAVES.get());
    }
    @OnlyIn(Dist.CLIENT)
    public static void itemColorLoad(ColorHandlerEvent.Item event){
        event.getItemColors().register((stack,index)->FoliageColor.getDefaultColor(),PT2Blocks.GREATWOOD_LEAVES.get(),PT2Blocks.SILVERWOOD_LEAVES.get(),PT2Blocks.TAINT_LEAVES.get());
    }
}
