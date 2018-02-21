package com.thunder.laboratory;

import com.thunder.laboratory.samples.virus.ICustomVirus;
import com.thunder.laboratory.samples.virus.custom.CustomVirus;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private static final String KEY = Utilities.getModIdString("eff_list");

    //from player to items
    public static void startRevision(EntityPlayer player, IBioPlayer cap, int flag){
        NonNullList<ItemStack> mainInv = player.inventory.mainInventory;
        NonNullList<ItemStack> armorInv = player.inventory.armorInventory;
        NonNullList<ItemStack> offInv = player.inventory.offHandInventory;
        List<IBioSample> effects = cap.getEffectList();
        //common effects
        List<SampleBundle> effs = new ArrayList<>();
        for(IBioSample smp : effects){
            if(smp.getDangerous()){
               if(smp instanceof ICustomVirus) effs.add(new SampleBundle(smp.getId(), smp.getDuration(), smp.getPower(), smp.getDangerous(), smp.getName(), ((ICustomVirus) smp).getDNA(), true));
               else if(smp instanceof IVirus) effs.add(new SampleBundle(smp.getId(), smp.getDuration(), smp.getPower(), smp.getDangerous(), smp.getName(), ((IVirus)smp).getDNA(), false));
               else effs.add(new SampleBundle(smp.getId(), smp.getDuration(), smp.getPower(), smp.getDangerous(), smp.getName(), "", false));
            }
        }
        if(!effs.isEmpty()) {
            if (flag == 1) {
                //main inventory
                fromPlayerToItems(mainInv, effs, KEY);
                //armor inv
                fromPlayerToItems(armorInv, effs, KEY);
                //offhand inv
                fromPlayerToItems(offInv, effs, KEY);
            } else if (flag == 2) {
                //armor inv
                fromPlayerToItems(armorInv, effs, KEY);
            }
        }
    }

    //from items to player
    public static void expose(EntityPlayer player, IBioPlayer cap, int flag){
        NonNullList<ItemStack> mainInv = player.inventory.mainInventory;
        NonNullList<ItemStack> armorInv = player.inventory.armorInventory;
        NonNullList<ItemStack> offInv = player.inventory.offHandInventory;
        if(flag == 1) {
            //main inv
            fromItemsToPlayer(player, mainInv, cap);
            //armor inv
            fromItemsToPlayer(player, armorInv, cap);
            //offhand inv
            fromItemsToPlayer(player, offInv, cap);
        }else if(flag == 2){
            //armor inv
            fromItemsToPlayer(player, armorInv, cap);
        }
    }

    private static void fromItemsToPlayer(EntityPlayer player, NonNullList<ItemStack> inventory, IBioPlayer cap){
        for(ItemStack stack : inventory){
            if(!stack.isEmpty()){
                NBTTagCompound nbt = Utilities.getNbt(stack);
                //add common effects
                if(nbt.hasKey(KEY)){
                    try {
                        List<SampleBundle> effs = Utilities.listFromNBT((NBTTagList) nbt.getTag(KEY));
                        for(SampleBundle bundle : effs){
                            if(bundle.isCustom){
                                cap.addEffect(new CustomVirus(bundle.id, bundle.duration, bundle.power, bundle.dangerous, bundle.name, bundle.dna), player);
                            }else
                                cap.addEffect(Utilities.getNewEffectCopy(Utilities.findEffectById(bundle.id)), player);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void fromPlayerToItems(NonNullList<ItemStack> inventory, List<SampleBundle> effects, String key){
        for (ItemStack stack : inventory){
            if(!stack.isEmpty()){
                NBTTagCompound nbt = Utilities.getNbt(stack);
                try {
                    nbt.setTag(key, Utilities.<SampleBundle>objListToNBT(effects));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class SampleBundle implements Serializable{

        private int id;
        private int duration;
        private int power;
        private boolean dangerous;
        private String name;
        private String dna;
        private boolean isCustom;

        public SampleBundle(int id, int duration, int power, boolean dangerous, String name, String dna, boolean isCustom){
            this.id = id;
            this.duration = duration;
            this.power = power;
            this.dangerous = dangerous;
            this.name = name;
            this.dna = dna;
            this.isCustom = isCustom;
        }
    }
}