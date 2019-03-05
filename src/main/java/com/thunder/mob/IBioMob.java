package com.thunder.mob;

import com.thunder.laboratory.IBioSample;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;


public interface IBioMob {

    List<IBioSample> getEffectList();
    void setEffectList(List<IBioSample> list);
    void setTicker(int value);
    List<IBioSample> getQueuedList();
    void addEffect(IBioSample sample, EntityLivingBase entity);
    void addEffectStrict(IBioSample sample, EntityLivingBase entity);
    void addEffectIntoQueue(IBioSample sample);
    void removeEffect(IBioSample sample, EntityLivingBase entity);
    void removeEffect(int id, EntityLivingBase entity);
    boolean isEffectActive(int id);
    IBioSample getEffectById(int id);
    void clearEffects(EntityLivingBase entity);
    void clearQueuedEffects();
    boolean isEffectActive(IBioSample sample);
    int getTicker();
    void incrementTicker();
    void copyBioMob(IBioMob bioPlayer);
    void syncAllCap(EntityLivingBase entity);
    NBTTagCompound writeToNBT();
    void readFromNBT(NBTBase nbtBase);
}
