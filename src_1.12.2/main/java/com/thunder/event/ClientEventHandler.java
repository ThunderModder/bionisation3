package com.thunder.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.thunder.bionisation.Bionisation;
import com.thunder.bionisation.Config;
import com.thunder.laboratory.ItemManager;
import com.thunder.misc.BloodParticle;
import com.thunder.misc.MyceliumParticle;
import com.thunder.misc.ParticleBStandart;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.sound.SoundHandler;
import com.thunder.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.thunder.util.Constants.*;
import static com.thunder.util.Utilities.isEffectActive;
import static com.thunder.util.Utilities.isTickerEqual;


public class ClientEventHandler extends Gui {

    public static void register(){
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    public static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){

    }

    @SubscribeEvent
    public void renderItemOverlay(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(stack != null && stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if(tag.hasKey(ItemManager.KEY)) {
                event.getToolTip().add(ChatFormatting.RED + I18n.format("tooltip.infected"));
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
                if((isEffectActive(ID_SEABACTERIA, player) || isEffectActive(ID_WAFFINITY_SYMBIONT, player)) && player.isInWater()){
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
                    BloodParticle eff = new BloodParticle(ParticleEvent.PARTICLE_BLOOD_TEXTURE, entity.world, entity.posX, entity.posY + entity.getEyeHeight() - 0.1, entity.posZ, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_ENDERBACTERIA: {
                    ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_ENDER_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_BONEBACTERIA: {
                    ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_BONE_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_MYCELIUMBACTERIA: {
                    MyceliumParticle eff = new MyceliumParticle(ParticleEvent.PARTICLE_MYCELIUM_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_GIANT_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_GIANT_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_ENDER_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_ENDER_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_VAMPIRE_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_BLACK_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_CREEPER_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_GREEN_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_RED_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_RED_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_POLAR_VIRUS: {
                ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_WHITE_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
            case ID_EW_SYMBIOSIS: {
                ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_VIOLET_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            case ID_RS_SYMBIOSIS: {
                ParticleBStandart eff = new ParticleBStandart(ParticleEvent.PARTICLE_CYAN_TEXTURE, entity.world, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(eff);
                }
                break;
            default:
                break;
        }
    }

    @SubscribeEvent
    public void onUpdateEvent(TickEvent.PlayerTickEvent event) {
        if (Config.canShowUpdates && !Bionisation.wasWarnedNewVersion && event.player.world.isRemote && !Bionisation.checker.isLatestVersion()) {
            if(Bionisation.checker.getLatestVersion().equals("") || Bionisation.checker.getNewVersionURL().equals("") || Bionisation.checker.getChanges().equals(""))
                event.player.sendMessage(new TextComponentString(I18n.format("checker.message.cant")));
            else {
                event.player.sendMessage(ForgeHooks.newChatWithLinks(TextFormatting.YELLOW + I18n.format("checker.message.version") + " " + TextFormatting.AQUA + Bionisation.checker.getLatestVersion() + " " + TextFormatting.YELLOW + I18n.format("checker.message.av") + TextFormatting.BLUE + " " +  Bionisation.checker.getNewVersionURL()));
                event.player.sendMessage(new TextComponentString(""));
                event.player.sendMessage(new TextComponentString(TextFormatting.YELLOW + I18n.format("checker.message.changes") + " " + TextFormatting.GRAY + Bionisation.checker.getChanges()));
                Bionisation.wasWarnedNewVersion = true;
            }
        }

    }
}
