package com.thunder.container;

import com.thunder.item.ItemRegistry;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerVirusReplicator extends ContainerBMachine {

    public ContainerVirusReplicator(InventoryPlayer playerInventory, IInventory machine) {
        super(playerInventory, machine, 2, 152, 61);

        this.addSlotToContainer(new Slot(machine, 1, 80, 17));
        this.addSlotToContainer(new Slot(machine, 2, 80, 61));

        this.addAirSlotRange(1, 2);
        this.addSpecialSlot(ItemRegistry.VIRUS_SPRAYER, 2);
        this.addDefaultSlots(playerInventory);
    }
}
