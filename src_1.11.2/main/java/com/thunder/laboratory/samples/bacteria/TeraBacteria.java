package com.thunder.laboratory.samples.bacteria;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.Event;


public class TeraBacteria extends AbstractEffect {

    public TeraBacteria() {
        this(Constants.DURATION_TERABACTERIA, 0);
    }

    public TeraBacteria(int duration, int power) {
        super(Constants.ID_TERABACTERIA, duration, power, true, "Tera Bacteria", SampleType.BACTERIA);
        observablePotions.add(Constants.POTION_SLOWNESS_ID);
        observablePotions.add(Constants.POTION_SPEED_ID);
        observablePotions.add(Constants.POTION_HASTE_ID);
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Block block = player.world.getBlockState(player.getPosition().down()).getBlock();
            if(block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND || block == Blocks.AIR || block == Blocks.MYCELIUM || block == Blocks.GRASS_PATH || block == Blocks.TALLGRASS){
                Utilities.removeIfActive(player, Constants.POTION_SLOWNESS_ID);
                Utilities.addPotionEffect(player, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
                Utilities.addPotionEffect(player, Constants.POTION_HASTE_ID, power, -1, wasPowerChanged);
            }else{
                Utilities.removeIfActive(player, Constants.POTION_SPEED_ID);
                Utilities.removeIfActive(player, Constants.POTION_HASTE_ID);
                Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}