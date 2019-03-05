package com.thunder.event;

import com.thunder.bionisation.Information;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ParticleEvent {

    public static final ResourceLocation PARTICLE_BLOOD_TEXTURE = new ResourceLocation(Information.MOD_ID  + ":entity/blood_particle");
    public static final ResourceLocation PARTICLE_ENDER_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/ender_particle");
    public static final ResourceLocation PARTICLE_BONE_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/bone_particle");
    public static final ResourceLocation PARTICLE_MYCELIUM_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/mycelium_particle");
    public static final ResourceLocation PARTICLE_GIANT_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/giant_particle");
    public static final ResourceLocation PARTICLE_BLACK_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/black_particle");
    public static final ResourceLocation PARTICLE_GREEN_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/green_particle");
    public static final ResourceLocation PARTICLE_RED_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/red_particle");
    public static final ResourceLocation PARTICLE_WHITE_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/white_particle");
    public static final ResourceLocation PARTICLE_VIOLET_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/violet_particle");
    public static final ResourceLocation PARTICLE_CYAN_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/cyan_particle");

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void spriteRegisterEventPre(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(PARTICLE_BLOOD_TEXTURE);
        event.getMap().registerSprite(PARTICLE_ENDER_TEXTURE);
        event.getMap().registerSprite(PARTICLE_BONE_TEXTURE);
        event.getMap().registerSprite(PARTICLE_MYCELIUM_TEXTURE);
        event.getMap().registerSprite(PARTICLE_GIANT_TEXTURE);
        event.getMap().registerSprite(PARTICLE_BLACK_TEXTURE);
        event.getMap().registerSprite(PARTICLE_GREEN_TEXTURE);
        event.getMap().registerSprite(PARTICLE_RED_TEXTURE);
        event.getMap().registerSprite(PARTICLE_WHITE_TEXTURE);
        event.getMap().registerSprite(PARTICLE_VIOLET_TEXTURE);
        event.getMap().registerSprite(PARTICLE_CYAN_TEXTURE);
    }
}
