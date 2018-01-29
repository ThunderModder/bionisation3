package com.thunder.gui;

import com.thunder.container.ContainerDisinfector;
import com.thunder.container.ContainerHerbalStation;
import com.thunder.container.ContainerVaccineCreator;
import com.thunder.gui.machine.GuiDisinfector;
import com.thunder.gui.machine.GuiHerbalStation;
import com.thunder.gui.machine.GuiVaccineCreator;
import com.thunder.tileentity.TileDisinfector;
import com.thunder.tileentity.TileHerbalStation;
import com.thunder.tileentity.TileVaccineCreator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class BGuiHandler implements IGuiHandler {

    public static final int HERBAL_STATION_GUI = 0;
    public static final int DISINFECTOR_GUI = 1;
    public static final int VACCINE_CREATOR_GUI = 2;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        switch(ID){
            case HERBAL_STATION_GUI:
                return new ContainerHerbalStation(player.inventory, (TileHerbalStation)tile);
            case DISINFECTOR_GUI:
                return new ContainerDisinfector(player.inventory, (TileDisinfector)tile);
            case VACCINE_CREATOR_GUI:
                return new ContainerVaccineCreator(player.inventory, (TileVaccineCreator)tile);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        switch(ID){
            case HERBAL_STATION_GUI:
                return new GuiHerbalStation(player.inventory, (TileHerbalStation)tile);
            case DISINFECTOR_GUI:
                return new GuiDisinfector(player.inventory, (TileDisinfector)tile);
            case VACCINE_CREATOR_GUI:
                return new GuiVaccineCreator(player.inventory, (TileVaccineCreator)tile);
            default:
                return null;
        }
    }
}
