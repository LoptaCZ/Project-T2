package com.raven_cze.projt2.common.config;

import com.raven_cze.projt2.ProjectT2;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientCFG {
    public static final ForgeConfigSpec.Builder builder =new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean>lowFX;
    public static final ForgeConfigSpec.ConfigValue<Boolean>customItem;
    public static final ForgeConfigSpec.ConfigValue<Boolean>shiftTools;
    public static final ForgeConfigSpec.ConfigValue<Boolean>pipeDrips;
    public static final ForgeConfigSpec.ConfigValue<Boolean>portalGFX;
    public static final ForgeConfigSpec.ConfigValue<Integer>portalRes;

    static{
        builder.comment("Project T2 Client Configuration").push(ProjectT2.MODID);
            lowFX=builder.comment("Set this to true to reduce the quality of rendered gfx.").define("Low FX",false);
            customItem=builder.comment("If set to true items will have colored names & tooltips.").define("Item Colors",false);
            shiftTools=builder.comment("Normally special tools like the Axe of the stream uses their special ability all the time. Setting this to true changes the behaviour so you need to press shift (sneak) to use the special abilities.").define("Shift Tools",false);
            pipeDrips=builder.comment("If set to true pipes will look leaky when nearly full.").define("Leaky Pipes",false);
            portalGFX=builder.comment("Set this to false if you want to disable the see-through portals.").define("Portal Render",true);
            portalRes=builder.comment("Defines resolution of rendered portal.").defineInRange("Portal Resolution",512,128,4096);
        builder.pop();
        SPEC= builder.build();
    }
}
