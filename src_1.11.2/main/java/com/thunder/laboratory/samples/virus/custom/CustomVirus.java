package com.thunder.laboratory.samples.virus.custom;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.misc.ai.EntityAIAttackPeaceful;
import com.thunder.misc.ai.EntityAINearestAttackableTargetR;
import com.thunder.misc.ai.EntityAINearestAttackableTargetWithWhiteList;
import com.thunder.mob.BioMobProvider;
import com.thunder.mob.IBioMob;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

import static com.thunder.util.Constants.*;
import static com.thunder.util.Utilities.*;

public class CustomVirus extends AbstractCustomVirusEffect {

    public CustomVirus(int id, int duration, int power, boolean isDangerous, String name, String dna) {
        super(id, duration, power, isDangerous, name, dna, SampleType.CUSTOM_VIRUS);
        canUpdatePower = true;
        canMutate = false;
        addOrReaddObservable();
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        String [] dna = this.getDNA().split(":");
        boolean isTickEvent = false;
        if(type == EventType.TICK)  isTickEvent = true;
        boolean isMutate = false;
        for(String s : dna){
            switch (s){
                case "1":
                    if(isTickEvent) addPotionEffect(player, POTION_SPEED_ID, power, -1, wasPowerChanged);
                    break;
                case "2":
                    if(isTickEvent) addPotionEffect(player, POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
                    break;
                case "3":
                    if(isTickEvent) addPotionEffect(player, POTION_HASTE_ID, power, -1, wasPowerChanged);
                    break;
                case "4":
                    if(isTickEvent) addPotionEffect(player, POTION_MINIMGF_ID, power, -1, wasPowerChanged);
                    break;
                case "5":
                    if(isTickEvent) addPotionEffect(player, POTION_STRENGTH_ID, power, -1, wasPowerChanged);
                    break;
                case "6":
                    if(isTickEvent) addPotionEffect(player, POTION_HEALTHBOOST_ID, power, -1, wasPowerChanged);
                    break;
                case "7":
                    if(isTickEvent) addPotionEffect(player, POTION_JUMPBOOST_ID, power, -1, wasPowerChanged);
                    break;
                case "8":
                    if(isTickEvent) addPotionEffect(player, POTION_NAUSEA_ID, power, -1, wasPowerChanged);
                    break;
                case "9":
                    if(isTickEvent) addPotionEffect(player, POTION_REGENERATION_ID, power, -1, wasPowerChanged);
                    break;
                case "10":
                    if(isTickEvent) addPotionEffect(player, POTION_RESISTANCE_ID, power, -1, wasPowerChanged);
                    break;
                case "11":
                    if(isTickEvent) addPotionEffect(player, POTION_FIRERESISTANCE_ID, power, -1, wasPowerChanged);
                    break;
                case "12":
                    if(isTickEvent) addPotionEffect(player, POTION_WATERBREATHING_ID, power, -1, wasPowerChanged);
                    break;
                case "13":
                    if(isTickEvent) addPotionEffect(player, POTION_INVISIBILITY_ID, power, -1, wasPowerChanged);
                    break;
                case "14":
                    if(isTickEvent) addPotionEffect(player, POTION_BLINDNESS_ID, power, -1, wasPowerChanged);
                    break;
                case "15":
                    if(isTickEvent) addPotionEffect(player, POTION_NIGHTVISION_ID, power, -1, wasPowerChanged);
                    break;
                case "16":
                    if(isTickEvent) addPotionEffect(player, POTION_HUNGER_ID, power, -1, wasPowerChanged);
                    break;
                case "17":
                    if(isTickEvent) addPotionEffect(player, POTION_WEAKENSS_ID, power, -1, wasPowerChanged);
                    break;
                case "18":
                    if(isTickEvent) addPotionEffect(player, POTION_POISON_ID, power, -1, wasPowerChanged);
                    break;
                case "19":
                    if(isTickEvent) addPotionEffect(player, POTION_WITHER_ID, power, -1, wasPowerChanged);
                    break;
                case "20":
                    if(isTickEvent) addPotionEffect(player, POTION_ABSORPTION_ID, power, -1, wasPowerChanged);
                    break;
                case "21":
                    if(isTickEvent) addPotionEffect(player, POTION_SATURATION_ID, power, -1, wasPowerChanged);
                    break;
                case "22":
                    if(isTickEvent) addPotionEffect(player, POTION_GLOWING_ID, power, -1, wasPowerChanged);
                    break;
                case "23":
                    if(isTickEvent) addPotionEffect(player, POTION_LEVITATION_ID, power, -1, wasPowerChanged);
                    break;
                case "24":
                    if(isTickEvent) addPotionEffect(player, POTION_LUCK_ID, power, -1, wasPowerChanged);
                    break;
                case "25":
                    if(isTickEvent) addPotionEffect(player, POTION_UNLUCK_ID, power, -1, wasPowerChanged);
                    break;
                case "26":
                    //none
                    break;
                case "27":
                    //none
                    break;
                case "28":
                    //none
                    break;
                case "29":
                    //Spread on attack
                    if(type == EventType.ATTACK){
                        LivingAttackEvent e = (LivingAttackEvent)event;
                        EntityLivingBase target = e.getEntityLiving();
                        CustomVirus virus = new CustomVirus(this.id, this.duration, this.power, isDangerous, name, getDNA());
                        Utilities.addEffectToLiving(virus, target);
                    }
                    break;
                case "30":
                    //Spread on hurt
                    if(type == EventType.HURT){
                        LivingHurtEvent e = (LivingHurtEvent)event;
                        DamageSource source = e.getSource();
                        if(source.getSourceOfDamage() instanceof EntityLivingBase) {
                            CustomVirus virus = new CustomVirus(this.id, this.duration, this.power, isDangerous, name, getDNA());
                            Utilities.addEffectToLiving(virus, (EntityLivingBase) source.getSourceOfDamage());
                        }
                    }
                    break;
                case "31":
                    //Spread on death
                    if(type == EventType.DEATH) {
                        CustomVirus virus = new CustomVirus(this.id, this.duration, this.power, isDangerous, name, getDNA());
                        spreadCustomEffect(virus, player, EntityLivingBase.class, 10);
                    }
                    break;
                case "32":
                    //Spread through air
                    if(isTickEvent && isTickerEqual(cap.getTicker(), 100)) {
                        CustomVirus virus = new CustomVirus(this.id, this.duration, this.power, isDangerous, name, getDNA());
                        spreadCustomEffect(virus, player, EntityLivingBase.class, 10);
                    }
                    break;
                case "33":
                    //Burning
                    if(isTickEvent && !player.isBurning())
                        player.setFire(10);
                    break;
                case "34":
                    //Explosion
                    if(isTickEvent && isTickerEqual(cap.getTicker(), 600))
                        player.world.createExplosion(player, player.posX + (random.nextInt(2) == 0 ? random.nextInt(4)  : - random.nextInt(4)), player.posY, player.posZ + (random.nextInt(2) == 0 ? random.nextInt(4)  : - random.nextInt(4)), 1.0f, false);
                    break;
                case "35":
                    //Random teleportation
                    if(isTickEvent && isTickerEqual(cap.getTicker(), 600)){
                        World world = player.world;
                        if (world.canSeeSky(new BlockPos(player))) {
                            double c0 = player.posX + (world.rand.nextDouble() - 0.5D) * (8.0D * power * 2);
                            double c1 = player.posY + (double) (world.rand.nextInt((int) (4 * (power + 1) * 2)) - 2);
                            double c2 = player.posZ + (world.rand.nextDouble() - 0.5D) * (8.0D * power * 2);
                            if (player.attemptTeleport(c0, c1, c2)) {
                                world.playSound(null, player.prevPosX, player.prevPosY, player.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, player.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }
                    }
                    break;
                case "36":
                    if(!canMutate) {
                        canMutate = true;
                        isMutate = true;
                    }
                    break;
                default:
                    break;
            }
        }
        if(canMutate && !isMutate) canMutate = false;
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        String [] dna = this.getDNA().split(":");
        boolean isTickEvent = false;
        if(type == EventType.TICK)  isTickEvent = true;
        boolean isMutate = false;
        for(String s : dna){
            switch (s){
                case "1":
                    if(isTickEvent) addPotionEffect(entity, POTION_SPEED_ID, power, -1, wasPowerChanged);
                    break;
                case "2":
                    if(isTickEvent) addPotionEffect(entity, POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
                    break;
                case "3":
                    if(isTickEvent) addPotionEffect(entity, POTION_HASTE_ID, power, -1, wasPowerChanged);
                    break;
                case "4":
                    if(isTickEvent) addPotionEffect(entity, POTION_MINIMGF_ID, power, -1, wasPowerChanged);
                    break;
                case "5":
                    if(isTickEvent) addPotionEffect(entity, POTION_STRENGTH_ID, power, -1, wasPowerChanged);
                    break;
                case "6":
                    if(isTickEvent) addPotionEffect(entity, POTION_HEALTHBOOST_ID, power, -1, wasPowerChanged);
                    break;
                case "7":
                    if(isTickEvent) addPotionEffect(entity, POTION_JUMPBOOST_ID, power, -1, wasPowerChanged);
                    break;
                case "8":
                    if(isTickEvent) addPotionEffect(entity, POTION_NAUSEA_ID, power, -1, wasPowerChanged);
                    break;
                case "9":
                    if(isTickEvent) addPotionEffect(entity, POTION_REGENERATION_ID, power, -1, wasPowerChanged);
                    break;
                case "10":
                    if(isTickEvent) addPotionEffect(entity, POTION_RESISTANCE_ID, power, -1, wasPowerChanged);
                    break;
                case "11":
                    if(isTickEvent) addPotionEffect(entity, POTION_FIRERESISTANCE_ID, power, -1, wasPowerChanged);
                    break;
                case "12":
                    if(isTickEvent) addPotionEffect(entity, POTION_WATERBREATHING_ID, power, -1, wasPowerChanged);
                    break;
                case "13":
                    if(isTickEvent) addPotionEffect(entity, POTION_INVISIBILITY_ID, power, -1, wasPowerChanged);
                    break;
                case "14":
                    if(isTickEvent) addPotionEffect(entity, POTION_BLINDNESS_ID, power, -1, wasPowerChanged);
                    break;
                case "15":
                    if(isTickEvent) addPotionEffect(entity, POTION_NIGHTVISION_ID, power, -1, wasPowerChanged);
                    break;
                case "16":
                    if(isTickEvent) addPotionEffect(entity, POTION_HUNGER_ID, power, -1, wasPowerChanged);
                    break;
                case "17":
                    if(isTickEvent) addPotionEffect(entity, POTION_WEAKENSS_ID, power, -1, wasPowerChanged);
                    break;
                case "18":
                    if(isTickEvent) addPotionEffect(entity, POTION_POISON_ID, power, -1, wasPowerChanged);
                    break;
                case "19":
                    if(isTickEvent && !(entity instanceof EntityCreeper)) addPotionEffect(entity, POTION_WITHER_ID, power, -1, wasPowerChanged);
                    break;
                case "20":
                    if(isTickEvent) addPotionEffect(entity, POTION_ABSORPTION_ID, power, -1, wasPowerChanged);
                    break;
                case "21":
                    if(isTickEvent) addPotionEffect(entity, POTION_SATURATION_ID, power, -1, wasPowerChanged);
                    break;
                case "22":
                    if(isTickEvent) addPotionEffect(entity, POTION_GLOWING_ID, power, -1, wasPowerChanged);
                    break;
                case "23":
                    if(isTickEvent) addPotionEffect(entity, POTION_LEVITATION_ID, power, -1, wasPowerChanged);
                    break;
                case "24":
                    if(isTickEvent) addPotionEffect(entity, POTION_LUCK_ID, power, -1, wasPowerChanged);
                    break;
                case "25":
                    if(isTickEvent) addPotionEffect(entity, POTION_UNLUCK_ID, power, -1, wasPowerChanged);
                    break;
                case "26":
                    //Make angry to enemies
                    if(isTickEvent && isTickerEqual(cap.getTicker(), 100)){
                        if(entity instanceof EntityCreature){
                            EntityCreature creature = (EntityCreature)entity;
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
                                    hasAttackableTask = true;
                                else if(entry.action instanceof EntityAINearestAttackableTargetR)
                                    task3 = entry.action;
                            }
                            if(task2 != null) creature.targetTasks.removeTask(task2);
                            if(task3 != null) creature.targetTasks.removeTask(task3);
                            if(!hasAttackableTask)
                                creature.targetTasks.addTask(2, new EntityAINearestAttackableTargetWithWhiteList<>(creature, EntityLivingBase.class, false, entity.getClass()));
                            if(!hasLeapTask)
                                creature.tasks.addTask(1, new EntityAILeapAtTarget(creature, 0.6f));
                            if(!(entity instanceof EntityMob) && !hasAttackablePeacefulTask)
                                creature.tasks.addTask(1, new EntityAIAttackPeaceful(creature, 0.9f, true));
                            //==============
                        }
                    }
                    break;
                case "27":
                    //Make angry to all
                    if(isTickEvent && isTickerEqual(cap.getTicker(), 100)){
                        if(entity instanceof EntityCreature){
                            EntityCreature creature = (EntityCreature)entity;
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
                    break;
                case "28":
                    //make peaceful
                    if(isTickEvent){
                        if(entity instanceof EntityCreature) {
                            EntityCreature creature = (EntityCreature) entity;
                            if(creature.targetTasks.taskEntries.size() > 0) creature.targetTasks.taskEntries.clear();
                        }
                    }
                    break;
                case "29":
                    //Spread on attack
                    if(type == EventType.ATTACK){
                        LivingAttackEvent e = (LivingAttackEvent)event;
                        EntityLivingBase target = e.getEntityLiving();
                        CustomVirus virus = new CustomVirus(this.id, this.duration, this.power, isDangerous, name, getDNA());
                        Utilities.addEffectToLiving(virus, target);
                    }
                    break;
                case "30":
                    //Spread on hurt
                    if(type == EventType.HURT){
                        LivingHurtEvent e = (LivingHurtEvent)event;
                        DamageSource source = e.getSource();
                        if(source.getSourceOfDamage() instanceof EntityLivingBase) {
                            CustomVirus virus = new CustomVirus(this.id, this.duration, this.power, isDangerous, name, getDNA());
                            Utilities.addEffectToLiving(virus, (EntityLivingBase) source.getSourceOfDamage());
                        }
                    }
                    break;
                case "31":
                    //Spread on death
                    if(type == EventType.DEATH) {
                        CustomVirus virus = new CustomVirus(this.id, this.duration, this.power, isDangerous, name, getDNA());
                        spreadCustomEffect(virus, entity, EntityLivingBase.class, 10);
                    }
                    break;
                case "32":
                    //Spread through air
                    if(isTickEvent && isTickerEqual(cap.getTicker(), 100)) {
                        CustomVirus virus = new CustomVirus(this.id, this.duration, this.power, isDangerous, name, getDNA());
                        spreadCustomEffect(virus, entity, EntityLivingBase.class, 10);
                    }
                    break;
                case "33":
                    //Burning
                    if(isTickEvent && !entity.isBurning())
                        entity.setFire(10);
                    break;
                case "34":
                    //Explosion
                    if(isTickEvent && isTickerEqual(cap.getTicker(), 600))
                        entity.world.createExplosion(entity, entity.posX + (random.nextInt(2) == 0 ? random.nextInt(4)  : - random.nextInt(4)), entity.posY, entity.posZ + (random.nextInt(2) == 0 ? random.nextInt(4)  : - random.nextInt(4)), 1.0f, false);
                    break;
                case "35":
                    //Random teleportation
                    if(isTickEvent && isTickerEqual(cap.getTicker(), 600)){
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
                    break;
                case "36":
                    if(!canMutate) {
                        canMutate = true;
                        isMutate = true;
                    }
                    break;
                default:
                    break;
            }
        }
        if(canMutate && !isMutate) canMutate = false;
    }

    public void addOrReaddObservable(){
        this.observablePotions.clear();
        for(String s: this.getDNA().split(":")){
            int i = Integer.parseInt(s);
            if(i == 0) continue;
            //lets use kostyli
            if(i <= 5) this.observablePotions.add(i);
            else if(i == 6) this.observablePotions.add(21);
            else if(i >= 7 && i <= 19) this.observablePotions.add(i + 1);
            else if(i == 20) this.observablePotions.add(22);
            else if(i > 20 && i <= 25) this.observablePotions.add(i + 2);
        }
    }

    public static void spreadCustomEffect(IBioSample sample, EntityLivingBase source, Class<? extends EntityLivingBase> entity, int radius){
        AxisAlignedBB box = source.getEntityBoundingBox().expand(radius, radius, radius);
        List<Entity> entities = source.world.getEntitiesWithinAABBExcludingEntity(source, box);
        for (Entity e : entities){
            if(entity.isInstance(e)) {
                if (e instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) e;
                    IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                    if (!hasFullBioArmor(player))
                        cap.addEffectIntoQueue(sample);
                } else {
                    EntityLivingBase ent = (EntityLivingBase) e;
                    IBioMob cap = ent.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                    cap.addEffectIntoQueue(sample);
                }
            }
        }
        if(source instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) source;
            IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            if (!hasFullBioArmor(player))
                cap.addEffectIntoQueue(sample);
        }
    }
}
