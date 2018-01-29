package com.thunder.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.thunder.bionisation.Config;
import com.thunder.bionisation.Information;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.IVirus;
import com.thunder.misc.*;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.sound.SoundHandler;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.ARBShaderObjects;

import java.io.IOException;

import static com.thunder.util.Utilities.*;
import static com.thunder.util.Constants.*;


public class ClientEventHandler extends Gui {

    public static void register(){
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    public static final Minecraft mc = Minecraft.getMinecraft();

    public static final ResourceLocation PARTICLE_BLOOD_TEXTURE = new ResourceLocation(Information.MOD_ID + ":entity/blood_particle");
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

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){

    }

    @SubscribeEvent
    public void renderItemOverlay(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(stack != null) {
            String key = Utilities.getModIdString("eff_list");
            NBTTagCompound tag = getNbt(stack);
            if(tag.hasKey(key)) {
                event.getToolTip().add(ChatFormatting.RED + "Infected");
            }
        }
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase ent = event.getEntityLiving();
        World world = ent.world;
        if(world.isRemote) {
            if (ent instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) ent;
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                cap.incrementTicker();
                //SOUND LACK OF BLOOD
                if (isTickerEqual(cap.getTicker(), 100) && cap.getBloodLevel() < 60) {
                    world.playSound(player, player.getPosition(), SoundHandler.SOUND_HBEAT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
                if (isTickerEqual(cap.getTicker(), 200) && cap.getBloodLevel() < 60)
                    world.playSound(player, player.getPosition(), SoundHandler.SOUND_HBREATH, SoundCategory.PLAYERS, 1.0F, 1.0F);
                //effect debug
                if(isEffectActive(ID_SEABACTERIA, player) && player.isInWater()){
                    player.motionX *= 1.2000000000000003D;
                    player.motionZ *= 1.2000000000000003D;
                }
                //AER VIRUS DEBUG
                if(isEffectActive(ID_AER_VIRUS, player)){
                    if(player.world.isRaining() || player.world.isThundering()){
                        if(Utilities.isTickerEqual(cap.getTicker(), 20)) player.addVelocity(player.world.rand.nextDouble() * 0.5, player.world.rand.nextDouble() * 0.5, player.world.rand.nextDouble() * 0.5);
                    }
                }
                //RABIES EFFECT AUTO ATTACK
                if(isEffectActive(ID_RABIES_VIRUS, player) && player.ticksExisted % 15 == 0)
                    if(Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.entityHit != null)
                        KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
            }
            //EFFECT BLEEDING PARTICLES
            if (isEffectActive(ID_BLEEDING, ent)  && ent.ticksExisted % 10 == 0)
                generateParticles(ent, ID_BLEEDING);
            //EFFECT ENDER BACTERIA
            if (isEffectActive(ID_ENDERBACTERIA, ent))
                generateParticles(ent, ID_ENDERBACTERIA);
            //EFFECT BONE BACTERIA
            if (isEffectActive(ID_BONEBACTERIA, ent))
                generateParticles(ent, ID_BONEBACTERIA);
            //EFFECT MYCELIUM BACTERIA
            if (isEffectActive(ID_MYCELIUMBACTERIA, ent))
                generateParticles(ent, ID_MYCELIUMBACTERIA);
            //EFFECT GIANT VIRUS
            if (isEffectActive(ID_GIANT_VIRUS, ent) && ent.ticksExisted % 10 == 0)
                generateParticles(ent, ID_GIANT_VIRUS);
            //EFFECT ENDER VIRUS
            if (isEffectActive(ID_ENDER_VIRUS, ent)  && ent.ticksExisted % 10 == 0)
                generateParticles(ent, ID_ENDER_VIRUS);
            //EFFECT BAT VIRUS
            if (isEffectActive(ID_VAMPIRE_VIRUS, ent)  && ent.ticksExisted % 10 == 0)
                generateParticles(ent, ID_VAMPIRE_VIRUS);
            //EFFECT CREEPER VIRUS
            if (isEffectActive(ID_CREEPER_VIRUS, ent)  && ent.ticksExisted % 10 == 0)
                generateParticles(ent, ID_CREEPER_VIRUS);
            //EFFECT RED VIRUS
            if (isEffectActive(ID_RED_VIRUS, ent)  && ent.ticksExisted % 10 == 0)
                generateParticles(ent, ID_RED_VIRUS);
            //EFFECT POLAR VIRUS
            if (isEffectActive(ID_POLAR_VIRUS, ent)  && ent.ticksExisted % 10 == 0)
                generateParticles(ent, ID_POLAR_VIRUS);
            //EFFECT EW SYMBIOSIS
            if (isEffectActive(ID_EW_SYMBIOSIS, ent)  && ent.ticksExisted % 10 == 0)
                generateParticles(ent, ID_EW_SYMBIOSIS);
            //EFFECT RS SYMBIOSIS
            if (isEffectActive(ID_RS_SYMBIOSIS, ent)  && ent.ticksExisted % 10 == 0)
                generateParticles(ent, ID_RS_SYMBIOSIS);
        }
    }

    public void generateParticles(Entity entity, int id) {
        double motionX = entity.world.rand.nextGaussian() * 0.02D;
        double motionY = entity.world.rand.nextGaussian() * 0.02D;
        double motionZ = entity.world.rand.nextGaussian() * 0.02D;
        switch(id){
            case ID_BLEEDING: {
                    BloodParticle eff = new BloodParticle(PARTICLE_BLOOD_TEXTURE, entity.world, entity.posX, entity.posY + entity.getEyeHeight() - 0.1, entity.posZ, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_ENDERBACTERIA: {
                    ParticleBStandart eff = new ParticleBStandart(PARTICLE_ENDER_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_BONEBACTERIA: {
                    ParticleBStandart eff = new ParticleBStandart(PARTICLE_BONE_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_MYCELIUMBACTERIA: {
                    MyceliumParticle eff = new MyceliumParticle(PARTICLE_MYCELIUM_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_GIANT_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(PARTICLE_GIANT_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_ENDER_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(PARTICLE_ENDER_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_VAMPIRE_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(PARTICLE_BLACK_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_CREEPER_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(PARTICLE_GREEN_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_RED_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(PARTICLE_RED_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_POLAR_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(PARTICLE_WHITE_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
            case ID_EW_SYMBIOSIS: {
                ParticleBStandart eff = new ParticleBStandart(PARTICLE_VIOLET_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_RS_SYMBIOSIS: {
                ParticleBStandart eff = new ParticleBStandart(PARTICLE_CYAN_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            default:
                break;
        }
    }

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
