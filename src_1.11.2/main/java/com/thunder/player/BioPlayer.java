package com.thunder.player;

import com.thunder.bionisation.Information;
import com.thunder.laboratory.IBioSample;
import com.thunder.network.NetworkHandler;
import com.thunder.network.SyncAllCapMessage;
import com.thunder.network.SyncBloodCapMessage;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;


public class BioPlayer implements IBioPlayer {

    private int immunity = Constants.MAX_IMMUNITY_LEVEL;
    private int blood = Constants.MAX_BLOOD_LEVEL;

    private List<IBioSample> effects = new ArrayList<IBioSample>();
    private List<IBioSample> queuedEffects = new ArrayList<IBioSample>();

    private int ticker = 0;

    @Override
    public void setImmunityLevel(int amount) {
        immunity = min(amount, Constants.MAX_IMMUNITY_LEVEL);
        if(immunity < 0) immunity = 0;
    }

    @Override
    public int getImmunityLevel() {
        return immunity;
    }

    @Override
    public void setBloodLevel(int amount) {
        blood = min(amount, Constants.MAX_BLOOD_LEVEL);
        if(blood < 0) blood = 0;
    }

    @Override
    public int getBloodLevel() {
        return blood;
    }

    @Override
    public void addImmunity(int amount) {
        immunity += amount;
        if(immunity < 0) immunity = 0;
        else if(immunity > Constants.MAX_IMMUNITY_LEVEL) immunity = Constants.MAX_IMMUNITY_LEVEL;
    }

    @Override
    public void removeImmunity(int amount) {
        immunity -= amount;
        if(immunity < 0) immunity = 0;
        else if(immunity > Constants.MAX_IMMUNITY_LEVEL) immunity = Constants.MAX_IMMUNITY_LEVEL;
    }

    @Override
    public void addBlood(int amount) {
        blood += amount;
        if(blood < 0) blood = 0;
        else if(blood > Constants.MAX_BLOOD_LEVEL) blood = Constants.MAX_BLOOD_LEVEL;
    }

    @Override
    public void removeBlood(int amount) {
        blood -= amount;
        if(blood < 0) blood = 0;
        else if(blood > Constants.MAX_BLOOD_LEVEL) blood = Constants.MAX_BLOOD_LEVEL;
    }

    @Override
    public List<IBioSample> getEffectList() {
        return effects;
    }

    @Override
    public List<IBioSample> getQueuedList() {
        return this.queuedEffects;
    }

    @Override
    public void setEffectList(List<IBioSample> list) {
        effects = list;
    }

    @Override
    public void setTicker(int value) {
        ticker = min(value, Constants.MAX_TICKER_VALUE);
        if(ticker < 0) ticker = 0;
    }

    @Override
    public void addEffect(IBioSample sample, EntityPlayer player) {
        if(!isEffectActive(sample)) {
            effects.add(sample);
            syncAllCap(player);
        }
    }

    @Override
    public void addEffectIntoQueue(IBioSample sample) {
        this.queuedEffects.add(sample);
    }

    @Override
    public void addEffectStrict(IBioSample sample, EntityPlayer player) {
        IBioSample smp = getEffectById(sample.getId());
        if(smp != null) {
            removeEffect(smp.getId(), player);
            addEffect(sample, player);
        }
        else addEffect(sample, player);
    }

    @Override
    public void removeEffect(IBioSample sample, EntityPlayer player) {
        if(isEffectActive(sample)){
            Utilities.clearObservablePotions(sample.getObservablePotionEffects(), player);
            effects.remove(sample);
            syncAllCap(player);
        }
    }

    @Override
    public void removeEffect(int id, EntityPlayer player) {
        if(isEffectActive(id)){
            IBioSample smp = getEffectById(id);
            Utilities.clearObservablePotions(smp.getObservablePotionEffects(), player);
            effects.remove(smp);
            syncAllCap(player);
        }
    }

    @Override
    public void clearEffects(EntityPlayer player) {
        if(!effects.isEmpty()) {
            for(IBioSample smp : effects)
                Utilities.clearObservablePotions(smp.getObservablePotionEffects(), player);
            effects.clear();
            syncAllCap(player);
        }
    }

    @Override
    public void clearQueuedEffects() {
        this.queuedEffects.clear();
    }

    @Override
    public boolean isEffectActive(IBioSample sample) {
        for (IBioSample b : effects)
            if(b.getId() == sample.getId()) return true;
        return false;
    }

    @Override
    public boolean isEffectActive(int id) {
        for (IBioSample b : effects)
            if(b.getId() == id) return true;
        return false;
    }

    @Override
    public IBioSample getEffectById(int id) {
        for (IBioSample b : effects)
            if(b.getId() == id) return b;
        return null;
    }

    @Override
    public int getTicker() {
        return ticker;
    }

    @Override
    public void incrementTicker() {
        if(ticker >= Constants.MAX_TICKER_VALUE) ticker = 0;
        else
            ticker++;
    }

    @Override
    public void syncBloodCap(EntityPlayer player) {
        NetworkHandler.network.sendTo(new SyncBloodCapMessage(blood), (EntityPlayerMP) player);
    }

    @Override
    public void syncAllCap(EntityPlayer player) {
        NetworkHandler.network.sendToAllAround(new SyncAllCapMessage(writeToNBT(), player.getEntityId()), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 50d));
    }

    @Override
    public void copyBioPlayer(IBioPlayer bioPlayer) {
        immunity = bioPlayer.getImmunityLevel();
        blood = bioPlayer.getBloodLevel();
        ticker = bioPlayer.getTicker();
        effects = bioPlayer.getEffectList();
        queuedEffects = bioPlayer.getQueuedList();
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        nbtTag.setInteger("immunity", immunity);
        nbtTag.setInteger("blood", blood);
        nbtTag.setInteger("ticker", ticker);
        try {
            nbtTag.setTag(Utilities.getModIdString("effects"), Utilities.objListToNBT(effects));
            nbtTag.setTag(Utilities.getModIdString("qeffects"), Utilities.objListToNBT(queuedEffects));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nbtTag;
    }

    @Override
    public void readFromNBT(NBTBase nbtBase) {
        NBTTagCompound nbtTag = (NBTTagCompound)nbtBase;
        immunity = nbtTag.getInteger("immunity");
        blood = nbtTag.getInteger("blood");
        ticker = nbtTag.getInteger("ticker");
        try {
            effects = Utilities.listFromNBT((NBTTagList)nbtTag.getTag(Utilities.getModIdString("effects")));
            queuedEffects = Utilities.listFromNBT((NBTTagList)nbtTag.getTag(Utilities.getModIdString("qeffects")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
