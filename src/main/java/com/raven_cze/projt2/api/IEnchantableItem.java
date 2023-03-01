package com.raven_cze.projt2.api;

import java.util.ArrayList;

import net.minecraft.world.item.enchantment.Enchantment;

public interface IEnchantableItem {
	ArrayList<Enchantment> acceptableEnchants();
}
