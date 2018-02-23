package com.thunder.bionisation;

import net.minecraftforge.common.config.Configuration;


public class Config {

    //main config
    public static boolean saveEffects;//+
    public static boolean canSpawnBloodVial;//+
    public static boolean canShowUpdates;

    //structure
    public static int structureSpawnChance;//+
    public static int symbiontSpawnChance;//+

    //other configurations
    public static int fatigueImmunityLevel;//30+
    public static int aerVirusImmunityLevel;//50+
    public static int lackOfBloodBloodLevel;//40+
    public static int revisionChance;//85+

    public static int mutationProcessChance;//30+
    public static int mutationProcessDelay;//2400+
    public static int mutationVirusChance;//15+

    //chances
    public static int chanceBleeding;//5+
    public static int chanceSunstroke;//35+
    public static int chanceCold;//20+
    public static int chanceFracture;//10+
    public static int chanceFoodPoisoning;//15+
    public static int chanceBlackBacteria;//100+
    public static int chanceSwampBacteria;//10+
    public static int chanceGlowingBacteriaGlowstone;//5+
    public static int chanceGlowingBacteriaBlaze;//35+
    public static int chanceWaterBacteria;//25+
    public static int chanceEnderBacteria;//100+
    public static int chanceCactusBacteria;//20+
    public static int chanceBoneBacteria;//5+
    public static int chanceTeraBacteria;//10+
    public static int chanceMyceliumBacteria;//25+
    public static int chanceSmallGreenBacteria;//35+
    public static int chanceBigGreenBacteria;//20+
    public static int chanceSeaBacteria;//25+
    public static int chanceCloneBacteria;//10+
    public static int chanceGiantVirus;//5+
    public static int chancePteroVirus;//5+
    public static int chanceEnderVirus;//45+
    public static int chanceBrainVirus;//15+
    public static int chanceWitherVirus;//15+
    public static int chanceBatVirus;//35+
    public static int chanceBloodVirus;//35+
    public static int chanceCreeperVirus;//20+
    public static int chanceRedVirus;//20+
    public static int chanceOceanVirus;//5+
    public static int chanceSkullVirus;//10+
    public static int chanceRabies;//5+
    public static int chancePolarVirus;//20+
    public static int chanceAerVirus;//70+
    public static int chanceDesertVirus;//25+

    //durations
    public static int durationBleeding;//6000+
    public static int durationSunstroke;//12000+
    public static int durationCold;//32000+
    public static int durationFracture;//12000+
    public static int durationFatigue;//-1+
    public static int durationLackOfBlood;//-1+
    public static int durationFoodPoisoning;//32000+
    public static int durationBlackBacteria;//36000+
    public static int durationSwampBacteria;//-1+
    public static int durationGlowingBacteria;//12000+
    public static int durationWaterBacteria;//18000+
    public static int durationEnderBacteria;//-1+
    public static int durationCactusBacteria;//12000+
    public static int durationBoneBacteria;//-1+
    public static int durationTeraBacteria;//12000+
    public static int durationMyceliumBacteria;//-1+
    public static int durationSmallGreenBacteria;//-1+
    public static int durationBigGreenBacteria;//-1+
    public static int durationSeaBacteria;//32000+
    public static int durationCloneBacteria;//-1+
    public static int durationGiantVirus;//6000+
    public static int durationSkullVirus;//32000+
    public static int durationDesertVirus;//16000+
    public static int durationPowerSymbiont;//-1+
    public static int durationResistanceSymbiont;//-1+
    public static int durationMobilitySymbiont;//-1+
    public static int durationVisionSymbiont;//-1+
    public static int durationMAffinitySymbiont;//-1+
    public static int durationWEyeSymbiont;//-1+
    public static int durationImmunitySymbiont;//-1+
    public static int durationBloodSymbiont;//-1+
    public static int durationHealthSymbiont;//-1+
    public static int durationEPowerSymbiont;//-1+
    public static int durationHealthSTSymbiont;//-1+
    public static int durationCreeperSymbiont;//-1+
    public static int durationBlazeSymbiont;//-1+
    public static int durationSoulsSymbiont;//-1+
    public static int durationGolemPSymbiont;//-1+
    public static int durationWAffinitySymbiont;//-1+
    public static int durationVampirismSymbiont;//-1+

