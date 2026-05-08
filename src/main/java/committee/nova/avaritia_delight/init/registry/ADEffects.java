package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.effect.OverWeightEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ADEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AvaritiaDelight.MOD_ID);

    public static final Holder<MobEffect> OVERWEIGHT = EFFECTS.register("overweight", OverWeightEffect::new);

    public static void register(IEventBus bus){
        EFFECTS.register(bus);
    }
}
