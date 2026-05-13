package committee.nova.avaritia_delight.init.registry;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class ADRarities {

    public static final EnumProxy<Rarity> FUNCTION = new EnumProxy<>(
            Rarity.class, 7777, "avaritia_delight:function", ChatFormatting.GREEN
    );

    public static final EnumProxy<Rarity> COMMEMORATION = new EnumProxy<>(
            Rarity.class, 7777, "avaritia_delight:commemoration", ChatFormatting.YELLOW
    );

}
