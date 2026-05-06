package committee.nova.avaritia_delight.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.menu.CropExtractorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CropExtractorScreen extends AbstractContainerScreen<CropExtractorMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "textures/gui/crop_extractor.png");

    private static final int PROGRESS_ARROW_X = 72;
    private static final int PROGRESS_ARROW_Y = 38;
    private static final int PROGRESS_WIDTH = 26;
    private static final int PROGRESS_HEIGHT = 17;

    public CropExtractorScreen(CropExtractorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.titleLabelY = 6;
        this.titleLabelX = 10;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.minecraft == null) return;

        guiGraphics.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int progress = this.menu.getExtractionProgressScaled();
        if (progress > 0) {
            guiGraphics.blit(BACKGROUND_TEXTURE,
                    this.leftPos + PROGRESS_ARROW_X,
                    this.topPos + PROGRESS_ARROW_Y,
                    177, 47,
                    progress, PROGRESS_HEIGHT);
        }
    }
}
