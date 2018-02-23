package com.thunder.laboratory.samples.cure;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CureSGreenBacteria extends AbstractEffect {

    public CureSGreenBacteria() {
        this(Constants.CURE_BACTERIA_STANDART_DURATION, 0);
    }

    public CureSGreenBacteria(int duration, int power) {
        super(Constants.ID_CURE_SMALLGREENBACTERIA, duration, power, false, "Cure: Small Green Bacteria", SampleType.BACTERIA_CURE);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK && isExpired){
            IBioSample smp = cap.getEffectById(Constants.ID_SMALLGREENBACTERIA);
            if(smp != null) smp.setExpired(true);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}