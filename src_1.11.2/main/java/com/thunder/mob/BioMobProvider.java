package com.thunder.mob;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;


public class BioMobProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IBioMob.class)
    public static final Capability<IBioMob> BIO_MOB_CAPABILITY= null;
    private IBioMob instance = BIO_MOB_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == BIO_MOB_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == BIO_MOB_CAPABILITY ? BIO_MOB_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return BIO_MOB_CAPABILITY.getStorage().writeNBT(BIO_MOB_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        BIO_MOB_CAPABILITY.getStorage().readNBT(BIO_MOB_CAPABILITY, this.instance, null, nbt);
    }
}
