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
import net.minecraft.item.ItemPotion;
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

    public static void initItemsServer(){
        GARLIC_BULB = new GarlicBulb("garlicbulb", BlockRegistry.GARLIC);
        DILL_SEED = new SeedBionisation("dillseed", BlockRegistry.DILL);
        DILL_BUNCH = new ItemBionisation("dillBunch");
        YARROW_SEED = new SeedBionisation("yarrowseed", BlockRegistry.YARROW);
        YARROW_BUNCH = new ItemBionisation("yarrowbunch");
        IMMUNITY_RECEIVER = new ImmunityReceiver();
        BANDAGE = new Bandage();
        DISINFECTANT_FLUID = new ItemBionisation("disinfectantfluid", 1);
        VACCINE_INJECTOR = new VaccineInjector();
        VIAL = new Vial();
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
        registerItemRender(BIO_HELMET);
        registerItemRender(BIO_CHEST);
        registerItemRender(BIO_LEGGINGS);
        registerItemRender(BIO_BOOTS);
        registerItemRender(POTION_CURE);
        registerItemRender(DISINFECTANT_FLUID);
        registerItemRender(VACCINE_INJECTOR);
        registerItemRender(VIAL);
    }

    private static void registerItemRender(Item item){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
