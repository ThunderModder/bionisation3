package com.thunder.worldgen;

import com.thunder.block.BlockRegistry;
import com.thunder.block.FlowerBionisation;
import com.thunder.block.GrowableBionisation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;


public class FlowerGeneratorHE extends WorldGenerator {

    private FlowerBionisation flower;
    private IBlockState state;
    private int rarirty;

    public FlowerGeneratorHE(FlowerBionisation flower, int rarity) {
        this.flower = flower;
        this.state = flower.getDefaultState();
        this.rarirty = rarity;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < rarirty; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (worldIn.isAirBlock(blockpos) && blockpos.getY() < worldIn.getActualHeight() && this.flower.canBlockStay(worldIn, blockpos, this.state)) {
                worldIn.setBlockState(blockpos, this.state, 2);
            }
        }
        return true;
    }
}
