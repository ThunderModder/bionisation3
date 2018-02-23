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

public class SymbiontOfBlood extends AbstractEffect {

    public SymbiontOfBlood() {
        this(Constants.DURATION_BLOOD_SYMBIONT, 0);
    }

    public SymbiontOfBlood(int duration, int power) {
        super(Constants.ID_BLOOD_SYMBIONT, duration, power, false, "Symbiont Of Blood", SampleType.SYMBIONT);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 2400)) cap.addBlood(5);
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample bleed = cap.getEffectById(Constants.ID_BLEEDING);
                if (bleed != null) bleed.setExpired(true);
                IBioSample intBleed = cap.getEffectById(Constants.ID_INTERNAL_BLEEDING);
                if (intBleed != null) intBleed.setExpired(true);
                IBioSample bloodV = cap.getEffectById(Constants.ID_BLOOD_VIRUS);
                if (bloodV != null) bloodV.setExpired(true);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
