package com.thunder.gui;

import com.thunder.bionisation.Information;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BAnalyzerGuiScreen extends GuiScreen{

    protected int xSize = 150;
    protected int ySize = 225;

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Information.MOD_ID + ":textures/gui/gui_bioanalyzer.png");

    private String info;

    public BAnalyzerGuiScreen(){
    }

    public BAnalyzerGuiScreen(String info){
        this.info = info;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void onGuiClosed(){
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int parWidth, int parHeight, float p_73863_3_){
        try {
            this.drawDefaultBackground();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(GUI_TEXTURE);
            int offsetFromScreenLeft = (width - 147 ) / 2;
            drawTexturedModalRect(offsetFromScreenLeft, 2, 0, 0, 147, 214);
            fontRenderer.drawSplitString(I18n.format("tooltip.analyzer.effects"), (offsetFromScreenLeft + 10), 12, 150, 0xffffff);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            String general = "";
            for (String s : info.split("_"))
                if (!s.isEmpty()) {
                    if(s.startsWith("virus/")){
                        String [] name = s.split("/");
                        general += I18n.format("tooltip.effect." + (name[1].replaceAll(" ", "_").toLowerCase())) + " (" + name[2] + ")" + "\n\n";
                    }else if(s.startsWith("cvirus/"))
                            general += s.replace("cvirus/", "").replace("Virus", I18n.format("tooltip.type.virus")) + "\n\n";
                        else
                            general += I18n.format("tooltip.effect." + (s.replaceAll(" ", "_").toLowerCase())) + "\n\n";
                }
            if(!general.isEmpty())
                fontRenderer.drawSplitString(general, (offsetFromScreenLeft + 10) * 2, 25 * 2, 300, 0xffffff);
            else
                fontRenderer.drawSplitString("<" + I18n.format("tooltip.empty") + ">", (offsetFromScreenLeft + 10) * 2, 25 * 2, 150, 0xffffff);
            GlStateManager.popMatrix();
        }catch (Exception ex){ex.printStackTrace();}
        super.drawScreen(parWidth, parHeight, p_73863_3_);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }
}
