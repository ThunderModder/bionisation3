package com.thunder.item;

import com.thunder.bionisation.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class SeedBionisation extends ItemSeeds {

    public SeedBionisation(String ulocalizedName, Block block) {
        super(block, Blocks.FARMLAND);
        this.setTranslationKey(ulocalizedName);
        this.setRegistryName(ulocalizedName);
        this.setCreativeTab(CommonProxy.tabBionisation);
        this.setMaxStackSize(64);
        ForgeRegistries.ITEMS.register(this);
    }
}