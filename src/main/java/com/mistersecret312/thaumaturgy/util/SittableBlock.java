package com.mistersecret312.thaumaturgy.util;

import com.mistersecret312.thaumaturgy.entities.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

//Code from Handcrafted, all credit goes to the respective devs

public interface SittableBlock {
    default boolean sitOn(Level level, BlockPos pos, Player player, Direction dir) {
        if (!level.isClientSide() && !SeatEntity.SITTING_POSITIONS.get(level.dimension()).contains(pos)) {
            SeatEntity entity = SeatEntity.of(level, pos, dir);
            if (level.addFreshEntity(entity)) {
                player.startRiding(entity);
                return true;
            } else {
                entity.removeSeat();
            }
        }
        return false;
    }

    AABB getSeatSize(BlockState state);
}
