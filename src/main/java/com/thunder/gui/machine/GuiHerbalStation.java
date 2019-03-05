package com.thunder.gui.machine;

import com.thunder.bionisation.Information;
import com.thunder.container.ContainerHerbalStation;
import com.thunder.tileentity.TileHerbalStation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHerbalStation extends GuiContainer {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Information.MOD_ID + ":textures/gui/machine/gui_herbalstation.png");
    private final InventoryPlayer playerInventory;
    private final IInventory machine;

    public GuiHerbalStation(InventoryPlayer playerInv, IInventory inventory){
        super(new ContainerHerbalStation(playerInv, inventory));
        this.playerInventory = playerInv;
        this.machine = inventory;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = I18n.format("inventory.herbalstation.name");
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        if (TileHerbalStation.isProcessing(this.machine)){
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 153, j + 37 + 19 - k, 176, 12 - k, 14, k + 1);
            int i1 = (int)(62 * (double)(machine.getField(2) / (double)machine.getField(3)));
            this.drawTexturedModalRect(i + 37, j + 16, 176, 14, 7, 62 - i1);
        }else
            this.drawTexturedModalRect(i + 37, j + 16, 176, 14, 7, 62);
    }

    private int getBurnLeftScaled(int pixels){
        int i = this.machine.getField(1);
        if (i == 0)
            i = 200;
        return this.machine.getField(0) * pixels / i;
    }
}
