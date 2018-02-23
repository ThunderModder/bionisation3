package com.thunder.tileentity;

import com.thunder.bionisation.Config;
import com.thunder.container.ContainerHerbalStation;
import com.thunder.recipe.MachineRecipeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class TileHerbalStation extends TileBMachine {

    @Override
    public int getProcessTime(@Nullable ItemStack stack) {
        return Config.herbalStationProcessTime;
    }

    @Override
    public boolean getProcessConditions(NonNullList<ItemStack> items) {
        if(items.get(1).isEmpty() || items.get(2).isEmpty()) return false;
        if(!items.get(1).getItem().equals(Items.POTIONITEM)) return false;
        ItemStack output = MachineRecipeRegistry.getInstance().getHerbalStationOutput(items.get(2), items.get(3));
        if(output.isEmpty()) return false;
        if(!items.get(4).isEmpty()) return false;
        return true;
    }

    @Override
    public void processResult(NonNullList<ItemStack> items) {
        ItemStack output = MachineRecipeRegistry.getInstance().getHerbalStationOutput(items.get(2), items.get(3));
        this.decrStackSize(1, 1);
        this.decrStackSize(2, 1);
        this.decrStackSize(3, 1);
        this.setInventorySlotContents(4, output);
    }

    @Override
    public int[] getInputIndexes() {
        return new int[]{1, 2, 3};
    }

    @Override
    public int[] getOutputIndexes() {
        return new int[]{0, 4};
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerHerbalStation(playerInventory, this);
    }
}
