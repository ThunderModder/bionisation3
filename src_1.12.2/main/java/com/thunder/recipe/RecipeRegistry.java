package com.thunder.recipe;


import com.thunder.bionisation.Information;
import com.thunder.block.BlockRegistry;
import com.thunder.item.ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeRegistry {

    public static void  init(){

        //ender core
        GameRegistry.addShapelessRecipe(new ResourceLocation(Information.MOD_ID + ":" + "ender_core"), new ResourceLocation(Information.MOD_ID + ":" + "ender_core"), new ItemStack(ItemRegistry.ENDER_CORE) , Ingredient.fromItem(ItemRegistry.ENDER_CORE), Ingredient.fromItem(Items.ENDER_PEARL));
        //glowing liquid
        GameRegistry.addShapelessRecipe(new ResourceLocation(Information.MOD_ID + ":" + "glowing_liquid"), new ResourceLocation(Information.MOD_ID + ":" + "glowing_liquid"), new ItemStack(ItemRegistry.GLOWING_LIQUID), Ingredient.fromItem(ItemRegistry.STRANGE_LIQUID), Ingredient.fromItem(Items.GLOWSTONE_DUST));
        //book
        GameRegistry.addShapelessRecipe(new ResourceLocation(Information.MOD_ID + ":" + "guide_book"), new ResourceLocation(Information.MOD_ID + ":" + "guide_book"), new ItemStack(ItemRegistry.GUIDE_BOOK), Ingredient.fromItem(Items.BOOK), Ingredient.fromItem(ItemRegistry.GARLIC_BULB));
        //cloth
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "cloth"), new ResourceLocation(Information.MOD_ID + ":" + "cloth"), new ItemStack(ItemRegistry.CLOTH), "###", "## ", "   ", '#', Items.STRING);
        //armor
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "bio_helmet"), new ResourceLocation(Information.MOD_ID + ":" + "bio_helmet"), new ItemStack(ItemRegistry.BIO_HELMET), "###", "#G#", "   ", '#', ItemRegistry.CLOTH, 'G', Blocks.GLASS);
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "bio_chest"), new ResourceLocation(Information.MOD_ID + ":" + "bio_chest"), new ItemStack(ItemRegistry.BIO_CHEST), "# #", "###", "###", '#', ItemRegistry.CLOTH);
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "bio_leggings"), new ResourceLocation(Information.MOD_ID + ":" + "bio_leggings"), new ItemStack(ItemRegistry.BIO_LEGGINGS), "###", "# #", "# #", '#', ItemRegistry.CLOTH);
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "bio_boots"), new ResourceLocation(Information.MOD_ID + ":" + "bio_boots"), new ItemStack(ItemRegistry.BIO_BOOTS), "   ", "# #", "#C#", '#', ItemRegistry.CLOTH, 'C', Items.CLAY_BALL);
        //processor
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "processor"), new ResourceLocation(Information.MOD_ID + ":" + "processor"), new ItemStack(ItemRegistry.PROCESSOR), "RIR", "IGI", "RIR", 'R', Items.REDSTONE, 'G', Items.GOLD_INGOT, 'I', Items.IRON_INGOT);
        //herbal station
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "herbal_station"), new ResourceLocation(Information.MOD_ID + ":" + "herbal_station"), new ItemStack(BlockRegistry.HERBAL_STATION), "DHD", "RFR", "DPD", 'D', new ItemStack(Blocks.STONE, 1, 3), 'H', Blocks.HOPPER, 'R', Items.REDSTONE, 'P', ItemRegistry.PROCESSOR, 'F', Blocks.FURNACE);
        //disinfector
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "disinfector"), new ResourceLocation(Information.MOD_ID + ":" + "disinfector"), new ItemStack(BlockRegistry.DISINFECTOR), "DHD", "PFP", "DCD", 'D', new ItemStack(Blocks.STONE, 1, 3), 'H', Blocks.HOPPER, 'C', Blocks.CHEST, 'P', ItemRegistry.PROCESSOR, 'F', Blocks.FURNACE);
        //vaccine creator
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "vaccine_creator"), new ResourceLocation(Information.MOD_ID + ":" + "vaccine_creator"), new ItemStack(BlockRegistry.VACCINE_CREATOR), "DHD", "PFP", "DND", 'D', new ItemStack(Blocks.STONE, 1, 3), 'H', Blocks.HOPPER, 'N', ItemRegistry.DNA_PATTERN, 'P', ItemRegistry.PROCESSOR, 'F', Blocks.FURNACE);
        //dna former
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "dna_former"), new ResourceLocation(Information.MOD_ID + ":" + "dna_former"), new ItemStack(BlockRegistry.DNA_FORMER), "DPD", "NFN", "DSD", 'D', new ItemStack(Blocks.STONE, 1, 3), 'P', ItemRegistry.PROCESSOR, 'N', Items.DIAMOND, 'S', ItemRegistry.ENDER_CORE, 'F', Blocks.FURNACE);
        //virus replicator
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "virus_replicator"), new ResourceLocation(Information.MOD_ID + ":" + "virus_replicator"), new ItemStack(BlockRegistry.VIRUS_REPLICATOR), "DND", "PFP", "DSD", 'D', new ItemStack(Blocks.STONE, 1, 3), 'P', ItemRegistry.PROCESSOR, 'N', Items.DIAMOND, 'S', Items.NETHER_STAR, 'F', Blocks.FURNACE);
        //immunity receiver
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "immunity_receiver"), new ResourceLocation(Information.MOD_ID + ":" + "immunity_receiver"), new ItemStack(ItemRegistry.IMMUNITY_RECEIVER), "CPC", "CRC", " G ", 'C', Items.CLAY_BALL, 'P', ItemRegistry.PROCESSOR, 'R', Items.REDSTONE, 'G', Items.GOLD_NUGGET);
        //bandage
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "bandage"), new ResourceLocation(Information.MOD_ID + ":" + "bandage"), new ItemStack(ItemRegistry.BANDAGE), "CC ", "CS ", "   ", 'C', ItemRegistry.CLOTH, 'S', Items.STRING);
        //disinfectant fluid
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "disinfectant_fluid"), new ResourceLocation(Information.MOD_ID + ":" + "disinfectant_fluid"), new ItemStack(ItemRegistry.DISINFECTANT_FLUID), "PL ", "CA ", "   ", 'P', Items.POTIONITEM, 'L', BlockRegistry.LIVING_BONE, 'C', BlockRegistry.CRYSTAL_FLOWER, 'A', BlockRegistry.ANCIENT_FLOWER);
        //vaccine injector
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "vaccine_injector"), new ResourceLocation(Information.MOD_ID + ":" + "vaccine_injector"), new ItemStack(ItemRegistry.VACCINE_INJECTOR), "CPC", "CBC", " G ", 'C', Items.CLAY_BALL, 'P', ItemRegistry.PROCESSOR, 'B', Items.GLASS_BOTTLE, 'G', Items.GOLD_NUGGET);
        //vial
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "vial"), new ResourceLocation(Information.MOD_ID + ":" + "vial"), new ItemStack(ItemRegistry.VIAL), "CGC", "CBC", "CRC", 'C', Items.CLAY_BALL, 'G', Items.GOLD_NUGGET, 'B', Items.GLASS_BOTTLE, 'R', Items.REDSTONE);
        //dna pattern
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "dna_pattern"), new ResourceLocation(Information.MOD_ID + ":" + "dna_pattern"), new ItemStack(ItemRegistry.DNA_PATTERN), "CGC", "CEC", "CVC", 'C', Items.CLAY_BALL, 'G', Blocks.GLASS, 'E', Items.EMERALD, 'V', ItemRegistry.VIAL);
        //virus sprayer
        GameRegistry.addShapedRecipe(new ResourceLocation(Information.MOD_ID + ":" + "virus_sprayer"), new ResourceLocation(Information.MOD_ID + ":" + "virus_sprayer"), new ItemStack(ItemRegistry.VIRUS_SPRAYER), "CRC", "CPC", "CGC", 'C', Items.CLAY_BALL, 'G', Items.GLASS_BOTTLE, 'R', Items.REDSTONE, 'P', ItemRegistry.PROCESSOR);
    }
}