    public static int standartEffectCureDuration;//1200+
    public static int standartBacteriaCureDuration;//2400+
    public static int standartVirusDuration;//-1+

    //machine
    public static int disinfectorProcessTime;//9600+
    public static int herbalStationProcessTime;//1200+
    public static int vaccineCreatorProcessTime;//1200+
    public static int dnaFormerProcessTime;//2400+
    public static int virusReplicatorProcessTime;//6000+

    //biome
    public static int biomeSpawnWeight;//100+
    public static int biomeID;//100+

    public static void init(){
        Configuration config = Bionisation.getConfig();
        config.load();
        //main
        saveEffects = config.getBoolean("SaveEffects", "Main configurations", false, "If true, all effects will be saved after player death.");
        canSpawnBloodVial = config.getBoolean("Drop blood vials", "Main configurations", false, "If true, mobs will drop blood vials after death.");
        canShowUpdates = config.getBoolean("Show Updates", "Main configurations", true, "If true, Bionisation 3 will show available updates in chat.");
        //structure
        structureSpawnChance = config.getInt("Abandoned laboratory gen chance", "Structure configurations", 10, 0, 100, "Chance for laboratory spawn. Type 0 to disable spawning.");
        symbiontSpawnChance = config.getInt("Symbiont gen chance in chests", "Structure configurations", 15, 1, 100, "Chance for symbiont gen in laboratory chests.");

        //other configurations
        fatigueImmunityLevel = config.getInt("Immunity Level Fatigue", "Other configurations", 30, 1, 100, "Immunity level for Fatigue effect.");
        aerVirusImmunityLevel = config.getInt("Immunity Level Aer Virus", "Other configurations", 50, 1, 100, "Immunity level for Aer Virus effect.");
        lackOfBloodBloodLevel = config.getInt("Blood Level Lack Of Blood", "Other configurations", 40, 1, 100, "Blood level for Lack of Blood effect.");
        revisionChance = config.getInt("Inventory Revision Chance", "Other configurations", 85, 1, 100, "Chance for inventory revision process(Item infection).");

        mutationProcessChance = config.getInt("Mutation Process Chance", "Other configurations", 30, 1, 100, "Chance for virus mutation process.");
        mutationProcessDelay = config.getInt("Mutation Process Delay", "Other configurations", 2400, 1, 1000000000, "Interval between virus mutation processes.");
        mutationVirusChance = config.getInt("Mutation Virus Chance", "Other configurations", 15, 1, 100, "Chance for specified virus mutation.");

        //biome
        biomeSpawnWeight = config.getInt("Biome Spawn Weight", "Biome configurations", 100, 1, 1000000000, "");
        biomeID = config.getInt("Biome ID", "Biome configurations", 100, 1, 1000000000, "");

        //machines
        disinfectorProcessTime = config.getInt("Disinfector Process Time", "Machine Configurations", 9600, 0, 1000000000, "");
        herbalStationProcessTime = config.getInt("Herbal Station Process Time", "Machine Configurations", 1200, 0, 1000000000, "");
        vaccineCreatorProcessTime = config.getInt("Vaccine Creator Process Time", "Machine Configurations", 1200, 0, 1000000000, "");
        dnaFormerProcessTime = config.getInt("DNA Former Process Time", "Machine Configurations", 2400, 0, 1000000000, "");
        virusReplicatorProcessTime = config.getInt("Virus Replicator Process Time", "Machine Configurations", 6000, 0, 1000000000, "");

        //chances
        chanceBleeding = config.getInt("Chance Bleeding", "Effect configurations - chance", 5, 1, 100, "");
        chanceSunstroke = config.getInt("Chance Sunstroke", "Effect configurations - chance", 35, 1, 100, "");
        chanceCold = config.getInt("Chance Cold", "Effect configurations - chance", 20, 1, 100, "");
        chanceFracture = config.getInt("Chance Fracture", "Effect configurations - chance", 10, 1, 100, "");
        chanceFoodPoisoning = config.getInt("Chance Food Poisoning", "Effect configurations - chance", 15, 1, 100, "");
        chanceBlackBacteria = config.getInt("Chance Black Bacteria", "Effect configurations - chance", 100, 1, 100, "");
        chanceSwampBacteria = config.getInt("Chance Swamp Bacteria", "Effect configurations - chance", 10, 1, 100, "");
        chanceGlowingBacteriaGlowstone = config.getInt("Chance Glowing Bacteria Glowstone", "Effect configurations - chance", 5, 1, 100, "");
        chanceGlowingBacteriaBlaze = config.getInt("Chance Glowing Bacteria Blaze", "Effect configurations - chance", 35, 1, 100, "");
        chanceWaterBacteria = config.getInt("Chance Water Bacteria", "Effect configurations - chance", 25, 1, 100, "");
        chanceEnderBacteria = config.getInt("Chance Ender Bacteria", "Effect configurations - chance", 100, 1, 100, "");
        chanceCactusBacteria = config.getInt("Chance Cactus Bacteria", "Effect configurations - chance", 20, 1, 100, "");
        chanceBoneBacteria = config.getInt("Chance Bone Bacteria", "Effect configurations - chance", 5, 1, 100, "");
        chanceTeraBacteria = config.getInt("Chance Tera Bacteria", "Effect configurations - chance", 10, 1, 100, "");
        chanceMyceliumBacteria = config.getInt("Chance Mycelium Bacteria", "Effect configurations - chance", 25, 1, 100, "");
        chanceSmallGreenBacteria = config.getInt("Chance Small Green Bacteria", "Effect configurations - chance", 35, 1, 100, "");
        chanceBigGreenBacteria = config.getInt("Chance Big Green Bacteria", "Effect configurations - chance", 20, 1, 100, "");
        chanceSeaBacteria = config.getInt("Chance Sea Bacteria", "Effect configurations - chance", 25, 1, 100, "");
        chanceCloneBacteria = config.getInt("Chance Clone Bacteria", "Effect configurations - chance", 10, 1, 100, "");
        chanceGiantVirus = config.getInt("Chance Giant Virus", "Effect configurations - chance", 5, 1, 100, "");
        chancePteroVirus = config.getInt("Chance Ptero Virus", "Effect configurations - chance", 5, 1, 100, "");
        chanceEnderVirus = config.getInt("Chance Ender Virus", "Effect configurations - chance", 45, 1, 100, "");
        chanceBrainVirus = config.getInt("Chance Brain Virus", "Effect configurations - chance", 15, 1, 100, "");
        chanceWitherVirus = config.getInt("Chance Wither Virus", "Effect configurations - chance", 15, 1, 100, "");
        chanceBatVirus = config.getInt("Chance Bat Virus", "Effect configurations - chance", 35, 1, 100, "");
        chanceBloodVirus = config.getInt("Chance Blood Virus", "Effect configurations - chance", 35, 1, 100, "");
        chanceCreeperVirus = config.getInt("Chance Creeper Virus", "Effect configurations - chance", 20, 1, 100, "");
        chanceRedVirus = config.getInt("Chance Red Virus", "Effect configurations - chance", 20, 1, 100, "");
        chanceOceanVirus = config.getInt("Chance Ocean Virus", "Effect configurations - chance", 5, 1, 100, "");
        chanceSkullVirus = config.getInt("Chance Skull Virus", "Effect configurations - chance", 10, 1, 100, "");
        chanceRabies = config.getInt("Chance Rabies", "Effect configurations - chance", 5, 1, 100, "");
        chancePolarVirus = config.getInt("Chance Polar Virus", "Effect configurations - chance", 20, 1, 100, "");
        chanceAerVirus = config.getInt("Chance Aer Virus", "Effect configurations - chance", 70, 1, 100, "");
        chanceDesertVirus = config.getInt("Chance Desert Virus", "Effect configurations - chance", 25, 1, 100, "");

        //durations
        config.addCustomCategoryComment("Effect configurations - duration", "Type -1 for infinite duration. Type 0 to disable effect.");

        standartEffectCureDuration = config.getInt("Standart Effect Cure Duration", "Effect configurations - duration", 1200, -1, 1000000000, "");
        standartBacteriaCureDuration = config.getInt("Standart Bacteria Cure Duration", "Effect configurations - duration", 2400, -1, 1000000000, "");
        standartVirusDuration = config.getInt("Standart Virus Duration", "Effect configurations - duration", -1, -1, 1000000000, "");


        durationBleeding = config.getInt("Duration Bleeding", "Effect configurations - duration", 6000, -1, 1000000000, "");
        durationSunstroke = config.getInt("Duration Sunstroke", "Effect configurations - duration", 12000, -1, 1000000000, "");
        durationCold = config.getInt("Duration Cold", "Effect configurations - duration", 32000, -1, 1000000000, "");
        durationFracture = config.getInt("Duration Fracture", "Effect configurations - duration", 12000, -1, 1000000000, "");
        durationFatigue = config.getInt("Duration Fatigue", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationLackOfBlood = config.getInt("Duration Lack Of Blood", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationFoodPoisoning = config.getInt("Duration Food Poisoning", "Effect configurations - duration", 32000, -1, 1000000000, "");
        durationBlackBacteria = config.getInt("Duration Black Bacteria", "Effect configurations - duration", 36000, -1, 1000000000, "");
        durationSwampBacteria = config.getInt("Duration Swamp Bacteria", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationGlowingBacteria = config.getInt("Duration Glowing Bacteria", "Effect configurations - duration", 12000, -1, 1000000000, "");
        durationWaterBacteria = config.getInt("Duration Water Bacteria", "Effect configurations - duration", 18000, -1, 1000000000, "");
        durationEnderBacteria = config.getInt("Duration Ender Bacteria", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationCactusBacteria = config.getInt("Duration Cactus Bacteria", "Effect configurations - duration", 12000, -1, 1000000000, "");
        durationBoneBacteria = config.getInt("Duration Bone Bacteria", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationTeraBacteria = config.getInt("Duration Tera Bacteria", "Effect configurations - duration", 12000, -1, 1000000000, "");
        durationMyceliumBacteria = config.getInt("Duration Mycelium Bacteria", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationSmallGreenBacteria = config.getInt("Duration Small Green Bacteria", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationBigGreenBacteria = config.getInt("Duration Big Green Bacteria", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationSeaBacteria = config.getInt("Duration Sea Bacteria", "Effect configurations - duration", 32000, -1, 1000000000, "");
        durationCloneBacteria = config.getInt("Duration Clone Bacteria", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationGiantVirus = config.getInt("Duration Giant Virus", "Effect configurations - duration", 6000, -1, 1000000000, "");
        durationSkullVirus = config.getInt("Duration Skull Virus", "Effect configurations - duration", 32000, -1, 1000000000, "");
        durationDesertVirus = config.getInt("Duration Desert Virus", "Effect configurations - duration", 16000, -1, 1000000000, "");

        durationPowerSymbiont = config.getInt("Symbiont Of Power", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationResistanceSymbiont = config.getInt("Symbiont Of Resistance", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationMobilitySymbiont = config.getInt("Symbiont Of Mobility", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationVisionSymbiont = config.getInt("Symbiont Of True Vision", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationMAffinitySymbiont = config.getInt("Symbiont Of Magic Affinity", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationWEyeSymbiont = config.getInt("Symbiont Of Withering Eye", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationImmunitySymbiont = config.getInt("Symbiont Of Immunity", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationBloodSymbiont = config.getInt("Symbiont Of Blood", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationHealthSymbiont = config.getInt("Symbiont Of Health", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationEPowerSymbiont = config.getInt("Symbiont Of Effect Power", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationHealthSTSymbiont = config.getInt("Symbiont Of Healthy Stomach", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationCreeperSymbiont = config.getInt("Symbiont Of Creeper Soul", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationBlazeSymbiont = config.getInt("Symbiont Of Blaze Heart", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationSoulsSymbiont = config.getInt("Symbiont Of Souls", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationGolemPSymbiont = config.getInt("Symbiont Of Golem Power", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationWAffinitySymbiont = config.getInt("Symbiont Of Water Affinity", "Effect configurations - duration", -1, -1, 1000000000, "");
        durationVampirismSymbiont = config.getInt("Symbiont Of Vampirism", "Effect configurations - duration", -1, -1, 1000000000, "");
        if (config.hasChanged()) config.save();
    }
}
