package com.thunder.bionisation;

import com.thunder.bionisation.versions.VersionChecker;
import com.thunder.command.BCommand;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(name = Information.MOD_NAME, modid = Information.MOD_ID, version = Information.VERSION)
public class Bionisation {

    @Mod.Instance(Information.MOD_ID)
    public static Bionisation INSTANCE;

    @SidedProxy(clientSide = Information.CLIENT_PROXY_PATH, serverSide = Information.COMMON_PROXY_PATH)
    public static CommonProxy proxy;

    public static Configuration config;
    public static VersionChecker checker;
    public static boolean wasWarnedNewVersion = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile(), Information.VERSION, true);
        Config.init();
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @EventHandler
    public  void onStart(FMLServerStartingEvent event){
        event.registerServerCommand(new BCommand());
    }

    public static Configuration getConfig() {
        return config;
    }
}
