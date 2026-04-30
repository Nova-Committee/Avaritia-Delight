package committee.nova.avaritia_delight.init.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;

@Mixin(AbstractStoveBlockEntity.class)
public interface StoveAccessor {
    @Accessor("cookingProgress")
    int[] getCookingProgress();

    @Accessor("cookingTime")
    int[] getCookingTime();
}
