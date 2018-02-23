package com.thunder.event;

import com.thunder.biome.InfectedBiome;
import com.thunder.bionisation.Config;
import com.thunder.item.ItemBlood;
import com.thunder.item.ItemRegistry;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.ItemManager;
import com.thunder.laboratory.samples.*;
import com.thunder.laboratory.samples.bacteria.*;
import com.thunder.laboratory.samples.virus.*;
import com.thunder.mob.BioMobProvider;
import com.thunder.mob.IBioMob;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.thunder.util.Constants.*;
import static com.thunder.util.Utilities.*;


public class EffectEventHandler {

    public static void register(){
        MinecraftForge.EVENT_BUS.register(new EffectEventHandler());
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase ent = event.getEntityLiving();
        World world = ent.world;
        if (!world.isRemote) {
            if(ent instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) ent;
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                int ticker = cap.getTicker();
                //mutation
                if(isTickerEqual(cap.getTicker(), MUTATION_PROCESS_DELAY) && getRandom(CHANCE_MUTATION_PROCESS))
                    startMutation(player, CHANCE_MUTATION_VIRUS);
                //revision
                if(isTickerEqual(ticker, 300) && getRandom(REVISION_CHANCE)){
                    if(!hasFullBioArmor(player))
                        ItemManager.startRevision(player, cap, 1);
                    else
                        ItemManager.startRevision(player, cap, 2);
                }
                if(isTickerEqual(ticker, 600) && getRandom(REVISION_CHANCE)){
                    if(!hasFullBioArmor(player))
                        ItemManager.expose(player, cap, 1);
                    else if(isBioArmorInfected(player.inventory.armorInventory))
                        ItemManager.expose(player, cap, 2);
                }
                //effect implementation
                //EFFECT SUNSTROKE
                Biome biome = world.getBiome(player.getPosition());
                if(Utilities.isTickerEqual(ticker, 1200) && getRandom(CHANCE_SUNSTROKE) && (biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT)){
                    if(player.inventory.armorInventory.get(3).isEmpty() && world.isDaytime() && !world.isRaining() && world.canBlockSeeSky(player.getPosition()))
                        cap.addEffect(new EffectSunstroke(DURATION_SUNSTROKE, 0), player);
                }
                //EFFECT_COLD
                if(Utilities.isTickerEqual(ticker, 6000) && cap.getImmunityLevel() < 80 && getRandom(CHANCE_COLD) && (biome == Biomes.COLD_BEACH || biome == Biomes.COLD_TAIGA || biome == Biomes.COLD_TAIGA_HILLS || biome == Biomes.ICE_MOUNTAINS || biome == Biomes.ICE_PLAINS || biome == Biomes.MUTATED_TAIGA_COLD || biome == Biomes.FROZEN_OCEAN || biome == Biomes.FROZEN_RIVER)){
                    if(player.inventory.armorInventory.get(3).isEmpty() && player.inventory.armorInventory.get(2).isEmpty() && player.inventory.armorInventory.get(1).isEmpty() && player.inventory.armorInventory.get(0).isEmpty()){
                        cap.addEffect(new EffectCold(DURATION_COLD, 0), player);
                    }
                }
                //EFFECT_FATIGUE
                if(cap.getImmunityLevel() < IMMUNITY_LEVEL_FATIGUE && isTickerEqual(ticker, 40))
                    cap.addEffect(new EffectFatigue(DURATION_FATIGUE, 1), player);
                //EFFECT LACKOFBLOOD
                if(cap.getBloodLevel() < BLOOD_LEVEL_LACKOFBLOOD && isTickerEqual(ticker, 40))
                    cap.addEffect(new EffectLackOfBlood(DURATION_LACKOFBLOOD, 1), player);
                //SWAMP BACTERIA
                if(isTickerEqual(ticker, 1200) && player.isInWater() && (biome == Biomes.SWAMPLAND || biome == Biomes.MUTATED_SWAMPLAND) && getRandom(CHANCE_SWAMPBACTERIA))
                    if(!hasFullBioArmor(player))
                        cap.addEffect(new SwampBacteria(DURATION_SWAMPBACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                //EFFECT WATER BACTERIA
                if(isTickerEqual(ticker, 2400) && player.isInWater() && cap.getImmunityLevel() < 80 && getRandom(CHANCE_WATERBACTERIA))
                    cap.addEffect(new WaterBacteria(DURATION_WATERBACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                //EFFECT MYCELIUM BACTERIA
                if(isTickerEqual(ticker, 400) && player.inventory.armorInventory.get(0).isEmpty() && player.world.getBlockState(player.getPosition().down()).getBlock() == Blocks.MYCELIUM && getRandom(CHANCE_MYCELIUMBACTERIA))
                    cap.addEffect(new MyceliumBacteria(DURATION_MYCELIUMBACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                //AER VIRUS
                if(isTickerEqual(ticker, 2400) && cap.getImmunityLevel() < IMMUNITY_LEVEL_AER_VIRUS && getRandom(CHANCE_AER_VIRUS))
                    cap.addEffect(new AerVirus(STANDART_VIRUS_DURATION, 1), player);
                //FOREST EFFECTS
                if(biome == InfectedBiome.INFECTED_BIOME) {
                    if(player.isInWater() && !hasFullBioArmor(player))
                        addPotionEffect(player, POTION_POISON_ID, 1, 40);
                    if(isTickerEqual(ticker, 300) && player.inventory.armorInventory.get(0).isEmpty() && world.getBlockState(player.getPosition().down()) != Blocks.AIR)
                        cap.addEffect(new TeraBacteria(DURATION_TERABACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                    if (isTickerEqual(ticker, 1200)) {
                        addPotionEffect(player, POTION_WEAKENSS_ID, 1, 100);
                    }
                    if (isTickerEqual(ticker, 2400)) {
                        addPotionEffect(player, POTION_BLINDNESS_ID, 1, 100);
                        addPotionEffect(player, POTION_SLOWNESS_ID, 1, 100);
                    }
                    if (isTickerEqual(ticker, 3600)) {
                        addPotionEffect(player, POTION_WITHER_ID, 1, 100);
                    }
                }
            }else{
                IBioMob cap = ent.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                //mutation
                if(isTickerEqual(cap.getTicker(), MUTATION_PROCESS_DELAY) && getRandom(CHANCE_MUTATION_PROCESS))
                    startMutation(ent, CHANCE_MUTATION_VIRUS);
                if((ent instanceof EntityWolf)){
                    if(Utilities.isTickerEqual(cap.getTicker(), 2400) && getRandom(CHANCE_RABIES_VIRUS))
                        cap.addEffect(new Rabies(STANDART_VIRUS_DURATION, 1), ent);
                }
                Biome biome = world.getBiome(ent.getPosition());
                //INFECTED FOREST EFFECTS
                if(biome == InfectedBiome.INFECTED_BIOME){
                    //FOREST RABIES
                    if(isTickerEqual(cap.getTicker(), 1200)) {
                        if(getRandom(CHANCE_RABIES_VIRUS * 3))
                            cap.addEffect(new Rabies(STANDART_VIRUS_DURATION, 1), ent);
                        if(getRandom(CHANCE_BLEEDING))
                            cap.addEffect(new EffectBleeding(DURATION_BLEEDING, 0), ent);
                    }
                    if(ent.isInWater())
                        addPotionEffect(ent, POTION_POISON_ID, 1, 40);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityUsingItem(LivingEntityUseItemEvent.Finish event) {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.world;
        if(!world.isRemote) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                ItemStack stack = event.getItem();
                if(!stack.isEmpty()){
                    Item item = stack.getItem();
                    //CURE EFFECT SUNSTROKE
                    if(item.getItemUseAction(stack) == EnumAction.DRINK && cap.isEffectActive(ID_SUNSTROKE))
                        cap.removeEffect(ID_SUNSTROKE, player);
                    //EFFECT FOODPOISONING
                    if(item.equals(Items.POISONOUS_POTATO) || item.equals(Items.ROTTEN_FLESH))
                        cap.addEffect(new EffectFoodPoisoning(DURATION_FOODPOISONING, 2), player);
                    else if((item.equals(Items.PORKCHOP) || item.equals(Items.BEEF) || item.equals(Items.CHICKEN) || item.equals(Items.RABBIT) || item.equals(Items.MUTTON)) && getRandom(CHANCE_FOODPOISONING))
                        cap.addEffect(new EffectFoodPoisoning(DURATION_FOODPOISONING, 0), player);
                    else if (item.equals(Items.SPIDER_EYE)) {
                        if(getRandom(CHANCE_FOODPOISONING * 2))
                            cap.addEffect(new EffectFoodPoisoning(DURATION_FOODPOISONING, 1), player);
                        //BLACK BACTERIA
                        if(getRandom(CHANCE_BLACKBACTERIA))
                            cap.addEffect(new BlackBacteria(DURATION_BLACKBACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                    }
                    else if(item.equals(Items.FISH)){
                        if(getRandom(CHANCE_FOODPOISONING))
                            cap.addEffect(new EffectFoodPoisoning(DURATION_FOODPOISONING, 1), player);
                        //SEA BACTERIA
                        if(getRandom(CHANCE_SEABACTERIA))
                            cap.addEffect(new SeaBacteria(DURATION_SEABACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                    }else if((item.equals(Items.POTATO) || item.equals(Items.CARROT) || item.equals(Items.BEETROOT)) && getRandom((int)(CHANCE_FOODPOISONING) / 3))
                        cap.addEffect(new EffectFoodPoisoning(DURATION_FOODPOISONING, 0), player);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        EntityLivingBase target = event.getEntityLiving();
        DamageSource source = event.getSource();
        World world = target.world;
        if(!world.isRemote){
            if(target instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)target;
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                //EFFECT BLEEDING
                if(source.getSourceOfDamage() instanceof EntityLivingBase && getRandom(CHANCE_BLEEDING))
                    cap.addEffect(new EffectBleeding(DURATION_BLEEDING, 0), player);
                //EFFECT FRACTURE
                if(source == DamageSource.FALL) {
                    IBioSample smp = cap.getEffectById(ID_FRACTURE);
                    if(smp != null){
                        cap.addEffect(new EffectBleeding(DURATION_BLEEDING, 0), player);
                        smp.setPower(smp.getPower() * 2);
                    }
                    else if (Utilities.getRandom(CHANCE_FRACTURE))
                        cap.addEffect(new EffectFracture(DURATION_FRACTURE, 2), player);
                }
                //GLOWING BACTERIUM BLAZE
                if(source.getEntity() instanceof EntityBlaze && getRandom(CHANCE_GLOWINGBACTERIABL))
                    cap.addEffect(new GlowingBacteria(DURATION_GLOWINGBACTERIA, 0), player);
                //CACTUS BACTERIA
                if(source == DamageSource.CACTUS && getRandom(CHANCE_CACTUSBACTERIA))
                    cap.addEffect(new CactusBacteria(DURATION_CACTUSBACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                //BONE BACTERIA
                if((source.getEntity() instanceof EntitySkeleton || source.getEntity() instanceof EntityStray) && getRandom(CHANCE_BONEBACTERIA))
                    cap.addEffect(new BoneBacteria(DURATION_BONEBACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                //SMALL GREEN BACTERIA
                boolean fromCreeper = false;
                if(source.getSourceOfDamage() instanceof EntityCreeper && getRandom(CHANCE_SMALLGREENBACTERIA)) {
                    fromCreeper = true;
                    cap.addEffect(new SGreenBacteria(DURATION_SMALLGREENBACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                }
                if(source.isExplosion() && !fromCreeper && cap.isEffectActive(ID_SMALLGREENBACTERIA)){
                    player.attackEntityFrom(DamageSource.GENERIC, 10000f);
                }
                //BLOOD VIRUS
                if(source.getSourceOfDamage() instanceof EntityWitch && isEffectActive(ID_BLOOD_VIRUS, (EntityLivingBase)source.getSourceOfDamage()))
                   cap.addEffect(new BloodVirus(STANDART_VIRUS_DURATION, 1), player);
                //SKULL VIRUS
                if((source.getEntity() instanceof EntitySkeleton || source.getEntity() instanceof EntityStray) && isEffectActive(ID_SKULL_VIRUS, (EntityLivingBase)source.getEntity()))
                    cap.addEffect(new SkullVirus(DURATION_SKULL_VIRUS, 1), player);
                //POLAR VIRUS
                if(source.getSourceOfDamage() instanceof EntityPolarBear && getRandom(CHANCE_POLAR_VIRUS))
                    cap.addEffect(new PolarVirus(STANDART_VIRUS_DURATION, 2), player);
            }else{
                IBioMob cap = target.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                //EFFECT BLEEDING
                if(source.getSourceOfDamage() instanceof EntityLivingBase && getRandom(CHANCE_BLEEDING * 3))
                    cap.addEffect(new EffectBleeding(DURATION_BLEEDING, random.nextInt(3)), target);
                if(source.getSourceOfDamage() instanceof EntityPlayer){
                    EntityPlayer player = (EntityPlayer)source.getSourceOfDamage();
                    IBioPlayer pcap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                    //PTERO VIRUS
                    if(target instanceof EntityChicken && getRandom(CHANCE_PTERO_VIRUS)) pcap.addEffect(new PteroVirus(STANDART_VIRUS_DURATION, 0), player);
                }
            }
            //ALL OTHER
            //WITHER VIRUS
            if((source.getSourceOfDamage() instanceof EntityWitherSkull || source.getSourceOfDamage() instanceof EntityWitherSkeleton) && getRandom(CHANCE_WITHER_VIRUS))
                addEffectToLiving(new WitherVirus(STANDART_VIRUS_DURATION, 1), target);
        }
    }

    @SubscribeEvent
    public void onEntityAttack(LivingAttackEvent event) {
        EntityLivingBase target = event.getEntityLiving();
        World world = target.world;
        DamageSource dsource = event.getSource();
        if(!world.isRemote){
            if(dsource.getSourceOfDamage() instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)dsource.getSourceOfDamage();
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                //BLACK BACTERIA
                if(player.getHeldItem(player.getActiveHand()).isEmpty() && (target instanceof EntitySpider || target instanceof EntityCaveSpider) && getRandom(CHANCE_BLACKBACTERIA))
                    cap.addEffect(new BlackBacteria(DURATION_BLACKBACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
                //ENDER BACTERIA
                if(player.getHeldItem(player.getActiveHand()).isEmpty() && target instanceof EntityEnderman && getRandom(CHANCE_ENDERBACTERIA))
                    cap.addEffect(new EnderBacteria(DURATION_ENDERBACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
            }
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.SpecialSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(!(entity instanceof EntityPlayer)) {
            IBioMob cap = entity.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
            if(cap != null) {
                //BIG GREEN BACTERIA
                if (entity instanceof EntityCreeper && getRandom(CHANCE_BIGGREENBACTERIA))
                    cap.addEffect(new BGreenBacteria(DURATION_BIGGREENBACTERIA, random.nextInt(4)), entity);
                //CLONE BACTERIA
                if (entity instanceof EntityMob && getRandom(CHANCE_CLONEBACTERIA))
                    cap.addEffect(new CloneBacteria(DURATION_CLONEBACTERIA, 1), entity);
                //GIANT VIRUS
                if(entity instanceof EntityMob && getRandom(CHANCE_GIANT_VIRUS))
                    cap.addEffect(new GiantVirus(DURATION_GIANT_VIRUS, 2), entity);
                //ENDER VIRUS
                if((entity instanceof EntityEnderman || entity instanceof EntityEndermite) && getRandom(CHANCE_ENDER_VIRUS))
                    cap.addEffect(new EnderVirus(STANDART_VIRUS_DURATION, 2), entity);
                //BRAIN VIRUS
                if((entity instanceof EntityZombie) && getRandom(CHANCE_BRAIN_VIRUS))
                    cap.addEffect(new BrainVirus(STANDART_VIRUS_DURATION, 1), entity);
                //BAT VIRUS
                if(entity instanceof EntityBat && getRandom(CHANCE_VAMPIRE_VIRUS))
                    cap.addEffect(new BatVirus(STANDART_VIRUS_DURATION, 1), entity);
                //BLOOD VIRUS
                if(entity instanceof EntityWitch && getRandom(CHANCE_BLOOD_VIRUS))
                    cap.addEffect(new BloodVirus(STANDART_VIRUS_DURATION, 1), entity);
                //CREEPER VIRUS
                if(entity instanceof EntityCreeper && getRandom(CHANCE_CREEPER_VIRUS))
                    cap.addEffect(new CreeperVirus(STANDART_VIRUS_DURATION, 1), entity);
                //RED VIRUS
                if(entity instanceof EntitySpider && getRandom(CHANCE_RED_VIRUS))
                    cap.addEffect(new RedVirus(STANDART_VIRUS_DURATION, 1), entity);
                //OCEAN VIRUS
                if(entity instanceof EntityGuardian && getRandom(CHANCE_OCEAN_VIRUS))
                    cap.addEffect(new OceanVirus(STANDART_VIRUS_DURATION, 1), entity);
                //SKULL VIRUS
                if((entity instanceof EntitySkeleton || entity instanceof EntityStray) && getRandom(CHANCE_SKULL_VIRUS))
                    cap.addEffect(new SkullVirus(DURATION_SKULL_VIRUS, 1), entity);
                //DESERT VIRUS
                if(entity instanceof EntityHusk && getRandom(CHANCE_DESERT_VIRUS))
                    cap.addEffect(new DesertVirus(DURATION_DESERT_VIRUS, 1), entity);
                //INFECTED FOREST
                World world = event.getWorld();
                Biome biome = world.getBiome(entity.getPosition());
                if(biome == InfectedBiome.INFECTED_BIOME){
                    //FOREST GIANT VIRUS
                    cap.addEffect(new GiantVirus(DURATION_GIANT_VIRUS, 2), entity);
                    //FOREST BIG GREEN BACTERIA
                    if(entity instanceof EntityCreeper)
                        cap.addEffect(new BGreenBacteria(DURATION_BIGGREENBACTERIA, random.nextInt(4)), entity);
                    //FOREST RED VIRUS
                    if(entity instanceof EntitySpider)
                        cap.addEffect(new RedVirus(STANDART_VIRUS_DURATION, 1), entity);
                    //FOREST SKULL VIRUS
                    if((entity instanceof EntitySkeleton || entity instanceof EntityStray))
                        cap.addEffect(new SkullVirus(DURATION_SKULL_VIRUS, 1), entity);
                    //FOREST BAT VIRUS
                    if(entity instanceof EntityBat)
                        cap.addEffect(new BatVirus(STANDART_VIRUS_DURATION, 1), entity);
                    //FOREST HEALTH BOOST
                    if(entity instanceof EntityZombie) {
                        addPotionEffect(entity, POTION_HEALTHBOOST_ID, 2, -1);
                        cap.addEffect(new BrainVirus(STANDART_VIRUS_DURATION, 1), entity);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerBreakBlock(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        Block block = event.getState().getBlock();
        if(player != null && !player.world.isRemote) {
            IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            //GLOWING BACTERIUM GLOWSTONE
            if(event.getState().getBlock().equals(Blocks.GLOWSTONE) && getRandom(CHANCE_GLOWINGBACTERIAGS))
                cap.addEffect(new GlowingBacteria(DURATION_GLOWINGBACTERIA, 0), player);
            //TERA BACTERIA
            if(player.getHeldItem(player.getActiveHand()).isEmpty() && getRandom(CHANCE_TERABACTERIA) && (block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND || block == Blocks.MYCELIUM || block == Blocks.GRASS_PATH))
                cap.addEffect(new TeraBacteria(DURATION_TERABACTERIA, getPowerFromImmunity(cap.getImmunityLevel())), player);
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event){
        //Modifying drops
        EntityLivingBase entity = event.getEntityLiving();
        if(!entity.world.isRemote) {
            if(Config.canSpawnBloodVial && entity instanceof EntityCreature && getRandom(20)){
                ItemStack stack = new ItemStack(ItemRegistry.ITEM_BLOOD);
                NBTTagCompound tag = getNbt(stack);
                tag.setString(ItemBlood.BLOOD_KEY, entity.getName());
                entity.entityDropItem(stack, 0.1f);
            }
            if (entity instanceof EntityBat) {
                if (getRandom(20)) entity.dropItem(ItemRegistry.BAT_WING, 1);
            } else if (entity instanceof EntityWolf) {
                if (getRandom(25)) entity.dropItem(ItemRegistry.WOLFS_TOOTH, 1);
            } else if (entity instanceof EntityVex) {
                if (getRandom(85)) entity.dropItem(ItemRegistry.SPECTRAL_DUST, 4);
            } else if (entity instanceof EntitySpider) {
                if (getRandom(45)) entity.dropItem(ItemRegistry.SPIDER_LEG, random.nextInt(4) + 1);
            } else if (entity instanceof EntityWitherSkeleton || entity instanceof EntityWither) {
                if (getRandom(20)) entity.dropItem(ItemRegistry.DARK_HEART, 1);
            } else if (entity instanceof EntityGuardian || entity instanceof EntityElderGuardian) {
                if (getRandom(30)) entity.dropItem(ItemRegistry.GUARDIAN_BRAIN, 1);
            } else if (entity instanceof EntityWitch) {
                if (getRandom(45)) entity.dropItem(ItemRegistry.STRANGE_LIQUID, 1);
            } else if (entity instanceof EntityChicken) {
                if (getRandom(25)) entity.dropItem(ItemRegistry.CHICKEN_HEAD, 1);
            } else if (entity instanceof EntityBlaze) {
                if (getRandom(55)) entity.dropItem(ItemRegistry.BLAZE_CORE, 1);
            } else if (entity instanceof EntityCreeper) {
                if (getRandom(45)) entity.dropItem(ItemRegistry.HEART_OF_CREEPER, 1);
            } else if (entity instanceof EntityEnderman || entity instanceof EntityEndermite) {
                if (getRandom(65)) entity.dropItem(ItemRegistry.ENDER_SUBSTANCE, random.nextInt(2) + 1);
            }
        }
    }
}
