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

public class SymbiontOfSouls extends AbstractEffect {

    public SymbiontOfSouls() {
        this(Constants.DURATION_SOULS_SYMBIONT, 0);
    }

    public SymbiontOfSouls(int duration, int power) {
        super(Constants.ID_SOULS_SYMBIONT, duration, power, false, "Symbiont Of Souls", SampleType.SYMBIONT);
        observablePotions.add(Constants.POTION_INVISIBILITY_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_INVISIBILITY_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample glowB = cap.getEffectById(Constants.ID_GLOWINGBACTERIA);
                if (glowB != null) glowB.setExpired(true);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
