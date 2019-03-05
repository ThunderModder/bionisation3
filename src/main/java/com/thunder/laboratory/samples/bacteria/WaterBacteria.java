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
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.Event;


public class WaterBacteria extends AbstractEffect {

    public WaterBacteria() {
        this(Constants.DURATION_WATERBACTERIA, 0);
    }

    public WaterBacteria(int duration, int power) {
        super(Constants.ID_WATERBACTERIA, duration, power, true, "Water Bacteria", SampleType.BACTERIA);
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(player.isInWater()){
                Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, power, 100, wasPowerChanged);
                if(player.getAir() > 1) player.setAir(1);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
