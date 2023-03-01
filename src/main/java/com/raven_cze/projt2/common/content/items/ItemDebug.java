package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.blocks.BlockVoidCube;
import com.raven_cze.projt2.common.content.tiles.TileVoidKeyhole;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ItemDebug extends PT2Item{
    String[]modes={"monument","taint","vis","greatwood","silverwood","taintwood","info"};
    String mode;
    int curMode;

    int delay;
    public ItemDebug(Properties properties) {
        super(properties.rarity(Rarity.EPIC));

        if(new ItemStack(this).hasTag()){
            CompoundTag tag=new ItemStack(this).getTag()!=null?new ItemStack(this).getTag():new CompoundTag();
            if (tag!=null){
                this.curMode=tag.getInt("CurMode");
                this.mode=tag.getString("Mode");
            }
        }else{
            this.curMode=0;
            this.mode=this.modes[curMode];

            ItemStack stack=new ItemStack(this);
            CompoundTag compound=new CompoundTag();
            compound.putInt("CurMode",this.curMode);
            compound.putString("Mode",this.mode);
            stack.setTag(compound);
        }
        this.delay=1;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>use(@NotNull Level level,@NotNull Player player,@NotNull InteractionHand hand){
        this.delay--;
        if(this.delay<0)this.delay=1;
        HitResult rayTrace=Minecraft.getInstance().hitResult;
        if(rayTrace!=null){if( !level.getBlockState(new BlockPos(rayTrace.getLocation())).equals(net.minecraft.world.level.block.Blocks.AIR.defaultBlockState()) || !level.getBlockState(new BlockPos(rayTrace.getLocation())).equals(net.minecraft.world.level.block.Blocks.CAVE_AIR.defaultBlockState()) || !level.getBlockState(new BlockPos(rayTrace.getLocation())).equals(net.minecraft.world.level.block.Blocks.VOID_AIR.defaultBlockState()) )
            if(player.isCrouching()){
                if(this.delay==0)this.curMode++;

                if(this.curMode>this.modes.length)this.curMode=0;
                this.mode=this.modes[Utils.Math.clamp(this.curMode,0,this.modes.length-1)];
                player.displayClientMessage(new TextComponent("[ "+this.mode.toUpperCase()+" ]"),true);

                CompoundTag compound=new CompoundTag();
                compound.putString("Mode",this.mode);
                compound.putInt("CurMode",this.curMode);

                player.getItemInHand(hand).setTag(compound);
            }else{
                BlockPos pos=new BlockPos(rayTrace.getLocation()).below();
                switch (this.mode){
                //              Object Summoner
                    case"monument"->{
                        String text="RDT_20220101_102227919230715751214693.gif";
                        if(level.getBlockEntity(pos)instanceof TileVoidKeyhole te){
                            String color="§f";
                            switch(te.rune){
                                case"vis"->color="§5";
                                case"water"->color="§b";
                                case"earth"->color="§a";
                                case"fire"->color="§4";
                                case"air"->color="§e";
                                case"taint"->color="§d";
                            }
                            if(!te.placed){
                                te.placed = true;
                                te.setChanged();
                                text="Void Keyhole rune: "+color+te.rune+"§r inserted "+color+te.rune+" §7crystal";

                            }else text="Void Keyhole rune: "+color+te.rune;
                            if(!level.isClientSide)player.displayClientMessage(new TextComponent(text), false);
                            return InteractionResultHolder.success(player.getItemInHand(hand));
                        }else{
                            this.delay=5;
                            BlockState vCube0 = PT2Blocks.VOID_CUBE.get().defaultBlockState();//MIDDLE
                            BlockState vCube1 = PT2Blocks.VOID_CUBE.get().defaultBlockState();//TOP
                            BlockState vCube2 = PT2Blocks.VOID_CUBE.get().defaultBlockState();//BOTTOM
                            BlockState vKeyhole = PT2Blocks.VOID_KEYHOLE.get().defaultBlockState();
                            BlockState vLock = PT2Blocks.VOID_LOCK.get().defaultBlockState();
                            BlockState eldritch = PT2Blocks.ELDRITCH_MONOLITH.get().defaultBlockState();
                            // .add(BlockBehaviour.Properties.of(Material.STONE).strength(Float.MAX_VALUE));
                            ((BlockVoidCube)vCube0.getBlock()).type=0;
                            ((BlockVoidCube)vCube1.getBlock()).type=1;
                            ((BlockVoidCube)vCube2.getBlock()).type=2;
                            //      Lock Mechanism
                            level.setBlockAndUpdate(pos.north(), vKeyhole);
                            level.setBlockAndUpdate(pos.east(), vKeyhole);
                            level.setBlockAndUpdate(pos.south(), vKeyhole);
                            level.setBlockAndUpdate(pos.west(), vKeyhole);
                            level.setBlockAndUpdate(pos, vLock);
                            //      Levitating thing in middle
                            level.setBlockAndUpdate(pos.above(3), vCube2.setValue(BlockVoidCube.CUBE_TYPE,3));
                            level.setBlockAndUpdate(pos.above(4), vCube0.setValue(BlockVoidCube.CUBE_TYPE,3));
                            level.setBlockAndUpdate(pos.above(5), vCube0.setValue(BlockVoidCube.CUBE_TYPE,3));
                            level.setBlockAndUpdate(pos.above(6), vCube0.setValue(BlockVoidCube.CUBE_TYPE,3));
                            level.setBlockAndUpdate(pos.above(7), vCube1.setValue(BlockVoidCube.CUBE_TYPE,3));
                            //      Surrounding blocks
                            level.setBlockAndUpdate(pos.north().east(), eldritch);
                            level.setBlockAndUpdate(pos.north().west(), eldritch);
                            level.setBlockAndUpdate(pos.south().east(), eldritch);
                            level.setBlockAndUpdate(pos.south().west(), eldritch);
                        }
                    }//END OF MONUMENT
                    case"greatwood"->{
                        ProjectT2.LOGGER.debug("Hey {} you should fuck yourself.", this.mode);
                    }//END OF GREATWOOD
                    case"silverwood"->{
                        ProjectT2.LOGGER.debug("{} you really should stop doing that.", this.mode);
                    }//END OF SILVERWOOD
                    case"taintwood"->{
                        ProjectT2.LOGGER.debug("Did you know that {} smell like shit?", this.mode);
                    }//END OF TAINTWOOD
                    case"ass"->{
                        ProjectT2.LOGGER.debug("Minecraft should kiss my {}!!", this.mode);
                    }//END OF ASS
                    //              Aura Management
                    case"vis"->{
                        ProjectT2.LOGGER.debug("Off to hang myself with {}.", this.mode);
                    }//END OF VIS MANAGEMENT
                    case"taint"->{
                        ProjectT2.LOGGER.debug("And now i will {} your whole family!", this.mode);
                    }//END OF TAINT MANAGEMENT
                    case"info"->{
                        ProjectT2.LOGGER.debug("Oy {}, where is my shit.", this.mode);
                    }//END OF INFO
                }// switch
            }// Check if player is crouching
        }// Ray Trace with AIR check
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
