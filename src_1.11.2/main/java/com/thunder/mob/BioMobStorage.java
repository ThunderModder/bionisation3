package com.thunder.mob;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;


public class BioMobStorage implements Capability.IStorage<IBioMob> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IBioMob> capability, IBioMob instance, EnumFacing side) {
        return instance.writeToNBT();
    }

    @Override
    public void readNBT(Capability<IBioMob> capability, IBioMob instance, EnumFacing side, NBTBase nbt) {
        instance.readFromNBT(nbt);
    }
}
