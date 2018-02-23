package com.thunder.item.crop;

import com.thunder.item.SeedBionisation;
import com.thunder.laboratory.samples.EffectImmunity;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Petro on 31.12.2017.
 */
public class GarlicBulb extends SeedBionisation {

    public GarlicBulb(String unlocalizedName, Block block) {
        super(unlocalizedName, block);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.garlicbulb"));
    }

    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving){
        stack.setCount(stack.getCount() - 1);
        if (entityLiving instanceof EntityPlayer){
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, worldIn, entityplayer);
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }
        return stack;
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player){
        if (!worldIn.isRemote){
            IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            cap.addEffect(new EffectImmunity(Constants.CURE_EFFECT_STANDART_DURATION, 0), player);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.EAT;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand){
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        playerIn.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }
}
