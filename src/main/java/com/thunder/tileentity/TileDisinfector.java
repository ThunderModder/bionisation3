package com.thunder.tileentity;

import com.thunder.bionisation.Config;
import com.thunder.container.ContainerDisinfector;
import com.thunder.item.ItemRegistry;
import com.thunder.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class TileDisinfector extends TileBMachine {

    private static final String KEY = Utilities.getModIdString("eff_list");

    @Override
    public int getProcessTime(@Nullable ItemStack stack) {
        return Config.disinfectorProcessTime;
    }

    @Override
    public boolean getProcessConditions(NonNullList<ItemStack> items) {
        if(items.get(16).isEmpty()) return false;
        if(!items.get(16).getItem().equals(ItemRegistry.DISINFECTANT_FLUID)) return false;
        boolean isItems = false;
        for (int i = 1; i <= 15; i++) {
            if(!items.get(i).isEmpty()) {
                isItems = true;
                break;
            }
        }
        return isItems;
    }

    @Override
    public void processResult(NonNullList<ItemStack> items) {
        this.decrStackSize(16, 1);
        for (int i = 1; i <= 15; i++) {
            if(!items.get(i).isEmpty()){
                NBTTagCompound tag = Utilities.getNbt(items.get(i));
                if(tag.hasKey(KEY))
                    tag.removeTag(KEY);
                if (tag.isEmpty()) {
                    items.get(i).setTagCompound(null);
                }
            }
        }
    }

    @Override
    public int[] getInputIndexes() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    }

    @Override
    public int[] getOutputIndexes() {
        return new int[]{0};
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerDisinfector(playerInventory, this);
    }
}
