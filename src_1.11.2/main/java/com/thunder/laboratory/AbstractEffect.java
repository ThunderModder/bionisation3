package com.thunder.laboratory;

import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractEffect implements IBioSample, Serializable {

    protected int id;
    protected int duration;
    protected String name;
    protected boolean isExpired;
    protected SampleType type;
    protected int power;

    protected boolean canUpdatePower;

    protected boolean isInfinite;

    protected boolean isDangerous;
    protected boolean needToBeSynced;

    protected boolean wasPowerChanged;

    protected List<Integer> observablePotions;

    protected AbstractEffect(int id, int duration, int power, boolean isDangerous, String name, SampleType type){
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.isExpired = false;
        this.isDangerous = isDangerous;
        this.needToBeSynced = false;
        this.type = type;
        this.power = power;
        this.observablePotions = new ArrayList<>();
        isInfinite = this.duration == -1;
        this.wasPowerChanged = false;
        this.canUpdatePower = false;
        if(isInfinite) this.duration = 1;
    }

    public void endEffect(){
        if(wasPowerChanged)
            wasPowerChanged = false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getDangerous() {
        return isDangerous;
    }

    @Override
    public boolean isNeedToBeSynced() {
        return needToBeSynced;
    }

    @Override
    public void setNeedToBeSynced(boolean needToBeSynced) {
        this.needToBeSynced = needToBeSynced;
    }

    @Override
    public List<Integer> getObservablePotionEffects() {
        return observablePotions;
    }

    @Override
    public void setDangerous(boolean bool) {
        this.isDangerous = bool;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean isExpired() {
        return isExpired;
    }

    @Override
    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    @Override
    public boolean isInfinite() {
        return isInfinite;
    }

    @Override
    public void setInfinite(boolean infinite) {
        isInfinite = infinite;
    }

    @Override
    public SampleType getType() {
        return type;
    }

    @Override
    public void setType(SampleType type) {
        this.type = type;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void setPower(int power) {
        this.wasPowerChanged = true;
        this.power = power;
    }

    @Override
    public boolean getCanUpdatePower(){
        return canUpdatePower;
    }

    @Override
    public void setCanUpdatePower(boolean bool){
        canUpdatePower = bool;
    }

    @Override
    public void performEffectPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK) {
            if (!isInfinite && duration <= 0) isExpired = true;
            else if(!isInfinite) duration--;
            if(isInfinite){
                this.duration++;
                if(duration > Constants.MAX_TICKER_VALUE) this.duration = 1;
            }
        }
        if(canUpdatePower) {
            int pow = Utilities.getPowerFromImmunity(cap.getImmunityLevel());
            if(power != pow)
                setPower(pow);
        }
        performPlayer(event, player, type, cap);
        endEffect();
    }

    @Override
    public void performEffectEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK) {
            if (!isInfinite && duration <= 0) isExpired = true;
            else if(!isInfinite) duration--;
            if(isInfinite){
                this.duration++;
                if(duration > Constants.MAX_TICKER_VALUE) this.duration = 1;
            }
        }
        performEntity(event, entity, type, cap);
        endEffect();
    }

    public abstract void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap);
    public abstract void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap);
}
