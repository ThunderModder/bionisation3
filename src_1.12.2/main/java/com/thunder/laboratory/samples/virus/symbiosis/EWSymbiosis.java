package com.thunder.laboratory.samples.virus.symbiosis;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.AbstractVirusEffect;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EWSymbiosis extends AbstractVirusEffect {

    public EWSymbiosis() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public EWSymbiosis(int duration, int power) {
        super(Constants.ID_EW_SYMBIOSIS, duration, power, true, "EW Symbiosis", Constants.DNA_EW_SYMBIOSIS, SampleType.SYMBIOSIS);
        observablePotions.add(Constants.POTION_NIGHTVISION_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_NIGHTVISION_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 80)) {
                //check viruses
                IBioSample v1 = cap.getEffectById(Constants.ID_ENDER_VIRUS);
                IBioSample v2 = cap.getEffectById(Constants.ID_WITHER_VIRUS);
                if (v1 != null) v1.setExpired(true);
                if (v2 != null) v2.setExpired(true);
            }
            //effects
            if(Utilities.isTickerEqual(cap.getTicker(), 1200)){
                EntityEnderPearl entityenderpearl = new EntityEnderPearl(player.world, player);
                entityenderpearl.shoot(player, player.rotationPitch + ((float)Math.random() * 360.0f), player.rotationYaw + ((float)Math.random() * 360.0f), 0.0F, 1.5F, 0.0F);
                player.world.spawnEntity(entityenderpearl);
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            if(!(target instanceof EntityCreeper))
                Utilities.addPotionEffect(target, Constants.POTION_WITHER_ID, power, 40);
        }
        if(type == EventType.HURT){
            LivingHurtEvent e = (LivingHurtEvent)event;
            DamageSource source = e.getSource();
            if(source.getTrueSource() instanceof EntityLivingBase){
                EntityLivingBase entity = (EntityLivingBase)source.getTrueSource();
                World world = entity.world;
                if (world.canSeeSky(new BlockPos(entity))) {
                    double c0 = entity.posX + (world.rand.nextDouble() - 0.5D) * (8.0D * 10);
                    double c1 = entity.posY + (double) (world.rand.nextInt((int) (40)) - 2);
                    double c2 = entity.posZ + (world.rand.nextDouble() - 0.5D) * (8.0D * 10);
                    if (entity.attemptTeleport(c0, c1, c2)) {
                        world.playSound(null, entity.prevPosX, entity.prevPosY, entity.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, entity.getSoundCategory(), 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
