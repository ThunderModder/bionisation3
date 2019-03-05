package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class DesertVirus extends AbstractVirusEffect {

    public DesertVirus() {
        this(Constants.DURATION_DESERT_VIRUS, 0);
    }

    public DesertVirus(int duration, int power) {
        super(Constants.ID_DESERT_VIRUS, duration, power, true, "Desert Virus", Constants.DNA_DESERT_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_REGENERATION_ID);
        observablePotions.add(Constants.POTION_SPEED_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_REGENERATION_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 200))
                Utilities.addPotionEffect(player, Constants.POTION_ABSORPTION_ID, power, 100, wasPowerChanged);
            if(isExpired) player.attackEntityFrom(DamageSource.GENERIC, player.getMaxHealth() + 1000f);
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new DesertVirus(Constants.DURATION_DESERT_VIRUS, 1), target);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(entity instanceof EntityHusk)
                if(Utilities.isTickerEqual(cap.getTicker(), 100))
                    Utilities.spreadEffect(this, entity, EntityHusk.class, 8);
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new DesertVirus(Constants.DURATION_DESERT_VIRUS, 1), target);
        }
    }
}
