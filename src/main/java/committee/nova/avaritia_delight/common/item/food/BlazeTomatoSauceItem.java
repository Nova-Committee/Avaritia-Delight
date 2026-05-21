package committee.nova.avaritia_delight.common.item.food;

import committee.nova.avaritia_delight.init.registry.ADItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class BlazeTomatoSauceItem extends Item {
    public BlazeTomatoSauceItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer player) {
            livingEntity.setRemainingFireTicks(20);

            ItemStack container = new ItemStack(ADItems.neutronium_bowl.get());

            if (!player.addItem(container)) {
                player.spawnAtLocation(container, 0.1F);
            }
        }

        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }
}
