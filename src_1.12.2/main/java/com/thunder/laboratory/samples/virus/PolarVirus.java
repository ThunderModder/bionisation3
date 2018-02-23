package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class PolarVirus extends AbstractVirusEffect {

    public PolarVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public PolarVirus(int duration, int power) {
        super(Constants.ID_POLAR_VIRUS, duration, power, true, "Polar Virus", Constants.DNA_POLAR_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_SLOWNESS_ID);
        observablePotions.add(Constants.POTION_HUNGER_ID);
        observablePotions.add(Constants.POTION_HEALTHBOOST_ID);
        needToBeSynced = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_HUNGER_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_HEALTHBOOST_ID, power * 3, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 600))
                Utilities.spreadEffect(this, player, EntityLivingBase.class, 5);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(!(entity instanceof EntityPolarBear)) {
                if(duration > 1200) entity.attackEntityFrom(DamageSource.GENERIC, entity.getMaxHealth() + 1000f);
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new PolarVirus(STANDART_VIRUS_DURATION, 2), target);
        }
    }
}
