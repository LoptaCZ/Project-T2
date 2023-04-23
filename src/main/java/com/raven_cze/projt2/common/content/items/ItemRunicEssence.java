package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.tiles.TileSeal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class ItemRunicEssence extends PT2Item{
    public ItemRunicEssence(Properties properties){super(properties);}

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext ctx){
        float addPitch=0;
        BlockEntity be=ctx.getLevel().getBlockEntity(ctx.getClickedPos());
        if(be instanceof TileSeal seal){
            boolean added=false;
            for(int runa=0;runa<3;runa++){
                if(seal.runes[runa]==-1 && ctx.getItemInHand().getItem().getRegistryName()!=null){
                    seal.runes[runa]=getRuneByte(ctx.getItemInHand().getItem().getRegistryName());

                    added=true;
                    addPitch=seal.runes[runa];
                    seal.delay=60;

                    //  Add Ticker for Portal & ChunkLoader
                    //  if(seal.runes[0]==0 && seal.runes[1]==1){}
                    //  if(seal.runes[0]==0 && seal.runes[1]==3){}

                    if(!ctx.getLevel().isClientSide())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.DEBUG,seal.runes[runa]);
                    break;
                }
            }
            if(added){
                ctx.getLevel().playSound(ctx.getPlayer(),ctx.getClickedPos(),PT2Sounds.RuneSet.get(),SoundSource.BLOCKS,0.5F,1.2F-addPitch*0.075F);
                ctx.getItemInHand().shrink(1);
            }
        }
        return super.useOn(ctx);
    }
    private byte getRuneByte(ResourceLocation registryName){
        String name=registryName.getPath();
        byte value;
        switch(name){
            default->value=-1;
            case"runic_magic"->value=0;
            case"runic_air"->value=1;
            case"runic_water"->value=2;
            case"runic_earth"->value=3;
            case"runic_fire"->value=4;
            case"runic_dark"->value=5;
        }
        return value;
    }
}
