package com.thunder.block;

import com.thunder.block.crop.Dill;
import com.thunder.block.crop.Garlic;
import com.thunder.block.crop.Yarrow;
import com.thunder.block.machine.BlockBMachine;
import com.thunder.gui.BGuiHandler;
import com.thunder.tileentity.*;
import com.thunder.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumPlantType;


public class BlockRegistry {

    public static GrowableBionisation GARLIC;
    public static GrowableBionisation DILL;
    public static GrowableBionisation YARROW;

    public static FlowerBionisation ENDER_FLOWER;
    public static FlowerBionisation PHOENIX_FLOWER;
    public static FlowerBionisation DESERT_FLOWER;
    public static FlowerBionisation ANCIENT_FLOWER;
    public static FlowerBionisation LOTUS;
    public static FlowerBionisation LIVING_BONE;
    public static FlowerBionisation CRYSTAL_FLOWER;

    public static BlockBMachine HERBAL_STATION;
    public static BlockBMachine DISINFECTOR;
    public static BlockBMachine VACCINE_CREATOR;
    public static BlockBMachine DNA_FORMER;
    public static BlockBMachine VIRUS_REPLICATOR;

    public static void initBlocksServer(){
        HERBAL_STATION = new BlockBMachine("blockherbalstation", BGuiHandler.HERBAL_STATION_GUI, TileHerbalStation.class);
        DISINFECTOR = new BlockBMachine("blockdisinfector", BGuiHandler.DISINFECTOR_GUI, TileDisinfector.class);
        VACCINE_CREATOR = new BlockBMachine("blockvaccinecreator", BGuiHandler.VACCINE_CREATOR_GUI, TileVaccineCreator.class);
        DNA_FORMER = new BlockBMachine("blockdnaformer", BGuiHandler.DNA_FORMER_GUI, TileDNAFormer.class);
        VIRUS_REPLICATOR = new BlockBMachine("blockvirusreplicator", BGuiHandler.VIRUS_REPLICATOR_GUI, TileVirusReplicator.class);
        GARLIC = new Garlic("garlic", Constants.STANDARTAXIS1_AABB, EnumPlantType.Plains);
        DILL = new Dill("dill", Constants.STANDARTAXIS3_AABB, EnumPlantType.Plains);
        YARROW = new Yarrow("yarrow", Constants.STANDARTAXIS2_AABB, EnumPlantType.Plains);
        ENDER_FLOWER = new FlowerBionisation("enderflower", Constants.STANDARTAXIS1_AABB, EnumPlantType.Plains, true, Blocks.GRASS, Blocks.END_STONE);
        PHOENIX_FLOWER = new FlowerBionisation("phoenixflower", Constants.STANDARTAXIS3_AABB, EnumPlantType.Plains, true, Blocks.NETHERRACK);
        DESERT_FLOWER = new FlowerBionisation("desertflower", Constants.STANDARTAXIS2_AABB, EnumPlantType.Plains, true, Blocks.SAND);
        ANCIENT_FLOWER = new FlowerBionisation("ancientflower", Constants.STANDARTAXIS1_AABB, EnumPlantType.Plains, true, Blocks.GRASS);
        LOTUS = new FlowerBionisation("lotus", Constants.STANDARTAXIS3_AABB, EnumPlantType.Plains, true, Blocks.WATER);
        LIVING_BONE = new FlowerBionisation("livingbone", Constants.STANDARTAXIS2_AABB, EnumPlantType.Plains, true, Blocks.GRASS);
        CRYSTAL_FLOWER = new FlowerBionisation("crystalflower", Constants.STANDARTAXIS2_AABB, EnumPlantType.Plains, true, Blocks.GRASS, Blocks.ICE);
    }

    public static void initBlocksClient(){
        registerBlockRender(HERBAL_STATION);
        registerBlockRender(ENDER_FLOWER);
        registerBlockRender(PHOENIX_FLOWER);
        registerBlockRender(DESERT_FLOWER);
        registerBlockRender(ANCIENT_FLOWER);
        registerBlockRender(LOTUS);
        registerBlockRender(LIVING_BONE);
        registerBlockRender(CRYSTAL_FLOWER);
        registerBlockRender(DISINFECTOR);
        registerBlockRender(VACCINE_CREATOR);
        registerBlockRender(DNA_FORMER);
        registerBlockRender(VIRUS_REPLICATOR);
    }

    private static void registerBlockRender(Block block){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }


}
