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
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;


public class BGreenBacteria extends AbstractEffect {

    public BGreenBacteria() {
        this(Constants.DURATION_BIGGREENBACTERIA, 0);
    }

    public BGreenBacteria(int duration, int power) {
        super(Constants.ID_BIGGREENBACTERIA, duration, power, true, "Big Green Bacteria", SampleType.BACTERIA);
        observablePotions.add(Constants.POTION_SPEED_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {

    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(entity, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
        }
        if(type == EventType.DEATH){
            World world = entity.getEntityWorld();
            for (int i = 0; i < 4; i++)
                world.createExplosion(entity, entity.posX + (Utilities.random.nextInt(2) == 0 ? Utilities.random.nextInt(4)  : - Utilities.random.nextInt(4)), entity.posY, entity.posZ + (Utilities.random.nextInt(2) == 0 ? Utilities.random.nextInt(4)  : - Utilities.random.nextInt(4)), 2.0f, false);
        }
    }
}
