package committee.nova.avaritia_delight.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.menu.ExtremeCookingPotMenu;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class ExtremeCookingPotScreen extends AbstractContainerScreen<ExtremeCookingPotMenu>
{
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "textures/gui/extreme_cooking_pot.png");

    private static final Rectangle HEAT_ICON = new Rectangle(177, 66, 17, 15);
    private static final Rectangle PROGRESS_ARROW = new Rectangle(172, 90, 0, 17);
    private static final Rectangle HEAT_HOVER_AREA = new Rectangle(174, 63, 21, 21);

    public ExtremeCookingPotScreen(ExtremeCookingPotMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageWidth = 234;
        this.imageHeight = 277;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = ((this.imageWidth - this.font.width(this.title)) / 2)-60;
        this.titleLabelY = 6;
    }

    @Override
    public void render(GuiGraphics gui, final int mouseX, final int mouseY, float partialTicks) {
        this.renderBackground(gui, mouseX, mouseY, partialTicks);
        super.render(gui, mouseX, mouseY, partialTicks);

        if (this.hoveredSlot != null && this.hoveredSlot.index == 81 && this.hoveredSlot.hasItem()) {
            this.renderMealDisplayTooltip(gui, mouseX, mouseY);
        } else {
            this.renderTooltip(gui, mouseX, mouseY);
        }

        this.renderHeatIndicatorTooltip(gui, mouseX, mouseY);
    }
    private void renderHeatIndicatorTooltip(GuiGraphics gui, int mouseX, int mouseY) {
        if (this.isHovering(HEAT_HOVER_AREA.x, HEAT_HOVER_AREA.y, HEAT_HOVER_AREA.width, HEAT_HOVER_AREA.height, mouseX, mouseY)) {
            String key = "container.extreme_cooking_pot." + (this.menu.isHeated() ? "heated" : "not_heated");
            gui.renderTooltip(this.font, TextUtils.getTranslation(key), mouseX, mouseY);
        }
    }


    protected void renderMealDisplayTooltip(GuiGraphics gui, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty()) {
            List<Component> tooltip = new ArrayList<>();
            ItemStack mealStack = this.hoveredSlot.getItem();

            tooltip.add(((MutableComponent) mealStack.getItem().getDescription()).withStyle(mealStack.getRarity().getStyleModifier()));

            ItemStack containerStack = this.menu.blockEntity.getContainer();
            if (!containerStack.isEmpty()) {
                String containerName = containerStack.getItem().getDescription().getString();
                tooltip.add(TextUtils.container("cooking_pot.served_on", containerName).withStyle(ChatFormatting.GRAY));
            }

            gui.renderComponentTooltip(font, tooltip, mouseX, mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        gui.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);

        gui.drawString(this.font, this.playerInventoryTitle, 37, 184, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.minecraft == null) return;

        gui.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 512, 512);

        if (this.menu.isHeated()) {
            gui.blit(BACKGROUND_TEXTURE, this.leftPos + HEAT_ICON.x, this.topPos + HEAT_ICON.y, 234, 0, 17, 15, 512, 512);
        }

        int l = this.menu.getCookProgressionScaled();
        gui.blit(BACKGROUND_TEXTURE, this.leftPos + PROGRESS_ARROW.x, this.topPos + PROGRESS_ARROW.y, 234, 15, l + 1, 17, 512, 512);
    }
}