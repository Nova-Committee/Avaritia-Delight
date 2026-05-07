package committee.nova.avaritia_delight.common;

import committee.nova.avaritia_delight.client.render.ExtremeSkilletItemRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class ADEnumParameters {

    public static final EnumProxy<HumanoidModel.ArmPose> PROXY_SKILLET_FLIP = new EnumProxy<>(
            HumanoidModel.ArmPose.class, false, new ExtremeSkilletItemRenderer.ArmPoseTransformer()
    );
}
