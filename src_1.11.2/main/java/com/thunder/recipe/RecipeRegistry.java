package com.thunder.recipe;


import com.thunder.block.BlockRegistry;
import com.thunder.item.ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeRegistry {

    public static void  init(){

        //ender core
        GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.ENDER_CORE), ItemRegistry.ENDER_SUBSTANCE, Items.ENDER_PEARL);
        //glowing liquid
        GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.GLOWING_LIQUID), ItemRegistry.STRANGE_LIQUID, Items.GLOWSTONE_DUST);
        //book
        GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.GUIDE_BOOK), Items.BOOK, ItemRegistry.GARLIC_BULB);
        //cloth
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.CLOTH), "###", "## ", "   ", '#', Items.STRING);
        //armor
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.BIO_HELMET), "###", "#G#", "   ", '#', ItemRegistry.CLOTH, 'G', Blocks.GLASS);
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.BIO_CHEST), "# #", "###", "###", '#', ItemRegistry.CLOTH);
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.BIO_LEGGINGS), "###", "# #", "# #", '#', ItemRegistry.CLOTH);
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.BIO_BOOTS), "   ", "# #", "#C#", '#', ItemRegistry.CLOTH, 'C', Items.CLAY_BALL);
        //processor
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.PROCESSOR), "RIR", "IGI", "RIR", 'R', Items.REDSTONE, 'G', Items.GOLD_INGOT, 'I', Items.IRON_INGOT);
        //herbal station
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.HERBAL_STATION), "DHD", "RFR", "DPD", 'D', new ItemStack(Blocks.STONE, 1, 3), 'H', Blocks.HOPPER, 'R', Items.REDSTONE, 'P', ItemRegistry.PROCESSOR, 'F', Blocks.FURNACE);
        //disinfector
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.DISINFECTOR), "DHD", "PFP", "DCD", 'D', new ItemStack(Blocks.STONE, 1, 3), 'H', Blocks.HOPPER, 'C', Blocks.CHEST, 'P', ItemRegistry.PROCESSOR, 'F', Blocks.FURNACE);
        //vaccine creator
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.VACCINE_CREATOR), "DHD", "PFP", "DND", 'D', new ItemStack(Blocks.STONE, 1, 3), 'H', Blocks.HOPPER, 'N', ItemRegistry.DNA_PATTERN, 'P', ItemRegistry.PROCESSOR, 'F', Blocks.FURNACE);
        //dna former
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.DNA_FORMER), "DPD", "NFN", "DSD", 'D', new ItemStack(Blocks.STONE, 1, 3), 'P', ItemRegistry.PROCESSOR, 'N', Items.DIAMOND, 'S', ItemRegistry.ENDER_CORE, 'F', Blocks.FURNACE);
        //virus replicator
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.VIRUS_REPLICATOR), "DND", "PFP", "DSD", 'D', new ItemStack(Blocks.STONE, 1, 3), 'P', ItemRegistry.PROCESSOR, 'N', Items.DIAMOND, 'S', Items.NETHER_STAR, 'F', Blocks.FURNACE);
        //immunity receiver
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.IMMUNITY_RECEIVER), "CPC", "CRC", " G ", 'C', Items.CLAY_BALL, 'P', ItemRegistry.PROCESSOR, 'R', Items.REDSTONE, 'G', Items.GOLD_NUGGET);
        //bandage
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.BANDAGE), "CC ", "CS ", "   ", 'C', ItemRegistry.CLOTH, 'S', Items.STRING);
        //disinfectant fluid
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.DISINFECTANT_FLUID), "PL ", "CA ", "   ", 'P', Items.POTIONITEM, 'L', BlockRegistry.LIVING_BONE, 'C', BlockRegistry.CRYSTAL_FLOWER, 'A', BlockRegistry.ANCIENT_FLOWER);
        //vaccine injector
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.VACCINE_INJECTOR), "CPC", "CBC", " G ", 'C', Items.CLAY_BALL, 'P', ItemRegistry.PROCESSOR, 'B', Items.GLASS_BOTTLE, 'G', Items.GOLD_NUGGET);
        //vial
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.VIAL), "CGC", "CBC", "CRC", 'C', Items.CLAY_BALL, 'G', Items.GOLD_NUGGET, 'B', Items.GLASS_BOTTLE, 'R', Items.REDSTONE);
        //dna pattern
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.DNA_PATTERN), "CGC", "CEC", "CVC", 'C', Items.CLAY_BALL, 'G', Blocks.GLASS, 'E', Items.EMERALD, 'V', ItemRegistry.VIAL);
        //virus sprayer
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.VIRUS_SPRAYER), "CRC", "CPC", "CGC", 'C', Items.CLAY_BALL, 'G', Items.GLASS_BOTTLE, 'R', Items.REDSTONE, 'P', ItemRegistry.PROCESSOR);
    }
}
