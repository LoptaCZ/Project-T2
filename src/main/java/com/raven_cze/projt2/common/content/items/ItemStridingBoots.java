package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.ProjectT2;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemStridingBoots extends PT2ArmorItem{
    double jumpPower;
    double moveSpeed;
    double jumpMax;
    int fallDmg;
    private boolean logged=false;
    public ItemStridingBoots(Properties properties){
        super(PT2ArmorMats.SPECIAL,EquipmentSlot.FEET,properties);
        this.jumpPower=0.3F;
        this.moveSpeed=0.04F;
        this.jumpMax=0.85F;
        this.fallDmg=2;
    }

    @Override
    public void onArmorTick(ItemStack stack,Level level,Player player){
        //this.moveSpeed=player.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
        //this.jumpPower=player.getAttribute(Attributes.JUMP_STRENGTH).getValue();

        if(!logged) {
            ProjectT2.LOGGER.info("{}'s jump power: {}", player.getDisplayName().getContents(), (float) this.jumpPower);
            ProjectT2.LOGGER.info("{}'s movement speed: {}", player.getDisplayName().getContents(), (float) this.moveSpeed);
            ProjectT2.LOGGER.info("{}'s jump height: {}", player.getDisplayName().getContents(), (float) this.jumpMax);
            ProjectT2.LOGGER.info("{}'s fallDmg division: {}", player.getDisplayName().getContents(), this.fallDmg);
            logged=!logged;
        }
        super.onArmorTick(stack,level,player);
    }
}
