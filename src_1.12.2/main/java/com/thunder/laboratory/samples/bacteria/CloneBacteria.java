package com.thunder.laboratory.samples.bacteria;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CloneBacteria extends AbstractEffect {

    public CloneBacteria() {
        this(Constants.DURATION_CLONEBACTERIA, 0);
    }

    public CloneBacteria(int duration, int power) {
        super(Constants.ID_CLONEBACTERIA, duration, power, true, "Clone Bacteria", SampleType.BACTERIA);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK) isExpired = true;
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.DEATH){
            LivingDeathEvent e = (LivingDeathEvent)event;
            EntityLivingBase dent = e.getEntityLiving();
            World world = dent.world;
            for (int i = 0; i < 3; i++){
                //Only for vanilla mobs
                EntityLivingBase ent = (EntityLivingBase)EntityList.createEntityByIDFromName(new ResourceLocation("minecraft:" + dent.getName()), world);
                if(ent != null) {
                    ent.setPosition(dent.posX + world.rand.nextInt(4), dent.posY, dent.posZ + world.rand.nextInt(4));
                    world.spawnEntity(ent);
                }
            }
            isExpired = true;
        }
    }
}
