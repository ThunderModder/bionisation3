package com.thunder.block.crop;

import com.thunder.block.GrowableBionisation;
import com.thunder.item.ItemRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.EnumPlantType;

import java.util.List;


public class Yarrow extends GrowableBionisation {

    public Yarrow(String className, AxisAlignedBB axis, EnumPlantType type) {
        super(className, axis, type, false);
    }

    @Override
    protected Item getSeed() {
        return ItemRegistry.YARROW_SEED;
    }

    @Override
    public List<ItemStack> getDrops(net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
        ret.add(new ItemStack(ItemRegistry.YARROW_BUNCH, 1));
        return ret;
    }
}
