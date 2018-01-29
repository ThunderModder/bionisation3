package com.thunder.bionisation;

import net.minecraftforge.common.config.Configuration;


public class Config {

    public static boolean saveEffects;

    public static void init(){
        Configuration config = Bionisation.getConfig();
        config.load();
        saveEffects = config.getBoolean("SaveEffects", "Main configurations", false, "If true, all effects will be saved after player death.");
        if (config.hasChanged()) config.save();
    }
}
