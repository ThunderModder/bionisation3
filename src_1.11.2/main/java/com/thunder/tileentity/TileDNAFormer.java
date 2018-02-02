package com.thunder.tileentity;

import com.thunder.container.ContainerDNAFormer;
import com.thunder.item.DNAPattern;
import com.thunder.item.ItemRegistry;
import com.thunder.recipe.MachineRecipeRegistry;
import com.thunder.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class TileDNAFormer extends TileBMachine {

    @Override
    public int getProcessTime(@Nullable ItemStack stack) {
        return 100;
    }

    @Override
    public boolean getProcessConditions(NonNullList<ItemStack> items) {
        if(items.get(1).isEmpty() || items.get(2).isEmpty()) return false;
        if(!items.get(2).getItem().equals(ItemRegistry.DNA_PATTERN)) return false;
        int gene = MachineRecipeRegistry.getInstance().getDNAFormerOutput(items.get(1));
        if(gene == 0) return false;
        NBTTagCompound tag = Utilities.getNbt(items.get(2));
        if(tag.hasKey(DNAPattern.DNA_ARRAY_KEY)){
            boolean isFull = true;
            boolean hasGene = false;
            for(int i : tag.getIntArray(DNAPattern.DNA_ARRAY_KEY)){
                if(i == 0)
                    isFull = false;
                if(gene == i) hasGene = true;
            }
            if(isFull) return false;
            if(hasGene) return false;
        }
        return true;
    }

    @Override
    public void processResult(NonNullList<ItemStack> items) {
        int gene = MachineRecipeRegistry.getInstance().getDNAFormerOutput(items.get(1));
        this.decrStackSize(1, 1);
        ItemStack dnaPattern = items.get(2);
        NBTTagCompound tag = Utilities.getNbt(dnaPattern);
        if(tag.hasKey(DNAPattern.DNA_ARRAY_KEY)){
            int [] dnaArr = tag.getIntArray(DNAPattern.DNA_ARRAY_KEY);
            for (int i = 0; i < dnaArr.length; i++) {
                if(dnaArr[i] == 0) {
                    dnaArr[i] = gene;
                    break;
                }
            }
        }else{
            int [] dna_array = new int[8];
            dna_array[0] = gene;
            tag.setIntArray(DNAPattern.DNA_ARRAY_KEY, dna_array);
        }
    }

    @Override
    public int[] getInputIndexes() {
        return new int[]{1};
    }

    @Override
    public int[] getOutputIndexes() {
        return new int[]{0, 2};
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerDNAFormer(playerInventory, this);
    }
}
