package com.thunder.bionisation;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


public class BCreativeTab extends CreativeTabs {

    public BCreativeTab() {
        super("Bionisation");
        setBackgroundImageName("item_search.png");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.SKULL);
    }

    @Override
    public boolean hasSearchBar(){
        return true;
    }
}
