package com.thunder.item;

import com.thunder.bionisation.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BItemBlock extends ItemBlock {

    public BItemBlock(Block block) {
        super(block);
        this.setMaxStackSize(64);
        this.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(this);
    }

    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return CommonProxy.tabBionisation;
    }
}
