package committee.nova.avaritia_delight.common.network;

import committee.nova.avaritia_delight.AvaritiaDelight;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record FlipExtremeSkilletPayload() implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "flip_extreme_skillet");
    public static final FlipExtremeSkilletPayload INSTANCE = new FlipExtremeSkilletPayload();
    public static final Type<FlipExtremeSkilletPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, FlipExtremeSkilletPayload> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
