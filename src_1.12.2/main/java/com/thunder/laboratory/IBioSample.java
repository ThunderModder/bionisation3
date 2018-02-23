package com.thunder.laboratory;

import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;


public interface IBioSample {

    int getId();
    void setId(int id);
    int getDuration();
    void setDuration(int duration);
    String getName();
    boolean getDangerous();
    void setDangerous(boolean bool);
    boolean isNeedToBeSynced();
    void setNeedToBeSynced(boolean needToBeSynced);
    boolean isExpired();
    void setExpired(boolean expired);
    boolean isInfinite();
    void setInfinite(boolean infinite);
    SampleType getType();
    void setType(SampleType type);
    int getPower();
    void setPower(int power);
    boolean getCanUpdatePower();
    void setCanUpdatePower(boolean bool);
    List<Integer> getObservablePotionEffects();
    void performEffectPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap);
    void performEffectEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap);
}
