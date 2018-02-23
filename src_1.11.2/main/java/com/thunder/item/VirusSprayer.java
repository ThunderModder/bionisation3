package com.thunder.item;

import com.thunder.laboratory.samples.virus.custom.CustomVirus;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class VirusSprayer extends ItemBionisation {

    public static final String DNA_KEY = Utilities.getModIdString("sp_dna");

    public VirusSprayer(){
        super();
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItemMainhand();
        if(!playerIn.world.isRemote){
            NBTTagCompound tag = Utilities.getNbt(stack);
            if(tag.hasKey(DNA_KEY)){
                String dna = tag.getString(DNA_KEY);
                IBioPlayer cap = playerIn.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                CustomVirus virus = new CustomVirus(Utilities.random.nextInt(Integer.MAX_VALUE - 1000) + 1000, -1, Utilities.getPowerFromImmunity(cap.getImmunityLevel()), true, "Virus " + dna, dna);
                CustomVirus.spreadCustomEffect(virus, playerIn, EntityLivingBase.class, 15);
                tag.removeTag(DNA_KEY);
            }
        }
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        NBTTagCompound tag = Utilities.getNbt(stack);
        if(tag.hasKey(DNA_KEY)){
            String [] dna_array = tag.getString(DNA_KEY).split(":");
            tooltip.add(I18n.format("tooltip.dna"));
            for (int i = 0; i < dna_array.length; i++) {
                //to do: add text visualization. not numeric
                tooltip.add((dna_array[i].isEmpty() || dna_array[i].equals("0") ? TextFormatting.RED + "<" + I18n.format("tooltip.empty") + ">" : TextFormatting.GREEN + "<" + I18n.format("tooltip.gene." + dna_array[i]) + ">"));
            }
        }
    }
}
