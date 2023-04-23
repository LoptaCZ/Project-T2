package com.raven_cze.projt2.common.event;

import com.raven_cze.projt2.ProjectT2;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=ProjectT2.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents{

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent e){
        Player player=e.player;
        Inventory inv=player.getInventory();

        if(e.phase==TickEvent.Phase.END){
            for(ItemStack item:inv.items){
                if(item.hasTag()){
                    CompoundTag nbt=getNBT(item);
                    if(!nbt.getList("Enchantments",9).isEmpty()){
                        ListTag ench=nbt.getList("Enchantments",9);
                        Tag enchant=ench.get(ench.indexOf(StringTag.valueOf("id")));
                        if( enchant.getAsString().equals(ProjectT2.MODID+":repair") ){
                            if(item.isDamageableItem()){
                                int damage=Mth.clamp(item.getDamageValue()-1,0,item.getMaxDamage());
                                item.setDamageValue(damage);
                            }
                        }
                    }
                }
            }
        }
    }

    private CompoundTag getNBT(ItemStack stack){
        if(stack.getTag()!=null){
            if(stack.getTag().isEmpty()){
                stack.getOrCreateTag();
            }
            return stack.getTag();
        }
        return new CompoundTag();
    }
}
