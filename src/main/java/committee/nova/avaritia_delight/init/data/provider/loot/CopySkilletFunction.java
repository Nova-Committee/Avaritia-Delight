package committee.nova.avaritia_delight.init.data.provider.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.block.entity.ExtremeSkilletBlockEntity;
import committee.nova.avaritia_delight.init.registry.ADLootFunctions;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CopySkilletFunction extends LootItemConditionalFunction {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID,"copy_skillet");
    public static final MapCodec<CopySkilletFunction> CODEC = RecordCodecBuilder.mapCodec(
            p_298131_ -> commonFields(p_298131_).apply(p_298131_, CopySkilletFunction::new)
    );

    private CopySkilletFunction(List<LootItemCondition> conditions) {
        super(conditions);
    }

    public static LootItemConditionalFunction.Builder<?> builder() {
        return simpleBuilder(CopySkilletFunction::new);
    }

    protected ItemStack run(ItemStack stack, LootContext context) {
        Object var4 = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (var4 instanceof ExtremeSkilletBlockEntity skillet) {
            stack = skillet.getSkilletAsItem();
        }

        return stack;
    }

    public LootItemFunctionType<CopySkilletFunction> getType() {
        return ADLootFunctions.COPY_SKILLET.get();
    }
}

