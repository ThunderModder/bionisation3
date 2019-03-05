package com.thunder.gui;

import com.thunder.container.*;
import com.thunder.gui.machine.*;
import com.thunder.tileentity.*;
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
    public static final int DNA_FORMER_GUI = 3;
    public static final int VIRUS_REPLICATOR_GUI = 4;

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
            case DNA_FORMER_GUI:
                return new ContainerDNAFormer(player.inventory, (TileDNAFormer)tile);
            case VIRUS_REPLICATOR_GUI:
                return new ContainerVirusReplicator(player.inventory, (TileVirusReplicator)tile);
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
            case DNA_FORMER_GUI:
                return new GuiDNAFormer(player.inventory, (TileDNAFormer)tile);
            case VIRUS_REPLICATOR_GUI:
                return new GuiVirusReplicator(player.inventory, (TileVirusReplicator)tile);
            default:
                return null;
        }
    }
}
