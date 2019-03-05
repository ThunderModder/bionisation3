package com.thunder.laboratory.samples;

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


public class EffectSunstroke extends AbstractEffect {

    public EffectSunstroke() {
        this(Constants.DURATION_SUNSTROKE, 0);
    }

    public EffectSunstroke(int duration, int power) {
        super(Constants.ID_SUNSTROKE, duration, power, false, "Sunstroke", SampleType.EFFECT);
        observablePotions.add(Constants.POTION_NAUSEA_ID);
        observablePotions.add(Constants.POTION_MINIMGF_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_NAUSEA_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_MINIMGF_ID, power, -1, wasPowerChanged);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
