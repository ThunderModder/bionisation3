package com.thunder.laboratory.samples;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EffectImmunity extends AbstractEffect {

    public EffectImmunity() {
        this(Constants.CURE_EFFECT_STANDART_DURATION, 0);
    }

    public EffectImmunity(int duration, int power) {
        super(Constants.ID_EFFECT_IMMUNITY, duration, power, false, "Immunity", SampleType.EFFECT);
    }


    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK && isExpired) cap.addImmunity(2 + power);
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
