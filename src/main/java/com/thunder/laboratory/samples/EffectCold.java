package com.thunder.laboratory.samples;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;


public class EffectCold extends AbstractEffect {

    public EffectCold() {
        this(Constants.DURATION_COLD, 0);
    }

    public EffectCold(int duration, int power) {
        super(Constants.ID_COLD, duration, power, false, "Cold", SampleType.EFFECT);
        observablePotions.add(Constants.POTION_WEAKENSS_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 400))
                Utilities.addPotionEffect(player, Constants.POTION_NAUSEA_ID, 1, 60, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_WEAKENSS_ID, power, -1, wasPowerChanged);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
