package com.thunder.worldgen;

import com.thunder.block.BlockRegistry;
import com.thunder.block.FlowerBionisation;
import com.thunder.block.GrowableBionisation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;


public class FlowerGenerator extends WorldGenerator {

    private FlowerBionisation flower;
    private IBlockState state;
    private int rarirty;

    public FlowerGenerator(FlowerBionisation flower, int rarity) {
        this.flower = flower;
        this.state = flower.getDefaultState();
        this.rarirty = rarity;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < this.rarirty; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.hasNoSky() || blockpos.getY() < 255) && this.flower.canBlockStay(worldIn, blockpos, this.state)) {
                Block block = this.state.getBlock();
                if(block.equals(BlockRegistry.GARLIC) || block.equals(BlockRegistry.DILL) || block.equals(BlockRegistry.YARROW))
                    worldIn.setBlockState(blockpos, this.state.withProperty(GrowableBionisation.AGE, 7));
                else worldIn.setBlockState(blockpos, this.state);
            }
        }
        return true;
    }
}
