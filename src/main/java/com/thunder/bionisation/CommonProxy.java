package com.thunder.bionisation;

import com.thunder.biome.InfectedBiome;
import com.thunder.block.BlockRegistry;
import com.thunder.event.BloodEventHandler;
import com.thunder.event.CapabilityEventHandler;
import com.thunder.event.EffectEventHandler;
import com.thunder.gui.BGuiHandler;
import com.thunder.item.ItemRegistry;
import com.thunder.jei.recipes.DNAFormerRecipes;
import com.thunder.jei.recipes.HerbalStationRecipes;
import com.thunder.jei.recipes.VaccineCreatorRecipes;
import com.thunder.jei.recipes.VirusReplicatorRecipes;
import com.thunder.mob.BioMob;
import com.thunder.mob.BioMobStorage;
import com.thunder.mob.IBioMob;
import com.thunder.network.NetworkHandler;
import com.thunder.player.BioPlayer;
import com.thunder.player.BioPlayerStorage;
import com.thunder.player.IBioPlayer;
import com.thunder.recipe.RecipeRegistry;
import com.thunder.sound.SoundHandler;
import com.thunder.tileentity.*;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import com.thunder.worldgen.WorldGenBionisation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class CommonProxy {

    public static CreativeTabs tabBionisation = new BCreativeTab();

    public void preInit() {
        Constants.init();
        BlockRegistry.initBlocksServer();
        ItemRegistry.initItemsServer();
        InfectedBiome.init();
        WorldGenBionisation.init();
        NetworkHandler.init();
        RecipeRegistry.init();
        GameRegistry.registerTileEntity(TileHerbalStation.class, Utilities.getModIdString("herbal_station"));
        GameRegistry.registerTileEntity(TileDisinfector.class, Utilities.getModIdString("disinfector"));
        GameRegistry.registerTileEntity(TileVaccineCreator.class, Utilities.getModIdString("vaccine_creator"));
        GameRegistry.registerTileEntity(TileDNAFormer.class, Utilities.getModIdString("dna_former"));
        GameRegistry.registerTileEntity(TileVirusReplicator.class, Utilities.getModIdString("virus_replicator"));
        //recipe init
        HerbalStationRecipes.initHBRecipes();
        VaccineCreatorRecipes.initVCRecipes();
        DNAFormerRecipes.initDNAFRecipes();
        VirusReplicatorRecipes.initVRRecipes();
    }

    public void init() {
        CapabilityManager.INSTANCE.register(IBioPlayer.class, new BioPlayerStorage(), BioPlayer.class);
        CapabilityManager.INSTANCE.register(IBioMob.class, new BioMobStorage(), BioMob.class);
        NetworkRegistry.INSTANCE.registerGuiHandler(Bionisation.INSTANCE, new BGuiHandler());
        EffectEventHandler.register();
        CapabilityEventHandler.register();
        BloodEventHandler.register();
        SoundHandler.init();
    }

    public void postInit() {

    }
}
