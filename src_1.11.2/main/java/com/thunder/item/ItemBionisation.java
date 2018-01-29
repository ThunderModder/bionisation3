package com.thunder.item;

import com.thunder.bionisation.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;


public class ItemBionisation extends Item {

    public ItemBionisation(String unlocalizedName){
        this(unlocalizedName, 64);
    }

    public ItemBionisation(String unlocalizedName, int stackCount){
        this.setMaxStackSize(stackCount);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        this.setCreativeTab(CommonProxy.tabBionisation);
        GameRegistry.register(this);
    }

    public ItemBionisation(){
        this.setMaxStackSize(64);
        String className = this.getClass().getName();
        String itemName = className.substring(className.lastIndexOf(".") + 1).toLowerCase();
        this.setUnlocalizedName(itemName);
        this.setRegistryName(itemName);
        this.setCreativeTab(CommonProxy.tabBionisation);
        GameRegistry.register(this);
    }
}
