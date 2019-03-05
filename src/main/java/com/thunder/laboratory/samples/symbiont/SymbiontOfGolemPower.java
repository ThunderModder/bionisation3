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

public class SymbiontOfGolemPower extends AbstractEffect {

    public SymbiontOfGolemPower() {
        this(Constants.DURATION_GOLEMP_SYMBIONT, 0);
    }

    public SymbiontOfGolemPower(int duration, int power) {
        super(Constants.ID_GOLEMP_SYMBIONT, duration, power, false, "Symbiont Of Golem Power", SampleType.SYMBIONT);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample boneB = cap.getEffectById(Constants.ID_BONEBACTERIA);
                if (boneB != null) boneB.setExpired(true);
                IBioSample skullV = cap.getEffectById(Constants.ID_SKULL_VIRUS);
                if (skullV != null) skullV.setExpired(true);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
