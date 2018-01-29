package com.thunder.container;

import com.thunder.item.ItemRegistry;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerVaccineCreator extends ContainerBMachine {

    public ContainerVaccineCreator(InventoryPlayer playerInventory, IInventory machine) {
        super(playerInventory, machine, 3, 152, 61);

        this.addSlotToContainer(new Slot(machine, 1, 69, 17));
        this.addSlotToContainer(new Slot(machine, 2, 91, 17));
        this.addSlotToContainer(new Slot(machine, 3, 80, 61));
        addSpecialSlot(ItemRegistry.VIAL, 1);
        addSpecialSlot(ItemRegistry.VACCINE_INJECTOR, 2);
        addDefaultSlots(playerInventory);
    }
}
