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


public class EffectFatigue extends AbstractEffect {

    public EffectFatigue() {
        this(Constants.DURATION_FATIGUE, 0);
    }

    public EffectFatigue(int duration, int power) {
        super(Constants.ID_FATIGUE, duration, power, false, "Fatigue", SampleType.EFFECT);
        observablePotions.add(Constants.POTION_WEAKENSS_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_WEAKENSS_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 20))
                player.dropItem(true);
            if(cap.getImmunityLevel() > Constants.IMMUNITY_LEVEL_FATIGUE) isExpired = true;
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
