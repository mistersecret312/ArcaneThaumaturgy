package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.ParticleInit;
import com.mistersecret312.thaumaturgy.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.mistersecret312.thaumaturgy.blocks.GreatwoodSapBowlBlock.SAP_AMOUNT;
import static com.mistersecret312.thaumaturgy.blocks.GreatwoodTapBlock.FACING;
import static com.mistersecret312.thaumaturgy.blocks.GreatwoodTapBlock.SAP;

public class SilverwoodTapBlockEntity extends BlockEntity {
    private int ticker = -1;

    public SilverwoodTapBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.SILVERWOOD_TAP.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SilverwoodTapBlockEntity tap) {
        Direction tapDirection = pState.getValue(FACING);
        RandomSource pRandom = pLevel.getRandom();
        if (tap.ticker == -1) {
            tap.ticker = pRandom.nextInt(100, 200);
        }

        if (tap.isOnSilverwoodTree(pLevel, pPos, pState)) {
            if (tap.ticker == 0) {
                if (pRandom.nextDouble() > 0.75) {
                    BlockState belowBlock = pLevel.getBlockState(pPos.below());

                    if (belowBlock.getBlock() == BlockInit.SILVERWOOD_SAP_BOWL.get() && belowBlock.getValue(SAP_AMOUNT) < 5) {
                        int sap = belowBlock.getValue(SAP_AMOUNT);

                        if (sap < 5) {
                            if (!pLevel.isClientSide) {
                                pLevel.setBlock(pPos.below(), belowBlock.setValue(SAP_AMOUNT, sap + 1), 2);
                            }
                            pLevel.playSound(null, pPos.below(), SoundInit.SILVERWOOD_BREAK.get(), SoundSource.BLOCKS, 1, 1.25f);
                        }
                    } else {
                        if (!pState.getValue(SAP)) {
                            if (!pLevel.isClientSide) {
                                pLevel.setBlock(pPos, pState.setValue(SAP, true), 2);
                            }
                            pLevel.playSound(null, pPos, SoundInit.SILVERWOOD_BREAK.get(), SoundSource.BLOCKS, 1, 1.25f);
                        }
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
                    pLevel.addParticle(ParticleInit.SILVERWOOD_SAP_HANGING.get(), x, y - 0.0625, z, 0, 0, 0);
                }

                tap.ticker = pRandom.nextInt(100, 200);
            } else {
                tap.ticker = tap.ticker - 1;
            }
        }
    }

    public boolean isOnSilverwoodTree(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        Direction tapDirection = pState.getValue(FACING);
        Block tappedBlock = pLevel.getBlockState(pPos.relative(tapDirection.getOpposite())).getBlock();
        return tappedBlock.equals(BlockInit.SILVERWOOD_LOG.get()) || tappedBlock.equals(BlockInit.SILVERWOOD_WOOD.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_LOG.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_WOOD.get());
    }
}
