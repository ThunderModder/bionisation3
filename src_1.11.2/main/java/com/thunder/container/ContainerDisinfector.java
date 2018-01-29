package com.thunder.container;

import com.thunder.item.ItemRegistry;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerDisinfector extends ContainerBMachine {

    public ContainerDisinfector(InventoryPlayer playerInventory, IInventory machine) {
        super(playerInventory, machine, 16, 152, 61);

        int index = 1;
        int startX = 26;
        int startY = 21;
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++) {
                this.addSlotToContainer(new Slot(machine, index, startX, startY));
                index++;
                startX += 18;
            }
            startX = 26;
            startY += 18;
        }
        this.addSlotToContainer(new Slot(machine, index, 152, 20));
        addAirSlotRange(1, index);
        addSpecialSlot(ItemRegistry.DISINFECTANT_FLUID, 16);
        addDefaultSlots(playerInventory);
    }
}
