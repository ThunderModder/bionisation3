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

public class SymbiontOfHealthyStomach extends AbstractEffect {

    public SymbiontOfHealthyStomach() {
        this(Constants.DURATION_HEALTHYST_SYMBIONT, 0);
    }

    public SymbiontOfHealthyStomach(int duration, int power) {
        super(Constants.ID_HEALTHYST_SYMBIONT, duration, power, false, "Symbiont Of Healthy Stomach", SampleType.SYMBIONT);
        observablePotions.add(Constants.POTION_SATURATION_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_SATURATION_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample swampB = cap.getEffectById(Constants.ID_SWAMPBACTERIA);
                if (swampB != null) swampB.setExpired(true);
                IBioSample foodP = cap.getEffectById(Constants.ID_FOODPOISONING);
                if (foodP != null) foodP.setExpired(true);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
