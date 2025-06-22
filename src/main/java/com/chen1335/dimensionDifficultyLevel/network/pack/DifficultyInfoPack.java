package com.chen1335.dimensionDifficultyLevel.network.pack;

import com.chen1335.dimensionDifficultyLevel.API.Capabilities;
import com.chen1335.dimensionDifficultyLevel.network.AbstractModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DifficultyInfoPack extends AbstractModPacket {

    private final int currentDifficulty;
    private final float eliteEnemyChance;
    private final float specialItemDropChance;

    public DifficultyInfoPack(int currentDifficulty, float eliteEnemyChance, float specialItemDropChance) {
        this.currentDifficulty = currentDifficulty;
        this.eliteEnemyChance = eliteEnemyChance;
        this.specialItemDropChance = specialItemDropChance;
    }

    public DifficultyInfoPack(FriendlyByteBuf buffer) {
        this.currentDifficulty = buffer.readInt();
        this.eliteEnemyChance = buffer.readFloat();
        this.specialItemDropChance = buffer.readFloat();
    }



    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(currentDifficulty);
        buffer.writeFloat(eliteEnemyChance);
        buffer.writeFloat(specialItemDropChance);
    }

    @Override
    public void handel(Supplier<NetworkEvent.Context> supplier) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(Capabilities.PLAYER_STATUE).ifPresent(playerStatue -> {
                playerStatue.setCurrentDifficulty(currentDifficulty);
            });
        }
    }
}
