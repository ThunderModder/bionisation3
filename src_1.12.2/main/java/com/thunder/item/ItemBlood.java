package com.thunder.item;

import com.thunder.util.Utilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlood extends ItemBionisation {

    public static final String BLOOD_KEY = Utilities.getModIdString("blood");

    public ItemBlood(){
        super();
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound tag = Utilities.getNbt(stack);
        if (tag.hasKey(BLOOD_KEY)) {
            tooltip.add(I18n.format("tooltip.blood") + " " + TextFormatting.GREEN + tag.getString(BLOOD_KEY));
        }
    }
}
