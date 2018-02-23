package com.thunder.laboratory.samples.symbiont;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SymbiontOfHealth extends AbstractEffect {

    public SymbiontOfHealth() {
        this(Constants.DURATION_HEALTH_SYMBIONT, 0);
    }

    public SymbiontOfHealth(int duration, int power) {
        super(Constants.ID_HEALTH_SYMBIONT, duration, power, false, "Symbiont Of Health", SampleType.SYMBIONT);
        observablePotions.add(Constants.POTION_HEALTHBOOST_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_HEALTHBOOST_ID, power + 2, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample brainV = cap.getEffectById(Constants.ID_BRAIN_VIRUS);
                if (brainV != null) brainV.setExpired(true);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
