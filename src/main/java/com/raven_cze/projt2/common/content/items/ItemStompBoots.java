package com.raven_cze.projt2.common.content.items;

import net.minecraft.world.entity.EquipmentSlot;

public class ItemStompBoots extends ItemSevenBoots{
    public ItemStompBoots(Properties properties){
        super(properties);
        this.jumpPower=0.8F;
        this.fallDmg=3;
        this.moveSpeed=0.075F;
        this.jumpMax=1.6F;
    }
}
