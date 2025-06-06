package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ArcaneThaumaturgyMod.MODID);

    public static RegistryObject<SoundEvent> WAND_USE = registerSoundEvent("wand_use");
    public static RegistryObject<SoundEvent> ESSENTIA_JAR_OPEN = registerSoundEvent("essentia_jar_open");
    public static RegistryObject<SoundEvent> ESSENTIA_JAR_CLOSE = registerSoundEvent("essentia_jar_close");
    public static RegistryObject<SoundEvent> CRUCIBLE_BUBBLE = registerSoundEvent("crucible_bubble");
    public static RegistryObject<SoundEvent> SILVERWOOD_BREAK = registerSoundEvent("silverwood_break");
    public static RegistryObject<SoundEvent> SILVERWOOD_STEP = registerSoundEvent("silverwood_step");

    private static RegistryObject<SoundEvent> registerSoundEvent(String sound)
    {
        return SOUNDS.register(sound, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ArcaneThaumaturgyMod.MODID, sound)));
    }

    public static void register(IEventBus bus)
    {
        SOUNDS.register(bus);
    }
}
