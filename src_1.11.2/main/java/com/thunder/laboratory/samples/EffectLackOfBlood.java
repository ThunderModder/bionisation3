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


public class EffectLackOfBlood extends AbstractEffect {

    public EffectLackOfBlood() {
        this(Constants.DURATION_LACKOFBLOOD, 0);
    }

    public EffectLackOfBlood(int duration, int power) {
        super(Constants.ID_LACKOFBLOOD, duration, power, false, "Lack of Blood", SampleType.EFFECT);
        observablePotions.add(Constants.POTION_NAUSEA_ID);
        observablePotions.add(Constants.POTION_MINIMGF_ID);
        observablePotions.add(Constants.POTION_WEAKENSS_ID);
        observablePotions.add(Constants.POTION_SLOWNESS_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 600))
                Utilities.addPotionEffect(player, Constants.POTION_NAUSEA_ID, 0, 100, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_MINIMGF_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_WEAKENSS_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
            if(cap.getBloodLevel() > Constants.BLOOD_LEVEL_LACKOFBLOOD) isExpired = true;
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
