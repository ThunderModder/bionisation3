package com.thunder.item;

import com.thunder.bionisation.Information;
import com.thunder.laboratory.EffectContainer;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;


public class CreativeVial extends ItemBionisation {

    public CreativeVial(){
        super();
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItemMainhand();
        if(!worldIn.isRemote) {
            if(stack.hasTagCompound()){
                NBTTagCompound nbt = Utilities.getNbt(stack);
                IBioPlayer cap = playerIn.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                cap.addEffect(Utilities.getNewEffectCopy(Utilities.findEffectById(nbt.getInteger(Utilities.getModIdString("id")))), playerIn);
            }
        }
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for(IBioSample smp : EffectContainer.getInstance().effects){
            if(smp.getType() != SampleType.BACTERIA_CURE && smp.getType() != SampleType.EFFECT_CURE) {
                ItemStack stack = new ItemStack(this);
                NBTTagCompound nbt = Utilities.getNbt(stack);
                nbt.setInteger(Utilities.getModIdString("id"), smp.getId());
                nbt.setString(Utilities.getModIdString("name"), smp.getName());
                nbt.setString(Utilities.getModIdString("type"), smp.getType().toString().toLowerCase());
                nbt.setInteger(Utilities.getModIdString("power"), smp.getPower() + 1);
                nbt.setInteger(Utilities.getModIdString("duration"), smp.getDuration() / 20);
                subItems.add(stack);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if(stack.hasTagCompound()){
            NBTTagCompound nbt = Utilities.getNbt(stack);
            tooltip.add("");
            tooltip.add("Sample: " + TextFormatting.GREEN + nbt.getString(Utilities.getModIdString("name")));
            tooltip.add("ID: " + TextFormatting.GREEN + nbt.getInteger(Utilities.getModIdString("id")));
            tooltip.add("Type: " + TextFormatting.GREEN + nbt.getString(Utilities.getModIdString("type")));
            tooltip.add("Power: " + TextFormatting.GREEN + nbt.getInteger(Utilities.getModIdString("power")));
            tooltip.add("Duration: " + TextFormatting.GREEN + (nbt.getInteger(Utilities.getModIdString("duration")) == 0 ? "Infinite" : nbt.getInteger(Utilities.getModIdString("duration")) + " sec"));
        }
    }
}
