package com.thunder.sound;

import com.thunder.bionisation.Information;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;


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
        SoundEvent.REGISTRY.register(id, rl, event);
        id++;
        return event;
    }
}
