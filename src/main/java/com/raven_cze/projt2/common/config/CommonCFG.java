package com.raven_cze.projt2.common.config;

import com.raven_cze.projt2.ProjectT2;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonCFG {
    public static final ForgeConfigSpec.Builder builder =new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean>debugMode;
    public static final ForgeConfigSpec.ConfigValue<Boolean>versionCheck;
    public static final ForgeConfigSpec.ConfigValue<Integer>autoSave;
    public static final ForgeConfigSpec.ConfigValue<Integer>maxAura;
    public static final ForgeConfigSpec.ConfigValue<Integer>taintSpread;

    static{
        builder.comment("Project T2 Client Configuration").push(ProjectT2.MODID);
            //
            debugMode=builder.comment("Setting this to true will enable Debug Mode.").define("Debug Mode",false);
            versionCheck=builder.comment("Enable Version Check").define("Version Check",true);
            autoSave=builder.comment("The interval in minutes between autosaves for ProjectT2 data.","If set to 0 autosaving is disabled.").defineInRange("Auto Save",5,0,60);
            maxAura=builder.comment("The maximum taint and aura amount - use values between 5000 & 30000","Default is 15000","Changing this setting with a world in progress can do funny stuff - you have been warned.").defineInRange("Max Aura",15000,5000,30000);
            taintSpread=builder.comment("How often tainted chunks spawn at world creation.","0 = None "," 1 = Default "," 2 = Common and with high taint levels").defineInRange("Taint Spreading",1,0,2);
        builder.pop();
        SPEC= builder.build();
    }
}