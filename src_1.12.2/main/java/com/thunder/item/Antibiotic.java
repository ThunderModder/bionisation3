package com.thunder.item;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.thunder.bionisation.CommonProxy;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Antibiotic extends ItemBionisation {

    public static List<Integer> weakTargetIds = new ArrayList<>();
    public static List<Integer> strongTargetIds = new ArrayList<>();

    public static final String ANT_TYPE = Utilities.getModIdString("ant_type");

    static{
        weakTargetIds.add(Constants.ID_SEABACTERIA);
        weakTargetIds.add(Constants.ID_GLOWINGBACTERIA);
        weakTargetIds.add(Constants.ID_CACTUSBACTERIA);
        weakTargetIds.add(Constants.ID_WATERBACTERIA);
        weakTargetIds.add(Constants.ID_SMALLGREENBACTERIA);
        strongTargetIds.add(Constants.ID_BLACKBACTERIA);
        strongTargetIds.add(Constants.ID_SWAMPBACTERIA);
        strongTargetIds.add(Constants.ID_ENDERBACTERIA);
        strongTargetIds.add(Constants.ID_BONEBACTERIA);
        strongTargetIds.add(Constants.ID_TERABACTERIA);
        strongTargetIds.add(Constants.ID_MYCELIUMBACTERIA);
    }

    public Antibiotic(String unlocalizedName){
        super(unlocalizedName, 64);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItemMainhand();
        if(!worldIn.isRemote) {
            if(stack.hasTagCompound()) {
                IBioPlayer cap = playerIn.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                int index = playerIn.inventory.currentItem;
                NBTTagCompound nbt = Utilities.getNbt(stack);
                if(nbt.getString(ANT_TYPE).equals("weak"))
                    if (!cap.isEffectActive(Constants.ID_CURE_WEAKANTIBIOTIC)) {
                        cap.addEffect(Utilities.getNewEffectCopy(Utilities.findEffectById(Constants.ID_CURE_WEAKANTIBIOTIC)), playerIn);
                        Utilities.decreaseOrRemove(playerIn, index);
                    }
                if(nbt.getString(ANT_TYPE).equals("strong"))
                    if (!cap.isEffectActive(Constants.ID_CURE_STRONGANTIBIOTIC)) {
                        cap.addEffect(Utilities.getNewEffectCopy(Utilities.findEffectById(Constants.ID_CURE_STRONGANTIBIOTIC)), playerIn);
                        Utilities.decreaseOrRemove(playerIn, index);
                    }
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
        if(stack.hasTagCompound()){
            NBTTagCompound nbt = Utilities.getNbt(stack);
                tooltip.add(ChatFormatting.GREEN + (nbt.getString(ANT_TYPE).equals("weak") ? I18n.format("tooltip.weakantibiotic") : I18n.format("tooltip.strongantibiotic")));
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == CommonProxy.tabBionisation) {
            items.add(getAntibiotic(0));
            items.add(getAntibiotic(1));
        }
    }

    public static ItemStack getAntibiotic(int type){
        if(type == 0){
            ItemStack stack = new ItemStack(ItemRegistry.ANTIBIOTIC);
            NBTTagCompound nbt = Utilities.getNbt(stack);
            nbt.setString(ANT_TYPE, "weak");
            return stack;
        }else{
            ItemStack stack = new ItemStack(ItemRegistry.ANTIBIOTIC);
            NBTTagCompound nbt = Utilities.getNbt(stack);
            nbt.setString(ANT_TYPE, "strong");
            return stack;
        }
    }
}
