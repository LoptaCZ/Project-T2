package com.raven_cze.projt2.api;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum EnumConduitConType implements StringRepresentable{
    NONE,
    UNKNOWN,
    CRUCIBLE_BASIC,
    CRUCIBLE_EYES,
    CRUCIBLE_SOULS,
    CRUCIBLE_THAUMIUM,
    DARK_GENERATOR,
    FURNACE,
    FILTER,
    INFUSER,
    DINFUSER;

    public boolean isConnected(){return this!=NONE;}
    @Override
    public @NotNull String getSerializedName() {
        return getName();
    }
    public String getName(){return name().toLowerCase(Locale.ENGLISH);}
}
