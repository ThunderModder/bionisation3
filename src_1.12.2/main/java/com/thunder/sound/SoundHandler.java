package com.thunder.sound;

import com.thunder.bionisation.Information;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class SoundHandler {

    public static SoundEvent SOUND_HBEAT, SOUND_HBREATH;
    public static int id = 0;

    public static void init(){
        id = SoundEvent.REGISTRY.getKeys().size();
        SOUND_HBEAT = registerSound("player_hbeat");
        SOUND_HBREATH = registerSound("player_breath");
    }

    public static SoundEvent registerSound(String name){
        ResourceLocation rl = new ResourceLocation(Information.MOD_ID, name);
        SoundEvent event = new SoundEvent(rl);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        //SoundEvent.REGISTRY.register(id, rl, event);
       // id++;
        return event;
    }
}
