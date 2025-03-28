package com.mistersecret312.thaumaturgy.init;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class SoundTypeInit
{
    public static final SoundType NITOR = new ForgeSoundType(1.0F, 1.0F, () -> SoundEvents.FIRE_EXTINGUISH, () -> SoundEvents.EMPTY, () -> SoundEvents.FIRECHARGE_USE, () -> SoundEvents.EMPTY, () -> SoundEvents.EMPTY);

    public static final SoundType ESSENTIA_JAR = new ForgeSoundType(1.0F, 1.0F, () -> SoundInit.ESSENTIA_JAR_OPEN.get(), () -> SoundEvents.GLASS_STEP, () -> SoundInit.ESSENTIA_JAR_OPEN.get(), () -> SoundEvents.EMPTY, () -> SoundEvents.GLASS_FALL);
}
