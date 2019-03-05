package com.thunder.event;

import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class BloodEventHandler {

    public static void register(){
        MinecraftForge.EVENT_BUS.register(new BloodEventHandler());
    }

    @SubscribeEvent
    public void onEntityBloodTick(LivingEvent.LivingUpdateEvent event){
        EntityLivingBase ent = event.getEntityLiving();
        World world = ent.world;
        if(!world.isRemote){
            if(ent instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)ent;
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                int ticker = cap.getTicker();
                if(cap.getBloodLevel() <= 30) {
                    player.attackEntityFrom(DamageSource.GENERIC, player.getMaxHealth() + 1000f);
                    return;
                }
                if(Utilities.isTickerEqual(ticker, 12000)) {
                    cap.addBlood(2);
                    cap.syncBloodCap(player);
                }
            }
        }
    }

}
