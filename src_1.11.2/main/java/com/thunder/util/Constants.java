package com.thunder.util;

import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;


public class Constants {

    public static final int MAX_IMMUNITY_LEVEL = 100;
    public static final int MAX_BLOOD_LEVEL = 100;
    public static final int MAX_TICKER_VALUE = Integer.MAX_VALUE;

    //flowers
    public static final AxisAlignedBB STANDARTAXIS1_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.9000000238418579D, 0.699999988079071D);
    public static final AxisAlignedBB STANDARTAXIS2_AABB = new AxisAlignedBB(0.100000000000001D, 0.0D, 0.100000000000001D, 0.900000000000001D, 0.9000000238418579D, 0.90000000000001D);
    public static final AxisAlignedBB STANDARTAXIS3_AABB = new AxisAlignedBB(0.100000000000001D, 0.0D, 0.100000000000001D, 0.90000000000000D, 0.4000000238418579D, 0.90000000000000D);

    //helper
    public static final int POTION_SPEED_ID = 1;
    public static final int POTION_SLOWNESS_ID = 2;
    public static final int POTION_HASTE_ID = 3;
    public static final int POTION_MINIMGF_ID = 4;
    public static final int POTION_STRENGTH_ID = 5;
    public static final int POTION_INSTHEALTH_ID = 6;
    public static final int POTION_INSTDAMAGE_ID = 7;
    public static final int POTION_JUMPBOOST_ID = 8;
    public static final int POTION_NAUSEA_ID = 9;
    public static final int POTION_REGENERATION_ID = 10;
    public static final int POTION_RESISTANCE_ID = 11;
    public static final int POTION_FIRERESISTANCE_ID = 12;
    public static final int POTION_WATERBREATHING_ID = 13;
    public static final int POTION_INVISIBILITY_ID = 14;
    public static final int POTION_BLINDNESS_ID = 15;
    public static final int POTION_NIGHTVISION_ID = 16;
    public static final int POTION_HUNGER_ID = 17;
    public static final int POTION_WEAKENSS_ID = 18;
    public static final int POTION_POISON_ID = 19;
    public static final int POTION_WITHER_ID = 20;
    public static final int POTION_HEALTHBOOST_ID = 21;
    public static final int POTION_ABSORPTION_ID = 22;
    public static final int POTION_SATURATION_ID = 23;
    public static final int POTION_GLOWING_ID = 24;
    public static final int POTION_LEVITATION_ID = 25;
    public static final int POTION_LUCK_ID = 26;
    public static final int POTION_UNLUCK_ID = 27;

    //effect id
    public static final int ID_FRACTURE = 0;
    public static final int ID_INTERNAL_BLEEDING = 1;
    public static final int ID_BLEEDING = 2;
    public static final int ID_SUNSTROKE = 3;
    public static final int ID_COLD = 4;
    public static final int ID_FATIGUE = 5;
    public static final int ID_LACKOFBLOOD = 6;
    public static final int ID_FOODPOISONING = 7;
    //bacteria id
    public static final int ID_BLACKBACTERIA = 8;
    public static final int ID_SWAMPBACTERIA = 9;
    public static final int ID_GLOWINGBACTERIA = 10;
    public static final int ID_WATERBACTERIA = 11;
    public static final int ID_ENDERBACTERIA = 12;
    public static final int ID_CACTUSBACTERIA = 13;
    public static final int ID_BONEBACTERIA = 14;
    public static final int ID_TERABACTERIA = 15;
    public static final int ID_MYCELIUMBACTERIA = 16;
    public static final int ID_SMALLGREENBACTERIA = 17;
    public static final int ID_BIGGREENBACTERIA = 18;
    public static final int ID_SEABACTERIA = 19;
    public static final int ID_CLONEBACTERIA = 20;
    //cures
    public static final int CURE_BACTERIA_STANDART_DURATION = 6000;
    public static final int CURE_EFFECT_STANDART_DURATION = 1200;

    public static final int ID_CURE_BLEEDING = 100;
    public static final int ID_CURE_СOLD = 101;
    public static final int ID_CURE_FPOISON = 102;
    public static final int ID_CURE_BLACKBACTERIA = 103;
    public static final int ID_CURE_SWAMPBACTERIA = 104;
    public static final int ID_CURE_GLOWINGBACTERIA = 105;
    public static final int ID_CURE_WATERBACTERIA = 106;
    public static final int ID_CURE_ENDERBACTERIA = 107;
    public static final int ID_CURE_CACTUSBACTERIA = 108;
    public static final int ID_CURE_BONEBACTERIA = 109;
    public static final int ID_CURE_TERABACTERIA = 110;
    public static final int ID_CURE_MYCELIUMBACTERIA = 111;
    public static final int ID_CURE_SMALLGREENBACTERIA = 112;
    public static final int ID_CURE_SEABACTERIA = 113;

    public static final int ID_EFFECT_IMMUNITY = 114;

    public static final int ID_GIANT_VIRUS = 50;
    public static final int ID_PTERO_VIRUS = 51;
    public static final int ID_ENDER_VIRUS = 52;
    public static final int ID_BRAIN_VIRUS = 53;
    public static final int ID_WITHER_VIRUS = 54;
    public static final int ID_VAMPIRE_VIRUS = 55;
    public static final int ID_BLOOD_VIRUS = 56;
    public static final int ID_CREEPER_VIRUS = 57;
    public static final int ID_RED_VIRUS = 58;
    public static final int ID_OCEAN_VIRUS = 58;
    public static final int ID_ARCHAIC_VIRUS = 59;
    public static final int ID_SKULL_VIRUS = 60;
    public static final int ID_RABIES_VIRUS = 61;
    public static final int ID_POLAR_VIRUS = 62;
    public static final int ID_AER_VIRUS = 63;
    public static final int ID_SLIME_VIRUS = 64;
    public static final int ID_DESERT_VIRUS = 65;

    public static final int ID_AP_SYMBIOSIS = 66;
    public static final int ID_EW_SYMBIOSIS = 67;
    public static final int ID_OV_SYMBIOSIS = 68;
    public static final int ID_RS_SYMBIOSIS = 69;

    public static int DURATION_BLEEDING;
    public static int CHANCE_BLEEDING;

    public static int DURATION_SUNSTROKE;
    public static int CHANCE_SUNSTROKE;

    public static int DURATION_COLD;
    public static int CHANCE_COLD;

    public static int DURATION_FRACTURE;
    public static int CHANCE_FRACTURE;

    public static int DURATION_FATIGUE;
    public static int CHANCE_FATIGUE;
    public static int IMMUNITY_LEVEL_FATIGUE;

    public static int DURATION_LACKOFBLOOD;
    public static int CHANCE_LACKOFBLOOD;
    public static int BLOOD_LEVEL_LACKOFBLOOD;

    public static int DURATION_FOODPOISONING;
    public static int CHANCE_FOODPOISONING;

    public static int DURATION_BLACKBACTERIA;
    public static int DURATION_SWAMPBACTERIA;
    public static int DURATION_GLOWINGBACTERIA;
    public static int DURATION_WATERBACTERIA;
    public static int DURATION_ENDERBACTERIA;
    public static int DURATION_CACTUSBACTERIA;
    public static int DURATION_BONEBACTERIA;
    public static int DURATION_TERABACTERIA;
    public static int DURATION_MYCELIUMBACTERIA;
    public static int DURATION_SMALLGREENBACTERIA;
    public static int DURATION_BIGGREENBACTERIA;
    public static int DURATION_SEABACTERIA;
    public static int DURATION_CLONEBACTERIA;

    public static int CHANCE_BLACKBACTERIA;
    public static int CHANCE_SWAMPBACTERIA;
    public static int CHANCE_GLOWINGBACTERIAGS;
    public static int CHANCE_GLOWINGBACTERIABL;
    public static int CHANCE_WATERBACTERIA;
    public static int CHANCE_ENDERBACTERIA;
    public static int CHANCE_CACTUSBACTERIA;
    public static int CHANCE_BONEBACTERIA;
    public static int CHANCE_TERABACTERIA;
    public static int CHANCE_MYCELIUMBACTERIA;
    public static int CHANCE_SMALLGREENBACTERIA;
    public static int CHANCE_BIGGREENBACTERIA;
    public static int CHANCE_SEABACTERIA;
    public static int CHANCE_CLONEBACTERIA;

    public static int REVISION_CHANCE;

    //viruses
    public static int STANDART_VIRUS_DURATION;

    public static int CHANCE_GIANT_VIRUS;
    public static int DURATION_GIANT_VIRUS;
    public static int CHANCE_PTERO_VIRUS;
    public static int CHANCE_ENDER_VIRUS;
    public static int CHANCE_BRAIN_VIRUS;
    public static int CHANCE_WITHER_VIRUS;
    public static int CHANCE_VAMPIRE_VIRUS;
    public static int CHANCE_BLOOD_VIRUS;
    public static int CHANCE_CREEPER_VIRUS;
    public static int CHANCE_RED_VIRUS;
    public static int CHANCE_OCEAN_VIRUS;
    public static int CHANCE_SKULL_VIRUS;
    public static int DURATION_SKULL_VIRUS;
    public static int CHANCE_RABIES_VIRUS;
    public static int CHANCE_POLAR_VIRUS;
    public static int CHANCE_AER_VIRUS;
    public static int IMMUNITY_LEVEL_AER_VIRUS;
    public static int CHANCE_DESERT_VIRUS;
    public static int DURATION_DESERT_VIRUS;

    public static int CHANCE_MUTATION_PROCESS;
    public static int CHANCE_MUTATION_VIRUS;
    public static int MUTATION_PROCESS_DELAY;

    public static final String DNA_GIANT_VIRUS = "1:12:78:1:94:5:96:3";
    public static final String DNA_PTERO_VIRUS = "74:3:93:8:27:5:43:2";
    public static final String DNA_ENDER_VIRUS = "95:4:3:71:56:2:1:83";
    public static final String DNA_BRAIN_VIRUS = "74:3:92:4:75:6:12:9";
    public static final String DNA_WITHER_VIRUS = "5:47:04:7:38:5:3:45";
    public static final String DNA_VAMPIRE_VIRUS = "78:3:19:5:4:85:94:0";
    public static final String DNA_BLOOD_VIRUS = "9:61:74:3:3:21:75:1";
    public static final String DNA_CREEPER_VIRUS = "18:4:9:78:3:14:81:2";
    public static final String DNA_RED_VIRUS = "74:3:18:6:5:32:25:6";
    public static final String DNA_OCEAN_VIRUS = "75:6:45:3:09:6:13:8";
    public static final String DNA_SKULL_VIRUS = "13:1:8:74:2:78:48:5";
    public static final String DNA_RABIES_VIRUS = "48:7:9:12:41:2:1:20";
    public static final String DNA_POLAR_VIRUS = "67:1:84:3:36:8:94:1";
    public static final String DNA_AER_VIRUS = "15:4:67:2:3:71:54:1";
    public static final String DNA_DESERT_VIRUS = "48:6:35:6:9:55:17:5";
    public static final String DNA_AP_SYMBIOSIS = "35:2:8:95:35:9:19:5";
    public static final String DNA_EW_SYMBIOSIS = "7:80:52:3:69:2:48:3";
    public static final String DNA_OV_SYMBIOSIS = "1:45:76:4:93:1:82:3";
    public static final String DNA_RS_SYMBIOSIS = "74:1:19:3:57:3:47:1";

    //Other
    public static int CHANCE_LABORATORY_GEN;


    public static void init(){

        DURATION_BLEEDING = 6000;
        CHANCE_BLEEDING = 5;

        DURATION_SUNSTROKE = 12000;
        CHANCE_SUNSTROKE = 35;

        DURATION_COLD = 32000;
        CHANCE_COLD = 20;

        DURATION_FRACTURE = 12000;
        CHANCE_FRACTURE = 10;

        DURATION_FATIGUE = -1;
        CHANCE_FATIGUE = 0;
        IMMUNITY_LEVEL_FATIGUE = 30;

        DURATION_LACKOFBLOOD = -1;
        CHANCE_LACKOFBLOOD = 0;
        BLOOD_LEVEL_LACKOFBLOOD = 40;

        DURATION_FOODPOISONING = 32000;
        CHANCE_FOODPOISONING = 15;

        DURATION_BLACKBACTERIA = 36000;
        CHANCE_BLACKBACTERIA = 100;

        DURATION_SWAMPBACTERIA = -1;
        CHANCE_SWAMPBACTERIA = 10;

        DURATION_GLOWINGBACTERIA = 12000;
        CHANCE_GLOWINGBACTERIAGS = 5;
        CHANCE_GLOWINGBACTERIABL = 35;

        DURATION_WATERBACTERIA = 18000;
        CHANCE_WATERBACTERIA = 25;

        DURATION_ENDERBACTERIA = -1;
        CHANCE_ENDERBACTERIA = 100;

        DURATION_CACTUSBACTERIA = 12000;
        CHANCE_CACTUSBACTERIA = 20;

        DURATION_BONEBACTERIA = -1;
        CHANCE_BONEBACTERIA = 5;

        DURATION_TERABACTERIA = 12000;
        CHANCE_TERABACTERIA = 10;

        DURATION_MYCELIUMBACTERIA = -1;
        CHANCE_MYCELIUMBACTERIA = 25;

        DURATION_SMALLGREENBACTERIA = -1;
        CHANCE_SMALLGREENBACTERIA = 35;

        DURATION_BIGGREENBACTERIA = -1;
        CHANCE_BIGGREENBACTERIA = 20;

        DURATION_SEABACTERIA = 32000;
        CHANCE_SEABACTERIA = 25;

        DURATION_CLONEBACTERIA = -1;
        CHANCE_CLONEBACTERIA = 10;

        REVISION_CHANCE = 85;

        STANDART_VIRUS_DURATION = -1;

        CHANCE_GIANT_VIRUS = 5;
        DURATION_GIANT_VIRUS = 6000;

        CHANCE_PTERO_VIRUS = 5;

        CHANCE_ENDER_VIRUS = 45;

        CHANCE_BRAIN_VIRUS = 15;

        CHANCE_WITHER_VIRUS = 15;

        CHANCE_VAMPIRE_VIRUS = 35;

        CHANCE_BLOOD_VIRUS = 35;

        CHANCE_CREEPER_VIRUS = 20;

        CHANCE_RED_VIRUS = 20;

        CHANCE_OCEAN_VIRUS = 5;

        CHANCE_SKULL_VIRUS = 10;
        DURATION_SKULL_VIRUS = 32000;

        CHANCE_RABIES_VIRUS = 5;

        CHANCE_POLAR_VIRUS = 20;

        CHANCE_AER_VIRUS = 70;
        IMMUNITY_LEVEL_AER_VIRUS = 50;

        CHANCE_DESERT_VIRUS = 25;
        DURATION_DESERT_VIRUS = 16000;

        CHANCE_MUTATION_PROCESS = 30;
        MUTATION_PROCESS_DELAY = 2400;
        CHANCE_MUTATION_VIRUS = 15;

        CHANCE_LABORATORY_GEN = 10;
    }
}
