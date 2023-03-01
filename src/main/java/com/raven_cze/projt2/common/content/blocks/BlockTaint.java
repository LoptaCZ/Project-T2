package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.blocks.references.PT2Block;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;

import java.util.function.Supplier;

public class BlockTaint extends PT2Block{
    public BlockTaint(Properties properties){
        super(properties);
        Supplier<SoundEvent>Break=PT2Sounds.Gore;
        Supplier<SoundEvent>Step=PT2Sounds.Gore;
        Supplier<SoundEvent>Place=PT2Sounds.Gore;
        Supplier<SoundEvent>Hit=PT2Sounds.Gore;
        Supplier<SoundEvent>Fall=PT2Sounds.Gore;

        properties.sound(new ForgeSoundType(1.0F,1.0F,Break,Step,Place,Hit,Fall));
    }
}
