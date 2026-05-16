package committee.nova.avaritia_delight.client.screen;

import com.mojang.datafixers.util.Pair;
import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.menu.InfinityCabinetMenu;
import committee.nova.mods.avaritia.common.container.slot.InfinitySlot;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.text.DecimalFormat;
import java.util.List;

public class InfinityCabinetScreen extends AbstractContainerScreen<InfinityCabinetMenu> {

    private static final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "textures/gui/infinity_cabinet.png");

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
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            ItemStack stack = this.hoveredSlot.getItem();
            List<Component> tooltip = this.getTooltipFromContainerItem(stack);

            if (this.hoveredSlot instanceof InfinitySlot) {
                tooltip.add(Component.translatable("container.infinity_chest", stack.getCount(), this.menu.getSlotMaxStack(this.hoveredSlot)));
            }

            graphics.renderTooltip(this.font, tooltip, stack.getTooltipImage(), stack, mouseX, mouseY);
        }
    }

    @Override
    protected void renderSlot(GuiGraphics graphics, Slot slot) {
        int i = slot.x;
        int j = slot.y;
        ItemStack itemStack = slot.getItem();

        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 100);

        if (itemStack.isEmpty() && slot.isActive()) {
            Pair<ResourceLocation, ResourceLocation> pair = slot.getNoItemIcon();
            if (pair != null) {
                TextureAtlasSprite sprite = this.minecraft.getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
                graphics.blit(i, j, 0, 16, 16, sprite);
            }
        }

        if (!itemStack.isEmpty()) {
            int seed = slot.x + slot.y * this.imageWidth;
            graphics.renderItem(itemStack, i, j, seed);

            String text = null;
            int count = itemStack.getCount();

            if (slot instanceof InfinitySlot && count >= 1000) {
                if (count >= 1000000000) {
                    text = new DecimalFormat("#").format(count / 1000000000D) + "G";
                } else if (count >= 1000000) {
                    text = new DecimalFormat("#").format(count / 1000000D) + "M";
                } else if (count >= 10000) {
                    text = new DecimalFormat("#").format(count / 10000D) + "W";
                } else if (count >= 1000) {
                    text = new DecimalFormat("#").format(count / 1000D) + "K";
                }
            }

            graphics.renderItemDecorations(this.font, itemStack, i, j, text);
        }

        graphics.pose().popPose();
    }
}