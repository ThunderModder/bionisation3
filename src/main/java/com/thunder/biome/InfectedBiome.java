package com.thunder.biome;

import com.thunder.bionisation.Config;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class InfectedBiome extends Biome {
    public static Biome INFECTED_BIOME;

    public static void init(){
        INFECTED_BIOME = new InfectedBiome(new BiomeProperties("Infected Forest").setHeightVariation(0.9f).setWaterColor(0x058230).setTemperature(0.7F).setRainfall(0.8F));
        INFECTED_BIOME.setRegistryName("infected_forest");
        ForgeRegistries.BIOMES.register(INFECTED_BIOME);
        BiomeDictionary.addTypes(INFECTED_BIOME, BiomeDictionary.Type.FOREST);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(INFECTED_BIOME, Config.biomeSpawnWeight));
        BiomeManager.addSpawnBiome(INFECTED_BIOME);
    }

    private static final IBlockState JUNGLE_LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
    private static final IBlockState JUNGLE_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private static final IBlockState OAK_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    public InfectedBiome(BiomeProperties properties) {
        super(properties);
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.MOSSY_COBBLESTONE.getDefaultState();
        this.decorator.deadBushPerChunk = 12;
        this.decorator.bigMushroomsPerChunk = 5;
        this.decorator.flowersPerChunk = 16;
        this.decorator.extraTreeChance = 1.0f;
        this.decorator.grassPerChunk = 32;
        this.decorator.treesPerChunk = 16;
        this.decorator.generateFalls = true;
        this.spawnableWaterCreatureList.clear();
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return (WorldGenAbstractTree)(rand.nextInt(10) == 0 ? BIG_TREE_FEATURE : (rand.nextInt(2) == 0 ? new WorldGenShrub(JUNGLE_LOG, OAK_LEAF) : (rand.nextInt(3) == 0 ? new WorldGenMegaJungle(false, 10, 20, JUNGLE_LOG, JUNGLE_LEAF) : new WorldGenTrees(false, 4 + rand.nextInt(7), JUNGLE_LOG, JUNGLE_LEAF, true))));
    }

    @Override
    public float getSpawningChance() {
        return 0.2F;
    }

    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float currentTemperature) {
        return 0x000000;
    }

    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos) {
        return 0x40776e;
    }

    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos) {
        return 0x1d2b27;
    }
}
