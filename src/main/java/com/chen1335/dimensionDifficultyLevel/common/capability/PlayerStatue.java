package com.chen1335.dimensionDifficultyLevel.common.capability;

import com.chen1335.dimensionDifficultyLevel.API.Capabilities;
import com.chen1335.dimensionDifficultyLevel.common.DifficultyFactorCalculator;
import com.chen1335.dimensionDifficultyLevel.common.PersonalDifficultyCalculator;
import com.chen1335.dimensionDifficultyLevel.network.ModMessages;
import com.chen1335.dimensionDifficultyLevel.network.pack.DifficultyInfoPack;
import com.chen1335.dimensionDifficultyLevel.utils.StructureHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerStatue implements ICapabilitySerializable<CompoundTag> {
    private final LazyOptional<PlayerStatue> holder = LazyOptional.of(() -> this);

    public ResourceLocation currentDimension = null;

    public float eliteEnemyChance = 0;

    public float specialItemDropChance = 0;

    private int currentDifficulty = 0;

    private int currentDifficultyOld = 0;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return Capabilities.PLAYER_STATUE.orEmpty(cap, holder);
    }


    public void tick(Player player) {
        if (!player.level().isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (serverPlayer.level().getGameTime() % 20 == 0) {
                DifficultyFactorCalculator.DifficultyContext difficultyContext = PersonalDifficultyCalculator.calculate(serverPlayer, serverPlayer.serverLevel(), StructureHelper.getStructuresAt(serverPlayer.serverLevel(), serverPlayer.blockPosition()));
                currentDifficulty = difficultyContext.experienceLevel;
            }
            if (currentDifficultyOld != currentDifficulty) {
                currentDifficultyOld = currentDifficulty;
                ModMessages.sendToClient(new DifficultyInfoPack(currentDifficulty, eliteEnemyChance, specialItemDropChance), serverPlayer);
            }
        }

        if (currentDimension == null) {
            currentDimension = player.level().dimension().location();
        }
    }

    public void setCurrentDifficulty(int currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public int getCurrentDifficulty() {
        return currentDifficulty;
    }


    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
