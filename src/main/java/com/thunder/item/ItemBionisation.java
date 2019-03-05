package com.thunder.item;

import com.thunder.bionisation.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class ItemBionisation extends Item {

    public ItemBionisation(String unlocalizedName){
        this(unlocalizedName, 64);
    }

    public ItemBionisation(String unlocalizedName, int stackCount){
        this.setMaxStackSize(stackCount);
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        this.setCreativeTab(CommonProxy.tabBionisation);
        ForgeRegistries.ITEMS.register(this);
    }

    public ItemBionisation(){
        this.setMaxStackSize(64);
        String className = this.getClass().getName();
        String itemName = className.substring(className.lastIndexOf(".") + 1).toLowerCase();
        this.setTranslationKey(itemName);
        this.setRegistryName(itemName);
        this.setCreativeTab(CommonProxy.tabBionisation);
        ForgeRegistries.ITEMS.register(this);
    }
}
