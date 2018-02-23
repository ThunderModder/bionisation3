package com.thunder.bionisation;

import com.thunder.bionisation.versions.VersionChecker;
import com.thunder.block.BlockRegistry;
import com.thunder.event.ClientEventHandler;
import com.thunder.event.ParticleEvent;
import com.thunder.gui.BloodBarOverlay;
import com.thunder.item.ItemRegistry;
import net.minecraftforge.common.MinecraftForge;


public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new ParticleEvent());
        super.preInit();
    }

    @Override
    public void init() {
        BlockRegistry.initBlocksClient();
        ItemRegistry.initItemsClient();
        MinecraftForge.EVENT_BUS.register(new BloodBarOverlay());
        ClientEventHandler.register();
        super.init();
    }

    @Override
    public void postInit() {
        if(Config.canShowUpdates) {
            Bionisation.checker = new VersionChecker();
            Thread versionCheckThread = new Thread(Bionisation.checker, "Version Check Thread");
            versionCheckThread.start();
        }
        super.postInit();
    }
}
