package com.thunder.laboratory.samples.bacteria;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.Event;


public class SwampBacteria extends AbstractEffect {

    public SwampBacteria() {
        this(Constants.DURATION_SWAMPBACTERIA, 0);
    }

    public SwampBacteria(int duration, int power) {
        super(Constants.ID_SWAMPBACTERIA, duration, power, true, "Swamp Bacteria", SampleType.BACTERIA);
        observablePotions.add(Constants.POTION_HUNGER_ID);
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_HUNGER_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 1200)) player.attackEntityFrom(DamageSource.GENERIC, 0.5f);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
