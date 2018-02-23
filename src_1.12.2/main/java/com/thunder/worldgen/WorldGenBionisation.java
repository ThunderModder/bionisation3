package com.thunder.worldgen;

import com.thunder.biome.InfectedBiome;
import com.thunder.block.BlockRegistry;
import com.thunder.block.FlowerBionisation;
import com.thunder.structure.BStructure;
import com.thunder.util.Constants;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;


public class WorldGenBionisation implements IWorldGenerator{

    public static void init(){
        GameRegistry.registerWorldGenerator(new WorldGenBionisation(), 0);
        GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> BStructure.generateLaboratory(Constants.CHANCE_LABORATORY_GEN, world, random, chunkX, chunkZ), 1);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()){
            case -1:
                generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                break;
            case 1:
                generateEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                break;
            case 0:
                generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                break;
        }
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
        generateFlower(BlockRegistry.GARLIC, 8, world, random, chunkX, chunkZ);
        Biome biome = world.getBiome(new BlockPos(chunkX << 4, world.getActualHeight(), chunkZ << 4));
        if(biome == Biomes.OCEAN || biome == Biomes.DEEP_OCEAN || biome == Biomes.RIVER)
            generateFlower(BlockRegistry.LOTUS, 32, world, random, chunkX, chunkZ);
        if(biome == InfectedBiome.INFECTED_BIOME){
            generateFlower(BlockRegistry.ENDER_FLOWER, 64, world, random, chunkX, chunkZ);
            generateFlower(BlockRegistry.LIVING_BONE, 32, world, random, chunkX, chunkZ);
            generateFlower(BlockRegistry.ANCIENT_FLOWER, 64, world, random, chunkX, chunkZ);
        }else{
            generateFlower(BlockRegistry.ENDER_FLOWER, 8, world, random, chunkX, chunkZ);
            generateFlower(BlockRegistry.LIVING_BONE, 8, world, random, chunkX, chunkZ);
            generateFlower(BlockRegistry.ANCIENT_FLOWER, 16, world, random, chunkX, chunkZ);
        }
        if(biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT)
            generateFlower(BlockRegistry.DESERT_FLOWER, 16, world, random, chunkX, chunkZ);
        if(biome == Biomes.ICE_PLAINS || biome == Biomes.ICE_MOUNTAINS || biome == Biomes.MUTATED_ICE_FLATS || biome == Biomes.TAIGA || biome == Biomes.TAIGA_HILLS || biome == Biomes.COLD_TAIGA || biome == Biomes.COLD_TAIGA_HILLS || biome == Biomes.REDWOOD_TAIGA || biome == Biomes.REDWOOD_TAIGA_HILLS || biome == Biomes.MUTATED_TAIGA || biome == Biomes.MUTATED_TAIGA_COLD || biome == Biomes.MUTATED_REDWOOD_TAIGA || biome == Biomes.MUTATED_REDWOOD_TAIGA_HILLS) {
            generateFlower(BlockRegistry.YARROW, 16, world, random, chunkX, chunkZ);
            generateFlower(BlockRegistry.CRYSTAL_FLOWER, 32, world, random, chunkX, chunkZ);
        }else  generateFlower(BlockRegistry.DILL, 16, world, random, chunkX, chunkZ);

        //BStructure.generateLaboratory(20, world, random, chunkX, chunkZ);
    }

    public void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
        generateFlowerHE(BlockRegistry.PHOENIX_FLOWER, 32, world, random, chunkX, chunkZ);
    }

    public void generateEnd(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
        generateFlowerHE(BlockRegistry.ENDER_FLOWER, 32, world, random, chunkX, chunkZ);
    }

    private void generateFlower(FlowerBionisation block, int rarity, World world, Random random, int chunkX, int chunkZ){
        int x = (chunkX << 4) + random.nextInt(16) + 8;
        int y = random.nextInt(128);
        int z = (chunkZ << 4) + random.nextInt(16) + 8;
        (new FlowerGenerator(block, rarity)).generate(world, random, new BlockPos(x, y, z));
    }

    private void generateFlowerHE(FlowerBionisation block, int rarity, World world, Random random, int chunkX, int chunkZ){
        int x = (chunkX << 4) + random.nextInt(16) + 8;
        int y = random.nextInt(128);
        int z = (chunkZ << 4) + random.nextInt(16) + 8;
        (new FlowerGeneratorHE(block, rarity)).generate(world, random, new BlockPos(x, y, z));
    }
}
