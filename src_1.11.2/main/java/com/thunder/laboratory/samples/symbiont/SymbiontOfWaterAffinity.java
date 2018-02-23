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

public class SymbiontOfWaterAffinity extends AbstractEffect {

    public SymbiontOfWaterAffinity() {
        this(Constants.DURATION_WAFFINITY_SYMBIONT, 0);
    }

    public SymbiontOfWaterAffinity(int duration, int power) {
        super(Constants.ID_WAFFINITY_SYMBIONT, duration, power, false, "Symbiont Of Water Affinity", SampleType.SYMBIONT);
        observablePotions.add(Constants.POTION_WATERBREATHING_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_WATERBREATHING_ID, power, -1, wasPowerChanged);
            if(player.isInWater()){
                player.motionX *= 1.2000000000000003D;
                player.motionZ *= 1.2000000000000003D;
            }
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample waterB = cap.getEffectById(Constants.ID_WATERBACTERIA);
                if (waterB != null) waterB.setExpired(true);
                IBioSample oceanV = cap.getEffectById(Constants.ID_OCEAN_VIRUS);
                if (oceanV != null) oceanV.setExpired(true);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
