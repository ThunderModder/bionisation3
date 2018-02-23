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

public class SymbiontOfResistance extends AbstractEffect {

    public SymbiontOfResistance() {
        this(Constants.DURATION_RESISTANCE_SYMBIONT, 0);
    }

    public SymbiontOfResistance(int duration, int power) {
        super(Constants.ID_RESISTANCE_SYMBIONT, duration, power, false, "Symbiont Of Resistance", SampleType.SYMBIONT);
        observablePotions.add(Constants.POTION_RESISTANCE_ID);
        observablePotions.add(Constants.POTION_FIRERESISTANCE_ID);
        observablePotions.add(Constants.POTION_ABSORPTION_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_RESISTANCE_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_FIRERESISTANCE_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_ABSORPTION_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample redV = cap.getEffectById(Constants.ID_RED_VIRUS);
                if (redV != null) redV.setExpired(true);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
