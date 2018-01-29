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


public class EffectFoodPoisoning extends AbstractEffect {

    public EffectFoodPoisoning() {
        this(Constants.DURATION_FOODPOISONING, 0);
    }

    public EffectFoodPoisoning(int duration, int power) {
        super(Constants.ID_FOODPOISONING, duration, power, false, "Food Poisoning", SampleType.EFFECT);
        observablePotions.add(Constants.POTION_NAUSEA_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 1200)) {
                Utilities.addPotionEffect(player, Constants.POTION_POISON_ID, power, 100, wasPowerChanged);
                Utilities.addPotionEffect(player, Constants.POTION_NAUSEA_ID, power, 400, wasPowerChanged);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
