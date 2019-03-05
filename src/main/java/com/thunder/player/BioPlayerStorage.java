package com.thunder.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;


public class BioPlayerStorage implements Capability.IStorage<IBioPlayer> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IBioPlayer> capability, IBioPlayer instance, EnumFacing side) {
        return instance.writeToNBT();
    }

    @Override
    public void readNBT(Capability<IBioPlayer> capability, IBioPlayer instance, EnumFacing side, NBTBase nbt) {
        instance.readFromNBT(nbt);
    }
}
