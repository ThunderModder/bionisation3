package com.thunder.item;

import com.thunder.bionisation.Information;
import com.thunder.block.BlockRegistry;
import com.thunder.item.armor.BioArmor;
import com.thunder.item.crop.GarlicBulb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;


public class ItemRegistry {

    public static ItemArmor.ArmorMaterial BIO_MATERIAL = EnumHelper.addArmorMaterial("BIO", Information.MOD_ID + ":bio", 20, new int[]{2, 7, 4, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);


    public static ItemBionisation IMMUNITY_RECEIVER;
    public static ItemBionisation BANDAGE;
    public static ItemBionisation CREATIVE_VIAL;

    public static SeedBionisation GARLIC_BULB;
    public static SeedBionisation DILL_SEED;
    public static SeedBionisation YARROW_SEED;

    public static ItemBionisation YARROW_BUNCH;
    public static ItemBionisation DILL_BUNCH;

    public static Item BIO_HELMET;
    public static Item BIO_CHEST;
    public static Item BIO_LEGGINGS;
    public static Item BIO_BOOTS;

    public static ItemBionisation POTION_CURE;

    public static ItemBionisation DISINFECTANT_FLUID;
    public static ItemBionisation VACCINE_INJECTOR;
    public static ItemBionisation VIAL;
    public static ItemBionisation DNA_PATTERN;

    public static ItemBionisation BAT_WING;
    public static ItemBionisation WOLFS_TOOTH;
    public static ItemBionisation ITEM_BLOOD;
    public static ItemBionisation SPECTRAL_DUST;
    public static ItemBionisation SPIDER_LEG;
    public static ItemBionisation ENDER_CORE;
    public static ItemBionisation DARK_HEART;
    public static ItemBionisation GUARDIAN_BRAIN;
    public static ItemBionisation GLOWING_LIQUID;
    public static ItemBionisation STRANGE_LIQUID;
    public static ItemBionisation CHICKEN_HEAD;
    public static ItemBionisation BLAZE_CORE;
    public static ItemBionisation HEART_OF_CREEPER;
    public static ItemBionisation ENDER_SUBSTANCE;
    public static ItemBionisation GENE_VIAL;

    public static ItemBionisation VIRUS_SPRAYER;

    public static ItemBionisation SYMBIONT_VIAL;

    public static ItemBionisation PROCESSOR;
    public static ItemBionisation CLOTH;

    public static ItemBionisation GUIDE_BOOK;

    public static void initItemsServer(){
        GARLIC_BULB = new GarlicBulb("garlicbulb", BlockRegistry.GARLIC);
        DILL_SEED = new SeedBionisation("dillseed", BlockRegistry.DILL);
        DILL_BUNCH = new ItemBionisation("dillbunch");
        YARROW_SEED = new SeedBionisation("yarrowseed", BlockRegistry.YARROW);
        YARROW_BUNCH = new ItemBionisation("yarrowbunch");
        PROCESSOR = new ItemBionisation("processor", 64);
        CLOTH = new ItemBionisation("cloth", 64);
        GUIDE_BOOK = new GuideBook();
        IMMUNITY_RECEIVER = new ImmunityReceiver();
        BANDAGE = new Bandage();
        DISINFECTANT_FLUID = new ItemBionisation("disinfectantfluid", 1);
        VACCINE_INJECTOR = new VaccineInjector();
        VIAL = new Vial();
        DNA_PATTERN = new DNAPattern();
        BAT_WING = new ItemBionisation("batwing", 16);
        WOLFS_TOOTH = new ItemBionisation("wolfstooth", 16);
        ITEM_BLOOD = new ItemBlood();
        SPECTRAL_DUST = new ItemBionisation("spectraldust", 64);
        SPIDER_LEG = new ItemBionisation("spiderleg", 16);
        ENDER_CORE = new ItemBionisation("endercore", 64);
        DARK_HEART = new ItemBionisation("darkheart", 16);
        GUARDIAN_BRAIN = new ItemBionisation("guardianbrain", 16);
        GLOWING_LIQUID = new ItemBionisation("glowingliquid", 1);
        STRANGE_LIQUID = new ItemBionisation("strangeliquid", 1);
        CHICKEN_HEAD = new ItemBionisation("chickenhead", 16);
        BLAZE_CORE = new ItemBionisation("blazecore", 64);
        HEART_OF_CREEPER = new ItemBionisation("heartofcreeper", 16);
        ENDER_SUBSTANCE = new ItemBionisation("endersubstance", 16);
        GENE_VIAL = new GeneVial();
        VIRUS_SPRAYER = new VirusSprayer();
        SYMBIONT_VIAL = new SymbiontVial();
        BIO_HELMET = new BioArmor("bio_helmet", BIO_MATERIAL, 0, EntityEquipmentSlot.HEAD);
        BIO_CHEST = new BioArmor("bio_chest", BIO_MATERIAL, 0, EntityEquipmentSlot.CHEST);
        BIO_LEGGINGS = new BioArmor("bio_leggings", BIO_MATERIAL, 0, EntityEquipmentSlot.LEGS);
        BIO_BOOTS = new BioArmor("bio_boots", BIO_MATERIAL, 0, EntityEquipmentSlot.FEET);
        CREATIVE_VIAL = new CreativeVial();
        POTION_CURE = new PotionCure();
    }

    public static void initItemsClient(){
        registerItemRender(IMMUNITY_RECEIVER);
        registerItemRender(BANDAGE);
        registerItemRender(CREATIVE_VIAL);
        registerItemRender(GARLIC_BULB);
        registerItemRender(DILL_SEED);
        registerItemRender(YARROW_SEED);
        registerItemRender(YARROW_BUNCH);
        registerItemRender(DILL_BUNCH);
        registerItemRender(PROCESSOR);
        registerItemRender(CLOTH);
        registerItemRender(BIO_HELMET);
        registerItemRender(BIO_CHEST);
        registerItemRender(BIO_LEGGINGS);
        registerItemRender(BIO_BOOTS);
        registerItemRender(POTION_CURE);
        registerItemRender(DISINFECTANT_FLUID);
        registerItemRender(VACCINE_INJECTOR);
        registerItemRender(VIAL);
        registerItemRender(DNA_PATTERN);
        registerItemRender(BAT_WING);
        registerItemRender(WOLFS_TOOTH);
        registerItemRender(ITEM_BLOOD);
        registerItemRender(SPECTRAL_DUST);
        registerItemRender(SPIDER_LEG);
        registerItemRender(ENDER_CORE);
        registerItemRender(DARK_HEART);
        registerItemRender(GUARDIAN_BRAIN);
        registerItemRender(GLOWING_LIQUID);
        registerItemRender(STRANGE_LIQUID);
        registerItemRender(CHICKEN_HEAD);
        registerItemRender(BLAZE_CORE);
        registerItemRender(HEART_OF_CREEPER);
        registerItemRender(ENDER_SUBSTANCE);
        registerItemRender(GENE_VIAL);
        registerItemRender(VIRUS_SPRAYER);
        registerItemRender(SYMBIONT_VIAL);
        registerItemRender(GUIDE_BOOK);
    }

    private static void registerItemRender(Item item){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
