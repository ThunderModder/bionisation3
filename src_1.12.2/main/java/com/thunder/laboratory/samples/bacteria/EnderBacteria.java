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
import net.minecraftforge.fml.common.eventhandler.Event;


public class EnderBacteria extends AbstractEffect {

    public EnderBacteria() {
        this(Constants.DURATION_ENDERBACTERIA, 0);
    }

    public EnderBacteria(int duration, int power) {
        super(Constants.ID_ENDERBACTERIA, duration, power, true, "Ender Bacteria", SampleType.BACTERIA);
        observablePotions.add(Constants.POTION_NIGHTVISION_ID);
        this.needToBeSynced = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_NIGHTVISION_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 400))
                Utilities.addPotionEffect(player, Constants.POTION_INVISIBILITY_ID, 1, 200);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}

