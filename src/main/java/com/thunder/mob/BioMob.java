package com.thunder.mob;

import com.thunder.bionisation.Information;
import com.thunder.laboratory.IBioSample;
import com.thunder.network.NetworkHandler;
import com.thunder.network.SyncAllCapMessage;
import com.thunder.network.SyncBloodCapMessage;
import com.thunder.network.SyncEntityCapMessage;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;


public class BioMob implements IBioMob {

    private List<IBioSample> effects = new ArrayList<IBioSample>();
    private List<IBioSample> queuedEffects = new ArrayList<IBioSample>();

    private int ticker = 0;

    @Override
    public List<IBioSample> getEffectList() {
        return effects;
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
    public List<IBioSample> getQueuedList() {
        return queuedEffects;
    }

    @Override
    public void addEffect(IBioSample sample, EntityLivingBase entity) {
        if(!isEffectActive(sample)) {
            effects.add(sample);
            syncAllCap(entity);
        }
    }

    @Override
    public void addEffectStrict(IBioSample sample, EntityLivingBase entity) {
        IBioSample smp = getEffectById(sample.getId());
        if(smp != null) {
            removeEffect(smp.getId(), entity);
            addEffect(sample, entity);
        }
        else addEffect(sample, entity);
    }

    @Override
    public void addEffectIntoQueue(IBioSample sample) {
        queuedEffects.add(sample);
    }

    @Override
    public void removeEffect(IBioSample sample, EntityLivingBase entity) {
        if(isEffectActive(sample)){
            Utilities.clearObservablePotions(sample.getObservablePotionEffects(), entity);
            effects.remove(sample);
            syncAllCap(entity);
        }
    }

    @Override
    public void removeEffect(int id, EntityLivingBase entity) {
        if(isEffectActive(id)){
            IBioSample smp = getEffectById(id);
            Utilities.clearObservablePotions(smp.getObservablePotionEffects(), entity);
            effects.remove(smp);
            syncAllCap(entity);
        }
    }

    @Override
    public void clearEffects(EntityLivingBase entity) {
        if(!effects.isEmpty()) {
            for(IBioSample smp : effects)
                Utilities.clearObservablePotions(smp.getObservablePotionEffects(), entity);
            effects.clear();
            syncAllCap(entity);
        }
    }

    @Override
    public void clearQueuedEffects() {
        queuedEffects.clear();
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
    public void copyBioMob(IBioMob bioMob) {
        ticker = bioMob.getTicker();
        effects = bioMob.getEffectList();
        queuedEffects = bioMob.getQueuedList();
    }

    @Override
    public void syncAllCap(EntityLivingBase entity) {
        NetworkHandler.network.sendToAllAround(new SyncEntityCapMessage(entity.getEntityId(), writeToNBT()), new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 50d));
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        nbtTag.setInteger("mticker", ticker);
        try {
            nbtTag.setTag(Utilities.getModIdString("meffects"), Utilities.objListToNBT(effects));
            nbtTag.setTag(Utilities.getModIdString("qmeffects"), Utilities.objListToNBT(queuedEffects));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nbtTag;
    }

    @Override
    public void readFromNBT(NBTBase nbtBase) {
        NBTTagCompound nbtTag = (NBTTagCompound)nbtBase;
        ticker = nbtTag.getInteger("mticker");
        try {
            effects = Utilities.listFromNBT((NBTTagList)nbtTag.getTag(Utilities.getModIdString("meffects")));
            queuedEffects = Utilities.listFromNBT((NBTTagList)nbtTag.getTag(Utilities.getModIdString("qmeffects")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
