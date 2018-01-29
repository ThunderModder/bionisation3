package com.thunder.laboratory;

import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    //from player to items
    public static void startRevision(EntityPlayer player, IBioPlayer cap, int flag){
        NonNullList<ItemStack> mainInv = player.inventory.mainInventory;
        NonNullList<ItemStack> armorInv = player.inventory.armorInventory;
        NonNullList<ItemStack> offInv = player.inventory.offHandInventory;
        List<IBioSample> effects = cap.getEffectList();
        for(IBioSample smp : effects){
            if(smp.getDangerous()){
                if(flag == 1) {
                    //main inventory
                    fromPlayerToItems(mainInv, smp);
                    //armor inv
                    fromPlayerToItems(armorInv, smp);
                    //offhand inv
                    fromPlayerToItems(offInv, smp);
                }else if(flag == 2){
                    //armor inv
                    fromPlayerToItems(armorInv, smp);
                }
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
        String key = Utilities.getModIdString("eff_list");
        List<Integer> ids = new ArrayList<>();
        for(ItemStack stack : inventory){
            if(!stack.isEmpty()){
                NBTTagCompound nbt = Utilities.getNbt(stack);
                if(nbt.hasKey(key)){
                    String [] eff = nbt.getString(key).split("_");
                    for(String s : eff) {
                        if(!s.isEmpty()) {
                            int id = Integer.parseInt(s);
                            if (!cap.isEffectActive(id)) ids.add(id);
                        }
                    }
                }
            }
        }
        for (Integer i : ids){
            cap.addEffect(Utilities.getNewEffectCopy(Utilities.findEffectById(i)), player);
        }
    }

    private static void fromPlayerToItems(NonNullList<ItemStack> inventory, IBioSample sample){
        String key = Utilities.getModIdString("eff_list");
        for (ItemStack stack : inventory){
            if(!stack.isEmpty()){
                NBTTagCompound nbt = Utilities.getNbt(stack);
                if(nbt.hasKey(key)){
                    String eff = nbt.getString(key);
                    if(!eff.contains("_" + sample.getId() + "_")) {
                        eff += sample.getId() + "_";
                        nbt.setString(key, eff);
                    }
                }else{
                    String eff = "_";
                    eff += sample.getId() + "_";
                    nbt.setString(key, eff);
                }
            }
        }
    }
}
