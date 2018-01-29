package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.symbiosis.OVSymbiosis;
import com.thunder.laboratory.samples.virus.symbiosis.RSSymbiosis;
import com.thunder.misc.ai.EntityAINearestAttackableTargetWithWhiteList;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class RedVirus extends AbstractVirusEffect {

    public RedVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public RedVirus(int duration, int power) {
        super(Constants.ID_RED_VIRUS, duration, power, true, "Red Virus", Constants.DNA_RED_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_WEAKENSS_ID);
        observablePotions.add(Constants.POTION_JUMPBOOST_ID);
        needToBeSynced = true;
        isHidden = true;
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                //check viruses
                IBioSample v = cap.getEffectById(Constants.ID_SKULL_VIRUS);
                if(v != null) cap.addEffectIntoQueue(new RSSymbiosis(Constants.STANDART_VIRUS_DURATION, Utilities.getPowerFromImmunity(cap.getImmunityLevel())));
            }
            if(duration > 6000) {
                if(isHidden) isHidden = false;
                Utilities.addPotionEffect(player, Constants.POTION_WEAKENSS_ID, power, -1, wasPowerChanged);
                Utilities.addPotionEffect(player, Constants.POTION_JUMPBOOST_ID, power, -1, wasPowerChanged);
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new RedVirus(STANDART_VIRUS_DURATION, 1), target);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(!(entity instanceof EntitySpider) && duration > 1200 && Utilities.isTickerEqual(cap.getTicker(), 400))
                Utilities.addPotionEffect(entity, Constants.POTION_POISON_ID, power, 40, wasPowerChanged);
            else {
                Utilities.spreadEffect(this, entity, EntitySpider.class, 5);
                if(entity instanceof EntitySpider){
                    if(Utilities.isTickerEqual(cap.getTicker(), 600)) {
                        EntitySpider spider = (EntitySpider) entity;
                        spider.targetTasks.taskEntries.clear();
                        //clear because it cannot check properly if task is present, so tasks are stacking and it is bad
                        spider.targetTasks.addTask(2, new EntityAINearestAttackableTargetWithWhiteList(spider, EntityLivingBase.class, true, EntitySpider.class));
                    }
                }
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new RedVirus(STANDART_VIRUS_DURATION, 1), target);
        }
    }
}
