package com.thunder.block;

import com.thunder.bionisation.CommonProxy;
import com.thunder.item.BItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.Random;


public class FlowerBionisation extends BlockBush {

    private AxisAlignedBB flower_aabb;
    private EnumPlantType flower_type;
    private Block [] ground;

    public FlowerBionisation(String className, AxisAlignedBB axis, EnumPlantType type, boolean tex, @Nullable Block... ground) {
        super(Material.PLANTS);
        this.setUnlocalizedName(className);
        this.setRegistryName(className);
        this.setSoundType(SoundType.PLANT);
        this.ground = ground;
        this.flower_aabb = axis;
        this.flower_type = type;
        GameRegistry.register(this);
        if(tex)
            new BItemBlock(this);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return flower_aabb;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return flower_type;
    }

    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean canSustainBush(IBlockState state) {
        if(ground == null) super.canSustainBush(state);
        else
            for (Block b : ground)
                if(state.getBlock() == b) return true;
        return false;
    }

}
