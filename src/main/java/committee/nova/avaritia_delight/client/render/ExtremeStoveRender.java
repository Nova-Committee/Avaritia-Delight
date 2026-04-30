package committee.nova.avaritia_delight.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import committee.nova.avaritia_delight.common.block.ExtremeStoveBlock;
import committee.nova.avaritia_delight.common.block.entity.ExtremeStoveBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ExtremeStoveRender<T extends ExtremeStoveBlockEntity> implements BlockEntityRenderer<T> {
    private static final float SIZE = 0.375F;
    private final ItemRenderer itemRenderer;

    public ExtremeStoveRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    public void render(T stove, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction direction = ((Direction)stove.getBlockState().getValue(ExtremeStoveBlock.FACING)).getOpposite();
        ItemStackHandler items = stove.getItems();
        int posLong = (int)stove.getBlockPos().asLong();

        for(int i = 0; i < items.getSlots(); ++i) {
            ItemStack stoveStack = items.getStackInSlot(i);
            if (!stoveStack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate((double)0.5F, 1.02, (double)0.5F);
                float f = -direction.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                Vec2 itemOffset = stove.getStoveItemOffset(i);
                poseStack.translate((double)itemOffset.x, (double)itemOffset.y, (double)0.0F);
                poseStack.scale(0.375F, 0.375F, 0.375F);
                this.itemRenderer.renderStatic(stoveStack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(stove.getLevel(), stove.getBlockPos().above()), packedOverlay, poseStack, buffer, stove.getLevel(), posLong + i);
                poseStack.popPose();
            }
        }

    }
}
