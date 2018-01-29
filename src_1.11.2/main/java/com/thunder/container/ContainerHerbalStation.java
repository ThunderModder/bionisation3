package com.thunder.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerHerbalStation extends ContainerBMachine {

    public ContainerHerbalStation(InventoryPlayer playerInventory, IInventory machine) {
        super(playerInventory, machine, 4, 152, 61);

        this.addSlotToContainer(new Slot(machine, 1, 58, 17));
        this.addSlotToContainer(new Slot(machine, 2, 80, 17));
        this.addSlotToContainer(new Slot(machine, 3, 102, 17));
        this.addSlotToContainer(new Slot(machine, 4, 80, 61));

        addAirSlotRange(2, 4);
        addSpecialSlot(Items.POTIONITEM, 1);
        addDefaultSlots(playerInventory);
    }
}
