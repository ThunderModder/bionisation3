package com.thunder.item;

import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.IVirus;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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

import javax.annotation.Nullable;
import java.util.List;

public class VaccineInjector extends ItemBionisation {

    public VaccineInjector(){
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
                String stab = nbt.getString(Utilities.getModIdString("vstability"));
                String dna = nbt.getString(Utilities.getModIdString("vdna"));
                if(stab.equals("Stable"))
                    applyVaccine(cap, dna, false);
                else {
                    applyVaccine(cap, dna, Utilities.random.nextInt(2) == 1);
                }
                nbt.removeTag(Utilities.getModIdString("vdna"));
                nbt.removeTag(Utilities.getModIdString("vstability"));
            }
        }
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(stack.hasTagCompound() && Utilities.getNbt(stack).hasKey(Utilities.getModIdString("vdna"))){
            NBTTagCompound nbt = Utilities.getNbt(stack);
            String stab = nbt.getString(Utilities.getModIdString("vstability"));
            tooltip.add(I18n.format("tooltip.injector.vaccine") + " " + (stab.equals("Stable") ? TextFormatting.GREEN : TextFormatting.RED) + I18n.format("tooltip.injector." + stab.toLowerCase()));
            tooltip.add(I18n.format("tooltip.injector.dna") + " " + TextFormatting.GREEN + nbt.getString(Utilities.getModIdString("vdna")));
        }
    }

    private static void applyVaccine(IBioPlayer cap, String dna, boolean bad){
        //vaccine effect
        for(IBioSample smp : cap.getEffectList()){
            if(smp instanceof IVirus){
                IVirus virus = (IVirus)smp;
                if(virus.getDNA().equals(dna)){
                    if(bad){
                        virus.setPower(virus.getPower() + 2);
                    }else {
                        virus.setInfinite(false);
                        virus.setDuration(1200);
                    }
                }
            }
        }
    }
}
