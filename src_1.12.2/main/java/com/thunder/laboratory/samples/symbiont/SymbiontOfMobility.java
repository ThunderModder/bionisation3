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

public class SymbiontOfMobility extends AbstractEffect {

    public SymbiontOfMobility() {
        this(Constants.DURATION_WAFFINITY_SYMBIONT, 0);
    }

    public SymbiontOfMobility(int duration, int power) {
        super(Constants.ID_MOBILITY_SYMBIONT, duration, power, false, "Symbiont Of Mobility", SampleType.SYMBIONT);
        observablePotions.add(Constants.POTION_SPEED_ID);
        observablePotions.add(Constants.POTION_HASTE_ID);
        observablePotions.add(Constants.POTION_JUMPBOOST_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_HASTE_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_JUMPBOOST_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample fractE = cap.getEffectById(Constants.ID_FRACTURE);
                if (fractE != null) fractE.setExpired(true);
                IBioSample teraB = cap.getEffectById(Constants.ID_TERABACTERIA);
                if (teraB != null) teraB.setExpired(true);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
