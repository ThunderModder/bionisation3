package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.misc.ai.EntityAINearestAttackableTargetWithWhiteList;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class BrainVirus extends AbstractVirusEffect {

    public BrainVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public BrainVirus(int duration, int power) {
        super(Constants.ID_BRAIN_VIRUS, duration, power, true, "Brain Virus", Constants.DNA_BRAIN_VIRUS, SampleType.VIRUS);
        isHidden = true;
        observablePotions.add(Constants.POTION_HEALTHBOOST_ID);
        observablePotions.add(Constants.POTION_NAUSEA_ID);
        observablePotions.add(Constants.POTION_STRENGTH_ID);
        observablePotions.add(Constants.POTION_SLOWNESS_ID);
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(duration > 6000) {
                if(isHidden) isHidden = false;
                Utilities.addPotionEffect(player, Constants.POTION_HEALTHBOOST_ID, power, -1, wasPowerChanged);
                Utilities.addPotionEffect(player, Constants.POTION_NAUSEA_ID, power, -1, wasPowerChanged);
                Utilities.addPotionEffect(player, Constants.POTION_STRENGTH_ID, power, -1, wasPowerChanged);
                Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
                if (Utilities.isTickerEqual(cap.getTicker(), 100)) {
                    //spam
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(TextFormatting.DARK_RED + player.getDisplayNameString() + ": " + TextFormatting.YELLOW + Utilities.genRandomString(Utilities.random.nextInt(20))));
                }
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new BrainVirus(STANDART_VIRUS_DURATION, 1), target);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK) {
            if (!(entity instanceof EntityZombie))
                entity.setFire(10);
            else {
                Utilities.addPotionEffect(entity, Constants.POTION_HEALTHBOOST_ID, power, -1, wasPowerChanged);
                Utilities.addPotionEffect(entity, Constants.POTION_STRENGTH_ID, power, -1, wasPowerChanged);
                //Utilities.addPotionEffect(entity, Constants.POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
                if(Utilities.isTickerEqual(cap.getTicker(), 600)) {
                    Utilities.spreadEffect(this, entity, EntityZombie.class, 5);
                    EntityZombie zombie = (EntityZombie) entity;
                    zombie.targetTasks.taskEntries.clear();
                    //clear because it cannot check properly if task is present, so tasks are stacking and it is bad
                    zombie.targetTasks.addTask(2, new EntityAINearestAttackableTargetWithWhiteList(zombie, EntityLivingBase.class, false, EntityZombie.class));
                }
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new BrainVirus(STANDART_VIRUS_DURATION, 1), target);
        }
    }
}
