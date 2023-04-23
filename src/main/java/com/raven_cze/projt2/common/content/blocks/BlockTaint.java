package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.blocks.references.PT2Block;
import com.raven_cze.projt2.common.content.enchantment.PT2Soulstealer;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;

import java.util.function.Supplier;

public class BlockTaint extends PT2Block{
    public BlockTaint(Properties properties){
        super(properties.sound(new ForgeSoundType(1.0F,1.0F,()->PT2Sounds.Gore.get(),()->PT2Sounds.Gore.get(),()->PT2Sounds.Gore.get(),()->PT2Sounds.Gore.get(),()->PT2Sounds.Gore.get())));
    }

}
