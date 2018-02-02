package com.thunder.tileentity;

import com.thunder.container.ContainerVirusReplicator;
import com.thunder.item.DNAPattern;
import com.thunder.item.ItemRegistry;
import com.thunder.item.VirusSprayer;
import com.thunder.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class TileVirusReplicator extends TileBMachine {

    @Override
    public int getProcessTime(@Nullable ItemStack stack) {
        return 100;
    }

    @Override
    public boolean getProcessConditions(NonNullList<ItemStack> items) {
        if(items.get(1).isEmpty() || items.get(2).isEmpty()) return false;
        if(!items.get(1).getItem().equals(ItemRegistry.DNA_PATTERN)) return false;
        if(!items.get(2).getItem().equals(ItemRegistry.VIRUS_SPRAYER)) return false;
        NBTTagCompound tag1 = Utilities.getNbt(items.get(1));
        NBTTagCompound tag2 = Utilities.getNbt(items.get(2));
        if(tag2.hasKey(VirusSprayer.DNA_KEY)) return false;
        if(!tag1.hasKey(DNAPattern.DNA_ARRAY_KEY))
            return false;
        else{
            boolean empty = true;
            for(int i : tag1.getIntArray(DNAPattern.DNA_ARRAY_KEY)){
                if(i != 0)
                    empty = false;
            }
            if(empty) return false;
        }
        return true;
    }

    @Override
    public void processResult(NonNullList<ItemStack> items) {
        NBTTagCompound tagPt = Utilities.getNbt(items.get(1));
        NBTTagCompound tagSp = Utilities.getNbt(items.get(2));
        int [] dnaArray = tagPt.getIntArray(DNAPattern.DNA_ARRAY_KEY);
        tagPt.setIntArray(DNAPattern.DNA_ARRAY_KEY, new int[8]);
        String dna = "";
        for (int i = 0; i < dnaArray.length; i++) {
            if (i == (dnaArray.length - 1)) dna += dnaArray[i];
            else
                dna += dnaArray[i] + ":";
        }
        tagSp.setString(VirusSprayer.DNA_KEY, dna);
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
        return new ContainerVirusReplicator(playerInventory, this);
    }
}
