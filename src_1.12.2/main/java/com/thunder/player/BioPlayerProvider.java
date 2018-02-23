package com.thunder.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;


public class BioPlayerProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IBioPlayer.class)
    public static final Capability<IBioPlayer> BIO_PLAYER_CAPABILITY = null;
    private IBioPlayer instance = BIO_PLAYER_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == BIO_PLAYER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == BIO_PLAYER_CAPABILITY ? BIO_PLAYER_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return BIO_PLAYER_CAPABILITY.getStorage().writeNBT(BIO_PLAYER_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        BIO_PLAYER_CAPABILITY.getStorage().readNBT(BIO_PLAYER_CAPABILITY, this.instance, null, nbt);
    }
}
