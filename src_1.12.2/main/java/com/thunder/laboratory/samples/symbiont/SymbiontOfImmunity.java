package com.thunder.laboratory.samples.symbiont;

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

public class SymbiontOfImmunity extends AbstractEffect {

    public SymbiontOfImmunity() {
        this(Constants.DURATION_IMMUNITY_SYMBIONT, 0);
    }

    public SymbiontOfImmunity(int duration, int power) {
        super(Constants.ID_IMMUNITY_SYMBIONT, duration, power, false, "Symbiont Of Immunity", SampleType.SYMBIONT);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
           if(Utilities.isTickerEqual(cap.getTicker(), 1200)) cap.addImmunity(2);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}