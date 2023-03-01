package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.tiles.TileVoidKeyhole;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Explosion;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemCrystal extends PT2Item{

    public ItemCrystal(Properties properties){
        super(properties);
    }

    @Override
    public@NotNull InteractionResult useOn(@NotNull UseOnContext context){
        if(context.getLevel().getBlockState(context.getClickedPos()).hasBlockEntity()){
            if(context.getLevel().getBlockEntity(context.getClickedPos()) instanceof TileVoidKeyhole tvk){
                if(!tvk.placed){
                    String name=Objects.requireNonNull(context.getItemInHand().getItem().getRegistryName()).getPath();
                    if(name.contains(tvk.rune)){
                        tvk.placed=true;
                        context.getItemInHand().shrink(1);
                        context.getLevel().playSound(context.getPlayer(),context.getClickedPos(),PT2Sounds.Place.get(),SoundSource.BLOCKS,1.0F,1.0F);
                        tvk.setChanged();
                        return InteractionResult.SUCCESS;
                    }else{
                        int x=context.getClickedPos().getX();
                        int y=context.getClickedPos().getY();
                        int z=context.getClickedPos().getZ();
                        context.getLevel().explode(null,x+0.5F,y+1.5F,z+0.5F,1.0F,Explosion.BlockInteraction.NONE);
                        return InteractionResult.PASS;
                    }
                }
            }
        }
        return super.useOn(context);
    }
}
