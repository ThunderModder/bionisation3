package com.thunder.event;

import com.thunder.bionisation.Config;
import com.thunder.bionisation.Information;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.samples.EffectFracture;
import com.thunder.laboratory.samples.EffectInternalBleeding;
import com.thunder.mob.BioMobProvider;
import com.thunder.mob.IBioMob;
import com.thunder.network.NetworkHandler;
import com.thunder.network.SyncAllCapMessage;
import com.thunder.network.SyncBloodCapMessage;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

import java.util.Iterator;


public class CapabilityEventHandler {

    public static void register(){
        MinecraftForge.EVENT_BUS.register(new CapabilityEventHandler());
    }

    public static final ResourceLocation BIO_PLAYER_CAPABILITY = new ResourceLocation(Information.MOD_ID, "bio_player");
    public static final ResourceLocation BIO_MOB_CAPABILITY = new ResourceLocation(Information.MOD_ID, "bio_mob");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof EntityPlayer){
            event.addCapability(BIO_PLAYER_CAPABILITY, new BioPlayerProvider());
        }else if(event.getObject() instanceof EntityLivingBase){
            event.addCapability(BIO_MOB_CAPABILITY, new BioMobProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
        if(cap != null && !player.world.isRemote){
            NetworkHandler.network.sendTo(new SyncAllCapMessage(cap.writeToNBT(), player.getEntityId()), (EntityPlayerMP) player);
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        IBioPlayer newCap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
        if(Config.saveEffects){
            IBioPlayer oldCap = event.getOriginal().getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            newCap.copyBioPlayer(oldCap);
            if(event.isWasDeath())
                newCap.setBloodLevel(100);
        }else {
            if (!event.isWasDeath()) {
                IBioPlayer oldCap = event.getOriginal().getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                newCap.copyBioPlayer(oldCap);
            } else {
                newCap.setImmunityLevel(100);
                newCap.setBloodLevel(100);
            }
        }
    }

    @SubscribeEvent
    public void syncBloodHUD(PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.player;
        if(!player.world.isRemote){
            IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            NetworkHandler.network.sendTo(new SyncAllCapMessage(cap.writeToNBT(), player.getEntityId()), (EntityPlayerMP) player);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
        if(!player.world.isRemote)
            NetworkHandler.network.sendTo(new SyncAllCapMessage(cap.writeToNBT(), player.getEntityId()), (EntityPlayerMP) player);
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent event){
        EntityLivingBase ent = event.getEntityLiving();
        World world = ent.world;
        if(!world.isRemote){
            if(ent instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)ent;
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                cap.incrementTicker();
                boolean needSync = false;
                //adding queued effects
                if(!cap.getQueuedList().isEmpty()){
                    for(IBioSample smp : cap.getQueuedList()){
                        cap.addEffect(smp, player);
                    }
                    cap.clearQueuedEffects();
                }
                //effect tickers=========
                Iterator<IBioSample> it = cap.getEffectList().iterator();
                while(it.hasNext()){
                    IBioSample smp = it.next();
                    if(smp.isNeedToBeSynced()) needSync = true;
                    if(smp.isExpired()) {
                        Utilities.clearObservablePotions(smp.getObservablePotionEffects(), player);
                        it.remove();
                        cap.syncAllCap(player);
                    }
                    else smp.performEffectPlayer(event, player, EventType.TICK, cap);
                }
                if(needSync && Utilities.isTickerEqual(cap.getTicker(), 40))
                    cap.syncAllCap(player);
                //=======================
            }else {
                IBioMob cap = ent.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                cap.incrementTicker();
                boolean needSync = false;
                //adding queued effects
                if(!cap.getQueuedList().isEmpty()){
                    for(IBioSample smp : cap.getQueuedList()){
                        cap.addEffect(smp, ent);
                    }
                    cap.clearQueuedEffects();
                }
                //iterating effects
                Iterator<IBioSample> it = cap.getEffectList().iterator();
                while(it.hasNext()){
                    IBioSample smp = it.next();
                    if(smp.isNeedToBeSynced()) needSync = true;
                    if(smp.isExpired()) {
                        Utilities.clearObservablePotions(smp.getObservablePotionEffects(), ent);
                        it.remove();
                        cap.syncAllCap(ent);
                    }
                    else smp.performEffectEntity(event, ent, EventType.TICK, cap);
                }
                if(needSync && Utilities.isTickerEqual(cap.getTicker(), 40))
                    cap.syncAllCap(ent);
            }
        }
    }

    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        EntityLivingBase ent = event.getEntityLiving();
        World world = ent.world;
        if(!world.isRemote){
            if(ent instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)ent;
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                //effect tickers=========
                Iterator<IBioSample> it = cap.getEffectList().iterator();
                while(it.hasNext()){
                    IBioSample smp = it.next();
                    smp.performEffectPlayer(event, player, EventType.HURT, cap);
                }
                //=======================
            }else{
                IBioMob cap = ent.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                Iterator<IBioSample> it = cap.getEffectList().iterator();
                while(it.hasNext()){
                    IBioSample smp = it.next();
                    smp.performEffectEntity(event, ent, EventType.HURT, cap);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityAttack(LivingAttackEvent event) {
        EntityLivingBase ent = event.getEntityLiving();
        World world = ent.world;
        DamageSource dsource = event.getSource();
        if(!world.isRemote){
            if(dsource.getSourceOfDamage() instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)dsource.getSourceOfDamage();
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                //effect tickers=========
                Iterator<IBioSample> it = cap.getEffectList().iterator();
                while(it.hasNext()){
                    IBioSample smp = it.next();
                    smp.performEffectPlayer(event, player, EventType.ATTACK, cap);
                }
                //=======================
            }else if(dsource.getSourceOfDamage() instanceof EntityLivingBase){
                EntityLivingBase entity = (EntityLivingBase)dsource.getSourceOfDamage();
                IBioMob cap = entity.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                Iterator<IBioSample> it = cap.getEffectList().iterator();
                while(it.hasNext()){
                    IBioSample smp = it.next();
                    smp.performEffectEntity(event, entity, EventType.ATTACK, cap);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event){
        EntityLivingBase ent = event.getEntityLiving();
        World world = ent.world;
        if(!world.isRemote){
            if(ent instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)ent;
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                //effect tickers=========
                Iterator<IBioSample> it = cap.getEffectList().iterator();
                while(it.hasNext()){
                    IBioSample smp = it.next();
                    smp.performEffectPlayer(event, player, EventType.DEATH, cap);
                }
                //=======================
            }else{
                IBioMob cap = ent.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                Iterator<IBioSample> it = cap.getEffectList().iterator();
                while(it.hasNext()){
                    IBioSample smp = it.next();
                    smp.performEffectEntity(event, ent, EventType.DEATH, cap);
                }
            }
        }
    }
}
