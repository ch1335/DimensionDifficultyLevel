package com.chen1335.dimensionDifficultyLevel.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractModPacket {

    public abstract void write(FriendlyByteBuf buffer);

    public abstract void handel(Supplier<NetworkEvent.Context> supplier);

}
