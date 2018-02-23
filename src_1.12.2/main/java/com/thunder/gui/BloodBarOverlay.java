package com.thunder.gui;

import com.thunder.bionisation.Information;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class BloodBarOverlay extends Gui {

    private static final ResourceLocation BLOOD_BAR_TEXTURE = new ResourceLocation(Information.MOD_ID, "textures/gui/blood_bar.png");
    private static Minecraft mc = Minecraft.getMinecraft();

    int t_height = 11;
    int t_width = 64;

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        IBioPlayer cap = mc.player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
        if(cap != null) {
            ScaledResolution scaledResolution = event.getResolution();
            int width = scaledResolution.getScaledWidth();
            int height = scaledResolution.getScaledHeight();
            int x = width - t_width - 30;
            int y = height - 20;
            mc.renderEngine.bindTexture(BLOOD_BAR_TEXTURE);
            drawTexturedModalRect(x, y, 0, 0, t_width, t_height);
            drawTexturedModalRect(x, y, 0, 12, (int)(((float)cap.getBloodLevel() / 100) * t_width), t_height - 1);
        }
    }
}
