package com.thunder.util;

import com.thunder.bionisation.Information;
import com.thunder.item.ItemRegistry;
import com.thunder.laboratory.EffectContainer;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.IVirus;
import com.thunder.laboratory.samples.EffectFracture;
import com.thunder.laboratory.samples.virus.ICustomVirus;
import com.thunder.mob.BioMobProvider;
import com.thunder.mob.IBioMob;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Utilities {

    public static Random random = new Random();

    public static boolean isTickerEqual(int ticker, int value){
        return ticker % value == 0;
    }

    public static boolean getRandom(int value){
        return random.nextInt(100) < value;
    }

    public static NBTTagCompound getNbt(ItemStack itemStack){
        NBTTagCompound nbt = itemStack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            itemStack.setTagCompound(nbt);
        }
        return nbt;
    }

    public static void spreadEffect(IBioSample sample, EntityLivingBase source, Class<? extends EntityLivingBase> entity, int radius){
        if(sample instanceof ICustomVirus) return;
        AxisAlignedBB box = source.getEntityBoundingBox().expand(radius, radius, radius);
        List<Entity> entities = source.world.getEntitiesWithinAABBExcludingEntity(source, box);
        for (Entity e : entities){
            if(entity.isInstance(e)){
                IBioSample effect = getNewEffectCopy(sample);
                effect.setPower(sample.getPower());
                effect.setCanUpdatePower(sample.getCanUpdatePower());
                if(sample instanceof IVirus){
                    IVirus virusFrom = (IVirus)sample;
                    IVirus virusTo = (IVirus)effect;
                    virusTo.setHasDNA(virusFrom.hasDNA());
                    if(virusTo.hasDNA())
                        virusTo.setDNA(virusFrom.getDNA());
                    virusTo.setCanMutate(virusFrom.isCanMutate());
                    virusTo.setHidden(virusFrom.isHidden());
                }
                if(e instanceof EntityPlayer){
                    EntityPlayer player = (EntityPlayer)e;
                    IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                    if(!hasFullBioArmor(player))
                        cap.addEffectIntoQueue(effect);
                }else{
                    EntityLivingBase ent = (EntityLivingBase)e;
                    IBioMob cap = ent.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                    cap.addEffectIntoQueue(effect);
                }
            }
        }
    }

    public static boolean hasFullBioArmor(EntityPlayer player){
        return !player.inventory.armorInventory.get(0).isEmpty() && !player.inventory.armorInventory.get(1).isEmpty() && !player.inventory.armorInventory.get(2).isEmpty()  && !player.inventory.armorInventory.get(3).isEmpty() && player.inventory.armorInventory.get(3).getItem() == ItemRegistry.BIO_HELMET && player.inventory.armorInventory.get(2).getItem() == ItemRegistry.BIO_CHEST && player.inventory.armorInventory.get(1).getItem() == ItemRegistry.BIO_LEGGINGS && player.inventory.armorInventory.get(0).getItem() == ItemRegistry.BIO_BOOTS;
    }

    public static boolean isBioArmorInfected(NonNullList<ItemStack> armor){
        String key = Utilities.getModIdString("eff_list");
        for(ItemStack s : armor){
            if(!s.isEmpty()){
                NBTTagCompound tag = getNbt(s);
                if(tag.hasKey(key)) return true;
            }
        }
        return false;
    }

    public static void removeIfActive(EntityLivingBase entity, int id){
        if(entity.isPotionActive(Potion.getPotionById(id)))
            entity.removePotionEffect(Potion.getPotionById(id));
    }

    public static int getPowerFromImmunity(int immunity){
        if(immunity < 20) return 4;
        else if(immunity < 40) return 3;
        else if(immunity < 60) return 2;
        else if(immunity < 80) return 1;
        else if(immunity < 90) return 0;
        return 0;
    }

    public static String genRandomString(int num) {
        String[] randoms = new String[num];
        for(int i = 0; i < num; i++) {
            char[] word = new char[random.nextInt(8) + 3];
            for(int j = 0; j < word.length; j++) {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randoms[i] = new String(word);
        }
        String result = "";
        for(String s : randoms)
            result += s + " ";
        return result + (random.nextInt(2) == 0 ? "!" : ".");
    }

    public static void startMutation(EntityLivingBase entity, int chance){
        if(entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            if(cap != null) {
                for (IBioSample smp : cap.getEffectList()) {
                    if (smp instanceof IVirus) {
                        IVirus virus = (IVirus) smp;
                        if (virus.isCanMutate() && getRandom(chance)) {
                            mutate(virus, entity);
                        }
                    }
                }
            }
        }else{
            IBioMob cap = entity.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
            if (cap != null) {
                for (IBioSample smp : cap.getEffectList()) {
                    if (smp instanceof IVirus) {
                        IVirus virus = (IVirus) smp;
                        if (virus.isCanMutate() && getRandom(chance)) {
                            mutate(virus, entity);
                        }
                    }
                }
            }
        }
    }

    private static void mutate(IVirus virus, EntityLivingBase entity){
        String prev = virus.getDNA();
        String[] dna = virus.getDNA().split(":");
        for (int i = 0; i < random.nextInt(5); i++) {
            int partNumber = random.nextInt(dna.length);
            dna[partNumber] = "" + (random.nextInt(40));
        }
        String newDNA = "";
        for (int i = 0; i < dna.length; i++) {
            if (i == (dna.length - 1)) newDNA += dna[i];
            else
                newDNA += dna[i] + ":";
        }
        virus.setDNA(newDNA);
        if (virus instanceof ICustomVirus) {
            clearObservablePotions(virus.getObservablePotionEffects(), entity);
            ((ICustomVirus) virus).addOrReaddObservable();
        }
        System.out.println("Mutation. Prev " + prev + " New " + newDNA);
    }

    public static void debugEffect(IBioSample effect,  EntityLivingBase entity){
        System.out.println("***=======Location=======***");
        System.out.println("Position: X= " + entity.posX + " Y= " + entity.posY + " Z= " + entity.posZ);
        debugEffect(effect);
    }

    public static void debugEffect(IBioSample effect){
        System.out.println("***=======Effect debug start=======***");
        System.out.println("Name: " + effect.getName());
        System.out.println("Type: " + effect.getType());
        System.out.println("Id: " + effect.getId());
        System.out.println("Power: " + effect.getPower());
        System.out.println("Cam update power: " + effect.getCanUpdatePower());
        System.out.println("Is infinite: " + effect.isInfinite());
        System.out.println("Duration: " + effect.getDuration());
        System.out.println("Is dangerous: " + effect.getDangerous());
        System.out.println("Is need to be synced: " + effect.isNeedToBeSynced());
        if(effect instanceof IVirus) {
            System.out.println("Is virus: " + (effect instanceof IVirus));
            System.out.println("Has DNA: " + ((IVirus) effect).hasDNA());
            System.out.println("DNA: " + ((IVirus) effect).getDNA());
            System.out.println("Is hidden: " + ((IVirus) effect).isHidden());
            System.out.println("Can mutate: " + ((IVirus) effect).isCanMutate());
            if(effect instanceof ICustomVirus)
                System.out.println("Is custom: " + ((ICustomVirus)effect).isCustom());
        }
        System.out.println("***=======Effect debug end=======***");
    }

    public static String getModIdString(String s){
        return Information.MOD_ID + s;
    }

    public static boolean decreaseOrRemove(EntityPlayer player, int index){
        ItemStack stack = player.inventory.getStackInSlot(index);
        if(!stack.isEmpty()){
            if(stack.getCount() > 1) {
                stack.setCount(stack.getCount() - 1);
                player.inventoryContainer.detectAndSendChanges();
                return true;
            }
            else {
                player.inventory.setInventorySlotContents(index, ItemStack.EMPTY);
                player.inventoryContainer.detectAndSendChanges();
                return true;
            }
        }
        return false;
    }

    public static IBioSample findEffectByName(String name){
        for(IBioSample smp : EffectContainer.getInstance().effects)
            if(smp.getName().equalsIgnoreCase(name)) return smp;
        return null;
    }

    public static IBioSample findEffectById(Integer id){
        for(IBioSample smp : EffectContainer.getInstance().effects)
            if(smp.getId() == id) return smp;
        return null;
    }

    public static <T> T getNewEffectCopy(T sample){
        try {
            return (T)sample.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void applyOrRemoveAttribute(boolean expired, IAttributeInstance attr, AttributeModifier modifier){
        if(!expired && !attr.hasModifier(modifier))
            attr.applyModifier(modifier);
        else if(attr.hasModifier(modifier))
            attr.removeModifier(modifier);
    }

    public static void addPotionEffect(EntityLivingBase e, int id, int power, int duration, boolean wasPowerChanged){
        boolean canAdd = false;
        if(e.isPotionActive(Potion.getPotionById(id))){
            PotionEffect p = e.getActivePotionEffect(Potion.getPotionById(id));
            if(p.getAmplifier() < power || p.getDuration() <= 1)
                canAdd = true;
            if(wasPowerChanged) e.removePotionEffect(Potion.getPotionById(id));
        }else
            canAdd = true;
        if(canAdd){
            if (duration == -1)
                e.addPotionEffect(new PotionEffect(Potion.getPotionById(id), Integer.MAX_VALUE, power));
            else
                e.addPotionEffect(new PotionEffect(Potion.getPotionById(id), duration, power));
        }
    }

    public static void addPotionEffect(EntityLivingBase e, int id, int power, int duration){
        boolean canAdd = false;
        if(e.isPotionActive(Potion.getPotionById(id))){
            PotionEffect p = e.getActivePotionEffect(Potion.getPotionById(id));
            if(p.getAmplifier() < power  || p.getDuration() <= 1)
                canAdd = true;
        }else
            canAdd = true;
        if(canAdd){
            if (duration == -1)
                e.addPotionEffect(new PotionEffect(Potion.getPotionById(id), Integer.MAX_VALUE, power));
            else
                e.addPotionEffect(new PotionEffect(Potion.getPotionById(id), duration, power));
        }
    }

    public static boolean isEffectActive(int id,  EntityLivingBase entity){
        if(entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entity;
            IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            if(cap != null) {
                for (IBioSample b : cap.getEffectList())
                    if (b.getId() == id) return true;
            }
        }else {
            IBioMob cap = entity.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
            if (cap != null) {
                for (IBioSample b : cap.getEffectList())
                    if (b.getId() == id) return true;
            }
        }
        return false;
    }

    public static void addEffectToLiving(IBioSample sample, EntityLivingBase entity){
        if(entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entity;
            IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            cap.addEffectIntoQueue(sample);
        }else {
            IBioMob cap = entity.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
            cap.addEffectIntoQueue(sample);
        }
    }

    public static boolean isEffectActive(IBioSample sample, EntityLivingBase entity){
        if(entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entity;
            IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            if(cap != null) {
                for (IBioSample b : cap.getEffectList())
                    if (b.getId() == sample.getId()) return true;
            }
        }else {
            IBioMob cap = entity.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
            if (cap != null) {
                for (IBioSample b : cap.getEffectList())
                    if (b.getId() == sample.getId()) return true;
            }
        }
        return false;
    }

    public static void clearObservablePotions(List<Integer> list, EntityLivingBase player){
        for (Integer i : list){
            if(player.isPotionActive(Potion.getPotionById(i))) player.removePotionEffect(Potion.getPotionById(i));
        }
    }

    public static <T> NBTTagList objListToNBT(List<T> objects) throws IOException{
        NBTTagList nbtTagList = new NBTTagList();
        for(T obj : objects){
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByteArray("bio_sample", convertToByteArray(obj));
            nbtTagList.appendTag(tag);
        }
        return nbtTagList;
    }

    public static <T> List<T> listFromNBT(NBTTagList nbtTagList) throws IOException, ClassNotFoundException{
        List<T> objects = new ArrayList<>();
        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            NBTTagCompound nbt = nbtTagList.getCompoundTagAt(i);
            T obj = convertFromByteArray(nbt.getByteArray("bio_sample"));
            objects.add(obj);
        }
        return objects;
    }

    private static <T> byte[] convertToByteArray(T object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private static <T> T convertFromByteArray(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            return (T)in.readObject();
        }
    }
}
