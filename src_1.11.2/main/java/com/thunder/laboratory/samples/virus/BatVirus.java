package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.symbiosis.OVSymbiosis;
import com.thunder.misc.ai.EntityAINearestAttackableTargetWithWhiteList;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class BatVirus extends AbstractVirusEffect {

    public BatVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public BatVirus(int duration, int power) {
        super(Constants.ID_VAMPIRE_VIRUS, duration, power, true, "Bat Virus", Constants.DNA_VAMPIRE_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_NIGHTVISION_ID);
        observablePotions.add(Constants.POTION_NAUSEA_ID);
        observablePotions.add(Constants.POTION_SLOWNESS_ID);
        observablePotions.add(Constants.POTION_WEAKENSS_ID);
        observablePotions.add(Constants.POTION_HASTE_ID);
        observablePotions.add(Constants.POTION_SPEED_ID);
        observablePotions.add(Constants.POTION_REGENERATION_ID);
        needToBeSynced = true;
        isHidden = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                //check viruses
                IBioSample v = cap.getEffectById(Constants.ID_OCEAN_VIRUS);
                if(v != null) cap.addEffectIntoQueue(new OVSymbiosis(Constants.STANDART_VIRUS_DURATION, Utilities.getPowerFromImmunity(cap.getImmunityLevel())));
            }
            if(duration > 3600) {
                if (isHidden) isHidden = false;
                //effects
                World world = player.world;
                float f = player.getBrightness(1.0F);
                BlockPos blockpos = player.getRidingEntity() instanceof EntityBoat ? (new BlockPos(player.posX, (double) Math.round(player.posY), player.posZ)).up() : new BlockPos(player.posX, (double) Math.round(player.posY), player.posZ);
                if (f > 0.5F && world.canSeeSky(blockpos) && !player.isWet() && !world.isRaining()) {
                    Utilities.removeIfActive(player, Constants.POTION_HASTE_ID);
                    Utilities.removeIfActive(player, Constants.POTION_SPEED_ID);
                    Utilities.removeIfActive(player, Constants.POTION_REGENERATION_ID);
                    Utilities.removeIfActive(player, Constants.POTION_NIGHTVISION_ID);
                    Utilities.addPotionEffect(player, Constants.POTION_NAUSEA_ID, power, -1, wasPowerChanged);
                    Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
                    Utilities.addPotionEffect(player, Constants.POTION_WEAKENSS_ID, power, -1, wasPowerChanged);
                    if(!player.isBurning()) player.setFire(10);
                }else{
                    Utilities.removeIfActive(player, Constants.POTION_WEAKENSS_ID);
                    Utilities.removeIfActive(player, Constants.POTION_SLOWNESS_ID);
                    Utilities.removeIfActive(player, Constants.POTION_NAUSEA_ID);
                    Utilities.addPotionEffect(player, Constants.POTION_HASTE_ID, power, -1, wasPowerChanged);
                    Utilities.addPotionEffect(player, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
                    Utilities.addPotionEffect(player, Constants.POTION_REGENERATION_ID, power, -1, wasPowerChanged);
                    Utilities.addPotionEffect(player, Constants.POTION_NIGHTVISION_ID, power, -1, wasPowerChanged);
                    if(player.isBurning()) player.extinguish();
                }
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new BatVirus(STANDART_VIRUS_DURATION, 1), target);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(!(entity instanceof EntityBat)){
                if(duration > 600){
                    World world = entity.world;
                    for (int i = 0; i < 4; i++){
                        EntityBat bat = new EntityBat(world);
                        bat.setPosition(entity.posX, entity.posY, entity.posZ);
                        Utilities.addEffectToLiving(new BatVirus(STANDART_VIRUS_DURATION, 1), bat);
                        world.spawnEntity(bat);
                    }
                    isExpired = true;
                    entity.attackEntityFrom(DamageSource.GENERIC, entity.getMaxHealth() + 1000f);
                    world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 0.1f, false);
                }
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new BatVirus(STANDART_VIRUS_DURATION, 1), target);
        }
        if(type == EventType.HURT && entity instanceof EntityBat){
            LivingHurtEvent e = (LivingHurtEvent)event;
            DamageSource source = e.getSource();
            if(source.getSourceOfDamage() instanceof EntityLivingBase)
                Utilities.addEffectToLiving(new BatVirus(STANDART_VIRUS_DURATION, 1), (EntityLivingBase)source.getSourceOfDamage());
        }
    }
}
