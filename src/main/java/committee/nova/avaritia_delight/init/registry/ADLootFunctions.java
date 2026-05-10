package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.init.data.provider.loot.CopySkilletFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ADLootFunctions {

    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, "avaritia_delight");

    public static final Supplier<LootItemFunctionType<CopySkilletFunction>> COPY_SKILLET = LOOT_FUNCTIONS.register("copy_skillet", () -> new LootItemFunctionType(CopySkilletFunction.CODEC));

    public static void register(IEventBus bus) {
        LOOT_FUNCTIONS.register(bus);
    }
}
