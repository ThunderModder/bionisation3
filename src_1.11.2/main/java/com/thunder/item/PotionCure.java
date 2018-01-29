package com.thunder.item;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.thunder.laboratory.EffectContainer;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PotionCure extends ItemBionisation {

    public static final String BKEY_ID = Utilities.getModIdString("bcure_id");

    public PotionCure(){
        super();
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {

        if(entityLiving instanceof EntityPlayer && !worldIn.isRemote) {
            EntityPlayer player = (EntityPlayer)entityLiving;
            if(stack.hasTagCompound()){
                NBTTagCompound nbt = Utilities.getNbt(stack);
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                cap.addEffect(Utilities.getNewEffectCopy(Utilities.findEffectById(nbt.getInteger(BKEY_ID))), player);
            }
            if (!player.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
            if (!player.capabilities.isCreativeMode) {
                if (stack.isEmpty()) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }
                if (player != null) {
                    player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
                }
            }
        }
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
       if(stack.hasTagCompound()){
           NBTTagCompound nbt = Utilities.getNbt(stack);
           if(nbt.hasKey(BKEY_ID))
               tooltip.add(ChatFormatting.GREEN + Utilities.findEffectById(nbt.getInteger(BKEY_ID)).getName());
       }
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for(IBioSample smp : EffectContainer.getInstance().effects){
            if(smp.getType() == SampleType.BACTERIA_CURE || smp.getType() == SampleType.EFFECT_CURE) {
                ItemStack stack = new ItemStack(this);
                NBTTagCompound nbt = Utilities.getNbt(stack);
                nbt.setInteger(BKEY_ID, smp.getId());
                subItems.add(stack);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }
}
