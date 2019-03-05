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


public class MyceliumBacteria extends AbstractEffect {

    public MyceliumBacteria() {
        this(Constants.DURATION_MYCELIUMBACTERIA, 0);
    }

    public MyceliumBacteria(int duration, int power) {
        super(Constants.ID_MYCELIUMBACTERIA, duration, power, true, "Mycelium Bacteria", SampleType.BACTERIA);
        observablePotions.add(Constants.POTION_HUNGER_ID);
        observablePotions.add(Constants.POTION_SLOWNESS_ID);
        observablePotions.add(Constants.POTION_STRENGTH_ID);
        this.needToBeSynced = true;
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_HUNGER_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_STRENGTH_ID, power * 2, -1, wasPowerChanged);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
