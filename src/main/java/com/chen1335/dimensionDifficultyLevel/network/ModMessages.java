package com.chen1335.dimensionDifficultyLevel.network;


import com.chen1335.dimensionDifficultyLevel.DimensionDifficultyLevel;
import com.chen1335.dimensionDifficultyLevel.network.pack.DifficultyInfoPack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int PacketId = 0;

    private static int id() {
        return PacketId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(DimensionDifficultyLevel.MODID, "messages"), () -> "1.0", (s) -> true, (s) -> true);
        INSTANCE = net;

        net.messageBuilder(DifficultyInfoPack.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DifficultyInfoPack::new)
                .encoder(DifficultyInfoPack::write)
                .consumerMainThread(DifficultyInfoPack::handel)
                .add();
    }

    public static <M> void sendToServer(M message) {
        INSTANCE.sendToServer(message);
    }

    public static <M> void sendToClient(M message, ServerPlayer serverPlayer) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }
}
