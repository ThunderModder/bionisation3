package com.thunder.player;

import com.thunder.laboratory.IBioSample;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;


public interface IBioPlayer {

    void setImmunityLevel(int amount);
    int getImmunityLevel();
    void setBloodLevel(int amount);
    int getBloodLevel();
    void addImmunity(int amount);
    void removeImmunity(int amount);
    void addBlood(int amount);
    void removeBlood(int amount);
    List<IBioSample> getEffectList();
    List<IBioSample> getQueuedList();
    void setEffectList(List<IBioSample> list);
    void setTicker(int value);
    void addEffect(IBioSample sample, EntityPlayer player);
    void addEffectIntoQueue(IBioSample sample);
    void addEffectStrict(IBioSample sample, EntityPlayer player);
    void removeEffect(IBioSample sample, EntityPlayer player);
    void removeEffect(int id, EntityPlayer player);
    boolean isEffectActive(int id);
    IBioSample getEffectById(int id);
    void clearEffects(EntityPlayer player);
    void clearQueuedEffects();
    boolean isEffectActive(IBioSample sample);
    int getTicker();
    void incrementTicker();
    void syncBloodCap(EntityPlayer player);
    void syncAllCap(EntityPlayer player);
    void copyBioPlayer(IBioPlayer bioPlayer);
    NBTTagCompound writeToNBT();
    void readFromNBT(NBTBase nbtBase);
}
