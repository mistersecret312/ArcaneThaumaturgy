package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.NetworkInit;
import com.mistersecret312.thaumaturgy.network.packets.UpdateCrucibleClientboundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

import static com.mistersecret312.thaumaturgy.blocks.GreatwoodTapBlock.SAP;
import static com.mistersecret312.thaumaturgy.blocks.GreatwoodTapBlock.FACING;

public class GreatwoodTapBlockEntity extends BlockEntity {
    private int ticker = -1;

    public GreatwoodTapBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.GREATWOOD_TAP.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, GreatwoodTapBlockEntity tap) {
        Direction tapDirection = pState.getValue(FACING);
        RandomSource pRandom = pLevel.getRandom();
        if (tap.ticker == -1) {
            tap.ticker = pRandom.nextInt(100, 200);
        }

        if (tap.isOnGreatwoodTree(pLevel, pPos, pState)) {
            if (tap.ticker == 0) {
                if (pRandom.nextDouble() > 0.75) {
                    if (!pState.getValue(SAP)) {
                        if (!pLevel.isClientSide) {
                            pLevel.setBlock(pPos, pState.setValue(SAP, true), 2);
                        }

                        pLevel.playSound(null, pPos, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 1, 1.25f);
                    }
                }
                double x = pPos.getX() + 0.5;
                double y = pPos.getY() + 0.2;
                double z = pPos.getZ() + 0.5;

                if (tapDirection == Direction.NORTH) {
                    z = z + 0.15;
                } else if (tapDirection == Direction.SOUTH) {
                    z = z - 0.15;
                } else if (tapDirection == Direction.EAST) {
                    x = x - 0.15;
                } else if (tapDirection == Direction.WEST) {
                    x = x + 0.15;
                }

                for (int i = 0; i < 3; i++) {
                    pLevel.addParticle(ParticleTypes.DRIPPING_HONEY, x, y, z, 0, 0, 0);
                }

                tap.ticker = pRandom.nextInt(100, 200);
            } else {
                tap.ticker = tap.ticker - 1;
            }
        }
    }

    public boolean isOnGreatwoodTree(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        Direction tapDirection = pState.getValue(FACING);
        Block tappedBlock = pLevel.getBlockState(pPos.relative(tapDirection.getOpposite())).getBlock();
        return tappedBlock.equals(BlockInit.GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.GREATWOOD_WOOD.get()) || tappedBlock.equals(BlockInit.STRIPPED_GREATWOOD_LOG.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_WOOD.get());
    }
}
