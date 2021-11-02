package raven_cze.projt2.common.config;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import raven_cze.projt2.ProjectT2;
import raven_cze.projt2.proxies.CommonProxy;

public class ModConfig {
    public static final String CATEGORY_NAME_CLIENT="client";
    public static final String CATEGORY_NAME_SERVER="server";

    //  CLIENT
    public static boolean customItemColors;
    public static boolean customItemModels;
    public static boolean pipesDrip;
    public static boolean seeTrough;
    //  SERVER
    public static int autoSave;
    public static int maxAura;
    public static int taintSpread;
    //  SHARED
    public static boolean debugMsgs;
    public static boolean shiftTools;

    public static void readConfig(){
        Configuration cfg= CommonProxy.config;
        try{
            cfg.load();
            initClientConfig(cfg);
            initServerConfig(cfg);
        }catch(Exception e){
            ProjectT2.ProjectT2Core.PT2Logger.error("Problem loading config file!",new Object[]{e});
        }finally{
            if(cfg.hasChanged()) cfg.save();
        }
    }
    private static void initClientConfig(Configuration cfg){
        cfg.addCustomCategoryComment(CATEGORY_NAME_CLIENT, I18n.format("item.crystal.vis.name"));
        customItemModels = cfg.getBoolean("customItemModels",CATEGORY_NAME_CLIENT,customItemColors,I18n.format("item.crystal.fire.name"));
        customItemColors = cfg.getBoolean("customItemColors",CATEGORY_NAME_CLIENT,customItemColors,I18n.format("item.crystal.water.name"));
        pipesDrip = cfg.getBoolean("pipesDrip",CATEGORY_NAME_CLIENT,customItemColors,I18n.format("item.crystal.empty.name"));
        seeTrough = cfg.getBoolean("seeTrough",CATEGORY_NAME_CLIENT,customItemColors,I18n.format("item.crystal.air.name"));
    }
    private static void initServerConfig(Configuration cfg){
        cfg.addCustomCategoryComment(CATEGORY_NAME_SERVER,I18n.format("item.crystal.taint.name"));
    }
}
