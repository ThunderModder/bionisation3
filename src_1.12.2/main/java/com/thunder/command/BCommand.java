package com.thunder.command;

import com.thunder.laboratory.IBioSample;
import com.thunder.mob.BioMobProvider;
import com.thunder.mob.IBioMob;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;


public class BCommand extends CommandBase {

    public BCommand(){}

    @Override
    public String getName() {
        return "cbio";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "'/cbio <command> <nickname> [args]'. Type '/cbio info' to get list of all available commands.";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1) {
            if(args[0].equals("info")) {
                EntityPlayer player = getCommandSenderAsPlayer(sender);
                if (player != null) {
                    player.sendMessage(new TextComponentString("/cbio set <nickname> immunity <amount> - set immunity level"));
                    player.sendMessage(new TextComponentString("/cbio set <nickname> blood <amount> - set blood level"));
                    player.sendMessage(new TextComponentString("/cbio get <nickname> level immunity - get immunity level"));
                    player.sendMessage(new TextComponentString("/cbio get <nickname> level blood - get blood level"));
                    player.sendMessage(new TextComponentString("/cbio add <nickname> <effect_id> <power> - add effect with specified id and power"));
                    player.sendMessage(new TextComponentString("/cbio remove <nickname> effect <effect_id> - remove effect with specified id"));
                    player.sendMessage(new TextComponentString("/cbio clear <nickname> effects - clear all bionisation effects"));
                    player.sendMessage(new TextComponentString("/cbio debug - clear all bionisation effects from all entities in world"));
                }
            }else if(args[0].equals("debug")){
                World world = server.getEntityWorld();
                for(Entity e : world.getLoadedEntityList()){
                    if(e instanceof EntityLivingBase){
                        if(e instanceof EntityPlayer){
                            EntityPlayer player = (EntityPlayer) e;
                            IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                            if(cap != null)
                                cap.clearEffects(player);
                        }else{
                            IBioMob cap = e.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                            if(cap != null)
                                cap.clearEffects((EntityLivingBase) e);
                        }
                    }
                }
            }
        }else if(args.length > 2){
            try{
                String nickname = args[1];
                String command = args[0];
                String type = args[2];
                EntityPlayer player = (EntityPlayer)getEntity(server, sender, nickname, EntityPlayer.class);
                IBioPlayer cap = player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                switch (command) {
                    case "set": {
                        String value = args[3];
                        if (type.equals("immunity")) cap.setImmunityLevel(Integer.parseInt(value));
                        else if (type.equals("blood")) {
                            cap.setBloodLevel(Integer.parseInt(value));
                            cap.syncBloodCap(player);
                        }
                        cap.syncAllCap(player);
                        break;
                    }
                    case "get": {
                        String value = args[3];
                        if (type.equals("level") && value.equals("immunity"))
                            player.sendMessage(new TextComponentString("Immunity(" + player.getName() + "): " + cap.getImmunityLevel()));
                        else if (type.equals("level") && value.equals("blood"))
                            player.sendMessage(new TextComponentString("Blood(" + player.getName() + "): " + cap.getBloodLevel()));
                        break;
                    }
                    case "add": {
                        String value = args[3];
                        IBioSample smp = Utilities.findEffectById(Integer.parseInt(type));
                        if (smp != null) {
                            IBioSample sample = Utilities.getNewEffectCopy(smp);
                            sample.setPower(Integer.parseInt(value));
                            cap.addEffect(sample, player);
                        }
                        cap.syncAllCap(player);
                        break;
                    }
                    case "remove": {
                        String value = args[3];
                        if (type.equals("effect"))
                            cap.removeEffect(Integer.parseInt(value), player);
                        cap.syncAllCap(player);
                        break;
                    }
                    case "clear": {
                        if (type.equals("effects")) {
                            cap.clearEffects(player);
                            cap.syncAllCap(player);
                        }
                        break;
                    }
                    default:
                        break;
                }
            }catch(Exception e){
                throw new WrongUsageException(this.getUsage(sender), new Object[0]);
            }
        }else
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
}
