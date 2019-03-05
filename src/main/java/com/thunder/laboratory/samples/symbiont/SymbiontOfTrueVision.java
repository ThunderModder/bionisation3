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

public class SymbiontOfTrueVision extends AbstractEffect {

    public SymbiontOfTrueVision() {
        this(Constants.DURATION_VISION_SYMBIONT, 0);
    }

    public SymbiontOfTrueVision(int duration, int power) {
        super(Constants.ID_VISION_SYMBIONT, duration, power, false, "Symbiont Of True Vision", SampleType.SYMBIONT);
        observablePotions.add(Constants.POTION_NIGHTVISION_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_NIGHTVISION_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample enderV = cap.getEffectById(Constants.ID_ENDER_VIRUS);
                if (enderV != null) enderV.setExpired(true);
                IBioSample blackB = cap.getEffectById(Constants.ID_BLACKBACTERIA);
                if (blackB != null) blackB.setExpired(true);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
