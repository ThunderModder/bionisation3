package com.thunder.tileentity;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class TileBMachine extends TileEntityLockable implements ITickable, ISidedInventory {

    private static final int[] SLOTS_SIDES = new int[] {0};

    private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(30, ItemStack.EMPTY);

    private int machineProcessTime;
    private int currentProcessTime;
    private int processTime;
    private int totalProcessTime;
    private boolean isCreative;

    public boolean isCreative() {
        return isCreative;
    }

    public void setCreative(boolean creative) {
        isCreative = creative;
    }

    public abstract int getProcessTime(@Nullable ItemStack stack);

    public abstract boolean getProcessConditions(NonNullList<ItemStack> items);

    public abstract void processResult(NonNullList<ItemStack> items);

    public abstract int[] getInputIndexes();

    public abstract int[] getOutputIndexes();

    public ItemStack getFuel(){
        return this.inventory.get(0);
    }

    public int getSizeInventory() {
        return this.inventory.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.inventory) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ItemStack getStackInSlot(int index) {
        return (ItemStack)this.inventory.get(index);
    }

    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = (ItemStack)this.inventory.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        if (checkInputSlot(index) && !flag) {
            this.totalProcessTime = this.getProcessTime(stack);
            this.processTime = 0;
            this.markDirty();
        }
    }

    public String getName() {
        return "";
    }

    public boolean hasCustomName() {
        return false;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        this.machineProcessTime = compound.getInteger("machine_time");
        this.processTime = compound.getInteger("process_time");
        this.totalProcessTime = compound.getInteger("total_process_time");
        this.currentProcessTime = compound.getInteger("current_process_time");
        this.isCreative = compound.getBoolean("is_creative");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("machine_time", this.machineProcessTime);
        compound.setInteger("process_time", this.processTime);
        compound.setInteger("total_process_time", this.totalProcessTime);
        compound.setInteger("current_process_time", this.currentProcessTime);
        compound.setBoolean("is_creative", this.isCreative);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        return compound;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isProcessing() {
        return this.machineProcessTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isProcessing(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    public void update() {
        boolean flag1 = false;
        if (this.isProcessing()) {
            --this.machineProcessTime;
        }
        if (!this.world.isRemote) {
            //fuel
            ItemStack itemstack = getFuel();
            if (this.isProcessing() || !itemstack.isEmpty()) {
                //fuel processing
                if (!this.isProcessing() && this.getProcessConditions(inventory)) {
                    this.machineProcessTime = TileEntityFurnace.getItemBurnTime(itemstack);
                    this.currentProcessTime = this.machineProcessTime;

                    if (this.isProcessing()) {
                        flag1 = true;

                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);

                            if (itemstack.isEmpty()) {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                this.inventory.set(1, item1);
                            }
                        }
                    }
                }
                //item processing
                if(isCreative && this.isProcessing() && this.getProcessConditions(inventory)){
                    this.processTime = 0;
                    this.totalProcessTime = this.getProcessTime(null);
                    this.processResult(inventory);
                    flag1 = true;
                }else if (this.isProcessing() && this.getProcessConditions(inventory)) {
                    ++this.processTime;
                    if (this.processTime == this.totalProcessTime) {
                        this.processTime = 0;
                        this.totalProcessTime = this.getProcessTime(null);
                        this.processResult(inventory);
                        flag1 = true;
                    }
                }
                else {
                    this.processTime = 0;
                }
                isCreative = false;
            }
            else if (!this.isProcessing() && this.processTime > 0) {
                this.processTime = MathHelper.clamp(this.processTime - 2, 0, this.totalProcessTime);
            }
        }
        if (flag1) {
            this.markDirty();
        }
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void openInventory(EntityPlayer player) {}

    public void closeInventory(EntityPlayer player) {}

    public boolean checkOutputSlot(int index){
        int [] slots = getOutputIndexes();
        for (int i : slots) {
            if (i == index) return true;
        }
        return false;
    }

    public boolean checkInputSlot(int index){
        int [] slots = getInputIndexes();
        for (int i : slots) {
            if (i == index) return true;
        }
        return false;
    }

    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if(index > 0 && !checkOutputSlot(index))
            return true;
        else {
            ItemStack itemstack = getFuel();
            return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? getOutputIndexes() : (side == EnumFacing.UP ? getInputIndexes() : SLOTS_SIDES);
    }

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 0) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }
        return true;
    }

    public String getGuiID() {
        return "";
    }

    public int getField(int id) {
        switch (id) {
            case 0:
                return this.machineProcessTime;
            case 1:
                return this.currentProcessTime;
            case 2:
                return this.processTime;
            case 3:
                return this.totalProcessTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.machineProcessTime = value;
                break;
            case 1:
                this.currentProcessTime = value;
                break;
            case 2:
                this.processTime = value;
                break;
            case 3:
                this.totalProcessTime = value;
        }
    }

    public int getFieldCount() {
        return 4;
    }

    public void clear() {
        this.inventory.clear();
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }
}