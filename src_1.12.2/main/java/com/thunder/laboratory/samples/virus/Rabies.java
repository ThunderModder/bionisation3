package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.misc.ai.EntityAIAttackPeaceful;
import com.thunder.misc.ai.EntityAINearestAttackableTargetR;
import com.thunder.misc.ai.EntityAINearestAttackableTargetWithWhiteList;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class Rabies extends AbstractVirusEffect {

    public Rabies() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public Rabies(int duration, int power) {
        super(Constants.ID_RABIES_VIRUS, duration, power, true, "Rabies", Constants.DNA_RABIES_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_STRENGTH_ID);
        observablePotions.add(Constants.POTION_SPEED_ID);
        observablePotions.add(Constants.POTION_JUMPBOOST_ID);
        isHidden = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(duration > 6000){
                if(isHidden) isHidden = false;
                Utilities.addPotionEffect(player, Constants.POTION_STRENGTH_ID, power, -1, wasPowerChanged);
                Utilities.addPotionEffect(player, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
                Utilities.addPotionEffect(player, Constants.POTION_JUMPBOOST_ID, power, -1, wasPowerChanged);
            }
            if(duration > 12000) {
                if(!player.isBurning()) player.setFire(10);
                if(player.isWet())
                    player.attackEntityFrom(DamageSource.GENERIC, player.getMaxHealth() + 1000f);
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new Rabies(STANDART_VIRUS_DURATION, 1), target);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(duration > 1200){
                if(isHidden) isHidden = false;
                Utilities.addPotionEffect(entity, Constants.POTION_STRENGTH_ID, power, -1, wasPowerChanged);
                Utilities.addPotionEffect(entity, Constants.POTION_SPEED_ID, power * 3, -1, wasPowerChanged);
                Utilities.addPotionEffect(entity, Constants.POTION_JUMPBOOST_ID, power, -1, wasPowerChanged);
                //remove red and brain virus if active
                if(Utilities.isTickerEqual(cap.getTicker(), 20)) {
                    //check viruses
                    IBioSample v1 = cap.getEffectById(Constants.ID_RED_VIRUS);
                    IBioSample v2 = cap.getEffectById(Constants.ID_BRAIN_VIRUS);
                    if (v1 != null) v1.setExpired(true);
                    if (v2 != null) v2.setExpired(true);
                }
                //if wolf - set angry
                if(entity instanceof EntityWolf){
                    EntityWolf wolf = (EntityWolf)entity;
                    wolf.setAngry(true);
                }
                if(Utilities.isTickerEqual(cap.getTicker(), 100)){
                    //add target tasks
                    if(entity instanceof EntityCreature){
                        EntityCreature creature = (EntityCreature)entity;
                        //creature.targetTasks.taskEntries.clear();
                        EntityAIBase task1 = null;
                        boolean hasLeapTask = false;
                        boolean hasAttackablePeacefulTask = false;
                        for(Object o : creature.tasks.taskEntries){
                            EntityAITasks.EntityAITaskEntry entry = (EntityAITasks.EntityAITaskEntry)o;
                            if(entry.action instanceof EntityAIPanic)
                                task1 = entry.action;
                            else if(entry.action instanceof EntityAILeapAtTarget)
                                hasLeapTask = true;
                            else if(entry.action instanceof EntityAIAttackPeaceful)
                                hasAttackablePeacefulTask = true;
                        }
                        if(task1 != null) creature.tasks.removeTask(task1);
                        //clear because it cannot check properly if task is present, so tasks are stacking and it is bad
                        //==============
                        EntityAIBase task2 = null;
                        EntityAIBase task3 = null;
                        boolean hasAttackableTask = false;
                        for(Object o : creature.targetTasks.taskEntries){
                            EntityAITasks.EntityAITaskEntry entry = (EntityAITasks.EntityAITaskEntry)o;
                            if(entry.action instanceof EntityAINearestAttackableTarget)
                                task2 = entry.action;
                            else if(entry.action instanceof EntityAINearestAttackableTargetWithWhiteList)
                                task3 = entry.action;
                            else if(entry.action instanceof EntityAINearestAttackableTargetR)
                                hasAttackableTask = true;
                        }
                        if(task2 != null) creature.targetTasks.removeTask(task2);
                        if(task3 != null) creature.targetTasks.removeTask(task3);
                        if(!hasAttackableTask)
                            creature.targetTasks.addTask(2, new EntityAINearestAttackableTargetR<>(creature, EntityLivingBase.class, false));
                        if(!hasLeapTask)
                            creature.tasks.addTask(1, new EntityAILeapAtTarget(creature, 0.6f));
                        if(!(entity instanceof EntityMob) && !hasAttackablePeacefulTask)
                            creature.tasks.addTask(1, new EntityAIAttackPeaceful(creature, 0.9f, true));
                        //==============
                    }

                }
            }
            if(duration > 4800) {
                if(!entity.isBurning()) entity.setFire(10);
                if(entity.isWet())
                    entity.attackEntityFrom(DamageSource.GENERIC, entity.getMaxHealth() + 1000f);
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new Rabies(STANDART_VIRUS_DURATION, 1), target);
        }
    }
}
