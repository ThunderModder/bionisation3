package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.symbiosis.EWSymbiosis;
import com.thunder.misc.ai.EntityAINearestAttackableTargetR;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class EnderVirus extends AbstractVirusEffect {

    public EnderVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public EnderVirus(int duration, int power) {
        super(Constants.ID_ENDER_VIRUS, duration, power, true, "Ender Virus", Constants.DNA_ENDER_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_NIGHTVISION_ID);
        needToBeSynced = true;
        isHidden = true;
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                //check viruses
                IBioSample v = cap.getEffectById(Constants.ID_WITHER_VIRUS);
                if(v != null) cap.addEffectIntoQueue(new EWSymbiosis(Constants.STANDART_VIRUS_DURATION, Utilities.getPowerFromImmunity(cap.getImmunityLevel())));
            }
            if(duration > 1200) {
                if(isHidden) isHidden = false;
                Utilities.addPotionEffect(player, Constants.POTION_NIGHTVISION_ID, power, -1, wasPowerChanged);
                if (Utilities.isTickerEqual(cap.getTicker(), 600)) {
                    World world = player.world;
                    if (world.canSeeSky(new BlockPos(player))) {
                        double c0 = player.posX + (world.rand.nextDouble() - 0.5D) * (8.0D * power * 2);
                        double c1 = player.posY + (double) (world.rand.nextInt((4 * (power + 1) * 2) + 1));
                        double c2 = player.posZ + (world.rand.nextDouble() - 0.5D) * (8.0D * power * 2);
                        if (player.attemptTeleport(c0, c1, c2)) {
                            world.playSound(null, player.prevPosX, player.prevPosY, player.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, player.getSoundCategory(), 1.0F, 1.0F);
                        }
                    }
                }
                if (player.isInWater()) {
                    Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, power, 100, wasPowerChanged);
                    Utilities.addPotionEffect(player, Constants.POTION_NAUSEA_ID, power, 100, wasPowerChanged);
                    Utilities.addPotionEffect(player, Constants.POTION_BLINDNESS_ID, power, 100, wasPowerChanged);
                    if (Utilities.isTickerEqual(cap.getTicker(), 100))
                        player.attackEntityFrom(DamageSource.GENERIC, 1f);
                }
            }
        }
        if(type == EventType.HURT){
            LivingHurtEvent e = (LivingHurtEvent)event;
            DamageSource source = e.getSource();
            if(source.getTrueSource() instanceof EntityLivingBase)
                Utilities.addEffectToLiving(new EnderVirus(STANDART_VIRUS_DURATION, 2), (EntityLivingBase)source.getTrueSource());
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new EnderVirus(STANDART_VIRUS_DURATION, 2), target);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(duration > 1200) {
                if(isHidden) isHidden = false;
                if (Utilities.isTickerEqual(cap.getTicker(), 600)) {
                    if(entity instanceof EntityEnderman){
                        EntityEnderman ender = (EntityEnderman)entity;
                        EntityAIBase task1 = null;
                        boolean hasCTask = false;
                        for(Object o : ender.targetTasks.taskEntries) {
                            EntityAITasks.EntityAITaskEntry entry = (EntityAITasks.EntityAITaskEntry) o;
                            if (entry.action instanceof EntityAINearestAttackableTarget)
                                task1 = entry.action;
                            else if (entry.action instanceof EntityAINearestAttackableTargetR)
                                hasCTask = true;
                        }
                        if(task1 != null)
                            ender.targetTasks.removeTask(task1);
                        if(!hasCTask)
                            ender.targetTasks.addTask(2, new EntityAINearestAttackableTargetR<>(ender, EntityPlayer.class, false));
                    }
                    World world = entity.world;
                    if (world.canSeeSky(new BlockPos(entity))) {
                        double c0 = entity.posX + (world.rand.nextDouble() - 0.5D) * (8.0D * power * 2);
                        double c1 = entity.posY + (double) (world.rand.nextInt((int) (4 * (power + 1) * 2)) - 2);
                        double c2 = entity.posZ + (world.rand.nextDouble() - 0.5D) * (8.0D * power * 2);
                        if (entity.attemptTeleport(c0, c1, c2)) {
                            world.playSound(null, entity.prevPosX, entity.prevPosY, entity.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, entity.getSoundCategory(), 1.0F, 1.0F);
                        }
                    }
                }
            }
        }
        if(type == EventType.HURT){
            LivingHurtEvent e = (LivingHurtEvent)event;
            DamageSource source = e.getSource();
            if(source.getTrueSource() instanceof EntityLivingBase)
                Utilities.addEffectToLiving(new EnderVirus(STANDART_VIRUS_DURATION, 2), (EntityLivingBase)source.getTrueSource());
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new EnderVirus(STANDART_VIRUS_DURATION, 2), target);
        }
    }
}
