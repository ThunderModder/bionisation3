package com.thunder.gui.guidebook;

import com.thunder.bionisation.Information;
import com.thunder.block.BlockRegistry;
import com.thunder.item.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiGuideBook extends GuiScreen {
	
    private final int bookImageHeight = 192;
    private final int bookImageWidth = 192;
    private int currPage = 0;
    private static final int bookTotalPages = 104;
    private static final ResourceLocation bookPageTexture = new ResourceLocation(Information.MOD_ID + ":textures/gui/guidebook/book.png");
    private static String[] stringPageText = new String[bookTotalPages];
    private GuiButton buttonDone;
    private NextPageButton buttonNextPage;
    private NextPageButton buttonPreviousPage;

    private HelpButton buttonMechanics;
    private HelpButton buttonIngredients;
    private HelpButton buttonEffects;
    private HelpButton buttonBacteria;
    private HelpButton buttonViruses;
    private HelpButton buttonGenetics;
    private HelpButton buttonSymbionts;
    private HelpButton buttonMechanisms;
    private HelpButton buttonOther;

    public GuiGuideBook(){
    	for(int i = 0; i < this.bookTotalPages; i++){
    		stringPageText[i] = formatString(I18n.format("book.page." + i));
    	}
    }
    
    private static String formatString(String s){
        return s.replaceAll("NL", "\n\n");
    }

    @Override
    public void initGui() {
        buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        buttonDone = new GuiButton(0, width / 2 - 40, 4 + bookImageHeight, 80, 20, I18n.format("gui.done", new Object[0]));
        buttonList.add(buttonDone);
        int offsetFromScreenLeft = (width - bookImageWidth) / 2;
        buttonList.add(buttonNextPage = new NextPageButton(1, offsetFromScreenLeft + 120, 156, true));
        buttonList.add(buttonPreviousPage = new NextPageButton(2, offsetFromScreenLeft + 38, 156, false));
        int start = 14;
        int interval = 11;
        int lx = 159;
        buttonMechanics = new HelpButton(3, offsetFromScreenLeft + lx, start, new ItemStack(Items.BONE));
        buttonMechanics.addTooltip(I18n.format("book.tooltip.mechanics"));
        buttonList.add(buttonMechanics);
        buttonIngredients = new HelpButton(4, offsetFromScreenLeft + lx, start + interval, new ItemStack(Item.getItemFromBlock(BlockRegistry.ENDER_FLOWER)));
        buttonIngredients.addTooltip(I18n.format("book.tooltip.ingredients"));
        buttonList.add(buttonIngredients);
        buttonEffects = new HelpButton(5, offsetFromScreenLeft + lx, start + interval * 2, new ItemStack(ItemRegistry.POTION_CURE));
        buttonEffects.addTooltip(I18n.format("book.tooltip.effects"));
        buttonList.add(buttonEffects);
        buttonBacteria = new HelpButton(6, offsetFromScreenLeft + lx, start + interval * 3, new ItemStack(ItemRegistry.VIAL));
        buttonBacteria.addTooltip(I18n.format("book.tooltip.bacteria"));
        buttonList.add(buttonBacteria);
        buttonViruses = new HelpButton(7, offsetFromScreenLeft + lx, start + interval * 4, new ItemStack(ItemRegistry.CREATIVE_VIAL));
        buttonViruses.addTooltip(I18n.format("book.tooltip.viruses"));
        buttonList.add(buttonViruses);
        buttonGenetics = new HelpButton(8, offsetFromScreenLeft + lx, start + interval * 5, new ItemStack(ItemRegistry.GENE_VIAL));
        buttonGenetics.addTooltip(I18n.format("book.tooltip.genetics"));
        buttonList.add(buttonGenetics);
        buttonSymbionts = new HelpButton(9, offsetFromScreenLeft + lx, start + interval * 6, new ItemStack(ItemRegistry.SYMBIONT_VIAL));
        buttonSymbionts.addTooltip(I18n.format("book.tooltip.symbionts"));
        buttonList.add(buttonSymbionts);
        buttonMechanisms = new HelpButton(10, offsetFromScreenLeft + lx, start + interval * 7, new ItemStack(ItemRegistry.PROCESSOR));
        buttonMechanisms.addTooltip(I18n.format("book.tooltip.mechanisms"));
        buttonList.add(buttonMechanisms);
        buttonOther = new HelpButton(11, offsetFromScreenLeft + lx, start + interval * 8, new ItemStack(Items.ENDER_PEARL));
        buttonOther.addTooltip(I18n.format("book.tooltip.other"));
        buttonList.add(buttonOther);
        buttonDone.visible = true;
        buttonMechanics.visible = true;
        buttonIngredients.visible = true;
        buttonEffects.visible = true;
        buttonBacteria.visible = true;
        buttonViruses.visible = true;
        buttonGenetics.visible = true;
        buttonSymbionts.visible = true;
        buttonMechanisms.visible = true;
        buttonOther.visible = true;
    }

    @Override
    public void updateScreen() {
    	super.updateScreen();
        buttonNextPage.visible = (currPage < bookTotalPages - 1);
        buttonPreviousPage.visible = currPage > 0;
    }

    @Override
    public void drawScreen(int parWidth, int parHeight, float p_73863_3_){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(bookPageTexture);
        int offsetFromScreenLeft = (width - bookImageWidth ) / 2;
        drawTexturedModalRect(offsetFromScreenLeft, 2, 0, 0, bookImageWidth, bookImageHeight);
        int widthOfString;
        String stringPageIndicator = I18n.format("book.pageIndicator", new Object[] {Integer.valueOf(currPage + 1), bookTotalPages});
        widthOfString = fontRenderer.getStringWidth(stringPageIndicator);
        fontRenderer.drawString(stringPageIndicator, offsetFromScreenLeft - widthOfString + bookImageWidth - 44, 18, 0);
        fontRenderer.drawSplitString(stringPageText[currPage], offsetFromScreenLeft + 36, 34, 116, 0);
        super.drawScreen(parWidth, parHeight, p_73863_3_);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton parButton) {
     if (parButton == buttonDone){
         mc.displayGuiScreen((GuiScreen)null);
     }
     else if (parButton == buttonNextPage){
         if (currPage < bookTotalPages - 1)
             ++currPage;
     }
     else if (parButton == buttonPreviousPage){
         if (currPage > 0)
             --currPage;
     }else{
         switch(parButton.id){
             case 3:
                 currPage = 5;
                 break;
             case 4:
                 currPage = 10;
                 break;
             case 5:
                 currPage = 15;
                 break;
             case 6:
                 currPage = 26;
                 break;
             case 7:
                 currPage = 40;
                 break;
             case 8:
                 currPage = 60;
                 break;
             case 9:
                 currPage = 79;
                 break;
             case 10:
                 currPage = 97;
                 break;
             case 11:
                 currPage = 102;
                 break;
             default:
                 break;
         }
     }
   }

    @Override
    public void onGuiClosed(){
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton{

        private final boolean isNextButton;

        public NextPageButton(int parButtonId, int parPosX, int parPosY, boolean parIsNextButton){
            super(parButtonId, parPosX, parPosY, 23, 13, "");
            isNextButton = parIsNextButton;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
        	if (visible){
        		boolean isButtonPressed = (mouseX >= x
        				&& mouseY >= y
        				&& mouseX < x + width
        				&& mouseY < y + height);
        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        		mc.getTextureManager().bindTexture(bookPageTexture);
        		int textureX = 0;
        		int textureY = 192;
        		if (isButtonPressed)
        			textureX += 23;
        		if (!isNextButton)
        			textureY += 13;
        		drawTexturedModalRect(x, y,  textureX, textureY, 23, 13);
        	}
        }
    }

    @SideOnly(Side.CLIENT)
    class HelpButton extends GuiButton {

        private List<String> tooltips;
        private ItemStack icon;

        public HelpButton(int parButtonId, int parPosX, int parPosY, ItemStack iconStack){
            super(parButtonId, parPosX, parPosY, 23, 13, "");
            tooltips = new ArrayList<>();
            icon = iconStack;
        }

        public void addTooltip(String tooltip){
            tooltips.add(tooltip);
        }

        @Override
        public void drawButton(Minecraft mc, int parX, int parY, float partialTicks){
            if (visible){
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                boolean isButtonPressed = (parX >= x
                        && parY >= y
                        && parX < x + width
                        && parY < y + height);
                mc.getTextureManager().bindTexture(bookPageTexture);
                int textureX = 48;
                int textureY = 194;
                drawTexturedModalRect(x, y,  textureX, textureY, 20, 10);
                if(isButtonPressed){
                    drawHoveringText(tooltips, parX, parY);
                }
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.5, 0.5, 0.5);
                itemRender.renderItemIntoGUI(icon, x * 2 + 8, y * 2 + 2);
                GlStateManager.popMatrix();
            }
        }
    }
}
