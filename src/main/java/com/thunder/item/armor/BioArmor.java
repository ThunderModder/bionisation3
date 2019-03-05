package com.thunder.item.armor;

import com.thunder.bionisation.CommonProxy;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class BioArmor extends ItemArmor {

    public BioArmor(String unlocalizedName, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn){
        super(materialIn, renderIndexIn, equipmentSlotIn);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        this.setCreativeTab(CommonProxy.tabBionisation);
        ForgeRegistries.ITEMS.register(this);
    }
}
