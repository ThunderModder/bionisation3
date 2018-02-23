package com.thunder.container;


import com.google.common.collect.Maps;
import com.thunder.tileentity.TileBMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

public abstract class ContainerBMachine extends Container {

    private final IInventory machine;
    private int [] fields;

    private HashMap<Item, Integer> specialSlots = Maps.<Item, Integer>newHashMap();

    private int currentSize = 1;
    private int [] airSlots;

    private EntityPlayer player;

    protected ContainerBMachine(InventoryPlayer playerInventory, IInventory machine, int invSize, int fx, int fy) {
        this.machine = machine;
        this.currentSize += invSize;
        this.player = playerInventory.player;
        this.fields = new int [machine.getFieldCount()];
        this.airSlots = new int []{-1, -1};
        //vanilla slots
        this.addSlotToContainer(new SlotFurnaceFuel(machine, 0, fx, fy));
    }

    public void addDefaultSlots(InventoryPlayer playerInventory){
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public void addAirSlotRange(int startIndex, int endIndex){
        this.airSlots = new int[]{startIndex, endIndex};
    }

    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.machine);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i) {

            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            for(int k = 0; k < this.machine.getFieldCount(); k++){
                if(this.fields[k] != this.machine.getField(k))
                    icontainerlistener.sendProgressBarUpdate(this, k, this.machine.getField(k));
            }
        }
        for(int k = 0; k < this.machine.getFieldCount(); k++){
            this.fields[k] = this.machine.getField(k);
        }
        //check creative
        if(machine instanceof TileBMachine) {
            if (player.capabilities.isCreativeMode)
                ((TileBMachine)machine).setCreative(true);
            else ((TileBMachine)machine).setCreative(false);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.machine.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.machine.isUsableByPlayer(playerIn);
    }

    public void addSpecialSlot(Item item, int index){
        if(!specialSlots.containsKey(item))
            specialSlots.put(item, index);
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < currentSize) {
                if (!this.mergeItemStack(itemstack1, currentSize, 36 + currentSize, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index > currentSize - 1) {
                //special slots processing
                Item item = itemstack1.getItem();
                if(specialSlots.containsKey(item)){
                    int ind = specialSlots.get(item);
                    if (!this.mergeItemStack(itemstack1, ind, ind + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }else if (TileEntityFurnace.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(airSlots[0] >= 0 && airSlots[1] >= 0){
                    if (!this.mergeItemStack(itemstack1, airSlots[0], airSlots[1], false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= currentSize && index < 27 + currentSize) {
                    if (!this.mergeItemStack(itemstack1, 27 + currentSize, 36 + currentSize, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 27 + currentSize && index < 36 + currentSize && !this.mergeItemStack(itemstack1, currentSize, 27 + currentSize, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, currentSize, 36 + currentSize, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }
}
