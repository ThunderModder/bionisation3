package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CreeperVirus extends AbstractVirusEffect {

    public CreeperVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public CreeperVirus(int duration, int power) {
        super(Constants.ID_CREEPER_VIRUS, duration, power, true, "Creeper Virus", Constants.DNA_CREEPER_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_SPEED_ID);
        observablePotions.add(Constants.POTION_RESISTANCE_ID);
        needToBeSynced = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_RESISTANCE_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 1200)) {
                cap.removeImmunity(1);
                player.world.createExplosion(player, player.posX, player.posY, player.posZ, 0.2f, false);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(entity instanceof EntityCreeper)
                Utilities.addPotionEffect(entity, Constants.POTION_SPEED_ID, power * 5, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 50)) {
                Utilities.spreadEffect(this, entity, EntityPlayer.class, 5);
                Utilities.spreadEffect(this, entity, EntityCreeper.class, 5);
            }
        }
    }
}
