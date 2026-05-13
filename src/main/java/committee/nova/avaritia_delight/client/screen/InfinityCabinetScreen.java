package committee.nova.avaritia_delight.client.screen;

import committee.nova.avaritia_delight.common.container.slot.InfinityCabinetSlot;
import committee.nova.avaritia_delight.common.menu.InfinityCabinetMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.text.DecimalFormat;

public class InfinityCabinetScreen extends AbstractContainerScreen<InfinityCabinetMenu> {

    private static final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath("avaritia_delight", "textures/gui/infinity_cabinet.png");

    public InfinityCabinetScreen(InfinityCabinetMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 500;
        this.imageHeight = 276;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(BACKGROUND_LOCATION, this.getGuiLeft(), this.getGuiTop(), 0.0F, 0.0F, this.imageWidth, this.imageHeight, 500, 500);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 170, this.imageHeight - 94, 4210752, false);
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        super.renderSlot(guiGraphics, slot);
        
        if (!slot.hasItem()) {
            return;
        }

        ItemStack itemStack = slot.getItem();
        int count = itemStack.getCount();
        
        // 只显示在InfinityCabinetSlot上且数量大于1000的物品
        if (!(slot instanceof InfinityCabinetSlot) || count < 1000) {
            return;
        }

        String text = null;
        if (count >= 1000000000) {
            text = new DecimalFormat("#").format(count / 1000000000) + "G";
        } else if (count >= 1000000) {
            text = new DecimalFormat("#").format(count / 1000000) + "M";
        } else if (count >= 10000) {
            text = new DecimalFormat("#").format(count / 10000) + "W";
        } else if (count >= 1000) {
            text = new DecimalFormat("#").format(count / 1000) + "K";
        }

        if (text != null) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, 0.0F, 200.0F);
            guiGraphics.drawString(this.font, text, slot.x + 16 - this.font.width(text), slot.y + 9, 16777215, true);
            guiGraphics.pose().popPose();
        }
    }
}
