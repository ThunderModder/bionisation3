package com.thunder.tileentity;

import com.thunder.bionisation.Config;
import com.thunder.container.ContainerVaccineCreator;
import com.thunder.item.ItemRegistry;
import com.thunder.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class TileVaccineCreator extends TileBMachine {

    @Override
    public int getProcessTime(@Nullable ItemStack stack) {
        return Config.vaccineCreatorProcessTime;
    }

    @Override
    public boolean getProcessConditions(NonNullList<ItemStack> items) {
        ItemStack stackVial = items.get(1);
        ItemStack stackInj = items.get(2);
        if(stackVial.isEmpty() || stackInj.isEmpty()) return false;
        if(!stackVial.getItem().equals(ItemRegistry.VIAL)) return false;
        if(!stackInj.getItem().equals(ItemRegistry.VACCINE_INJECTOR)) return false;
        NBTTagCompound nbt = Utilities.getNbt(stackVial);
        if(!nbt.hasKey(Utilities.getModIdString("sdna"))) return false;
        if(!items.get(3).isEmpty()) return false;
        return true;
    }

    @Override
    public void processResult(NonNullList<ItemStack> items) {
        ItemStack stackVial = items.get(1);
        ItemStack stackInj = items.get(2);
        NBTTagCompound tagVial = Utilities.getNbt(stackVial);
        String [] dnas = tagVial.getString(Utilities.getModIdString("sdna")).split("_");
        int index = Utilities.random.nextInt(dnas.length);
        String dna = dnas[index];
        dnas[index] = "";
        ItemStack newInj = stackInj.copy();
        NBTTagCompound newTag = Utilities.getNbt(newInj);
        newTag.setString(Utilities.getModIdString("vstability"), Utilities.random.nextInt(2) == 0 ? "Stable" : "Unstable");
        newTag.setString(Utilities.getModIdString("vdna"), dna);
        this.setInventorySlotContents(3, newInj);
        this.decrStackSize(2, 1);
        String newDna = "";
        for(String s : dnas){
            if(!s.isEmpty()) newDna += s + "_";
        }
        if(newDna.isEmpty()) tagVial.removeTag(Utilities.getModIdString("sdna"));
        else
            tagVial.setString(Utilities.getModIdString("sdna"), newDna);
    }

    @Override
    public int[] getInputIndexes() {
        return new int[]{1, 2};
    }

    @Override
    public int[] getOutputIndexes() {
        return new int[]{0, 3};
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerVaccineCreator(playerInventory, this);
    }
}
