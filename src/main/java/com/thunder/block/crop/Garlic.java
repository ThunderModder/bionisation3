package com.thunder.block.crop;

import com.thunder.block.GrowableBionisation;
import com.thunder.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.EnumPlantType;


public class Garlic extends GrowableBionisation {

    public Garlic(String className, AxisAlignedBB axis, EnumPlantType type) {
        super(className, axis, type, false);
    }

    @Override
    protected Item getSeed() {
        return ItemRegistry.GARLIC_BULB;
    }
}
