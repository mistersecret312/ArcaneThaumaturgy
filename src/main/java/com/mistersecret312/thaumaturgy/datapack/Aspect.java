package com.mistersecret312.thaumaturgy.datapack;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class Aspect
{
    public static final ResourceLocation THAUMATURGY_LOCATION = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "aspect");
    public static final ResourceKey<Registry<Aspect>> REGISTRY_KEY = ResourceKey.createRegistryKey(THAUMATURGY_LOCATION);
    public static final Codec<ResourceKey<Aspect>> RESOURCE_KEY_CODEC = ResourceKey.codec(REGISTRY_KEY);

    public static final Codec<Aspect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Aspect::getName),
            Codec.INT.listOf().fieldOf("rgb").forGetter(Aspect::getColor),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(Aspect::getTexture),
            DerivativeAspectData.CODEC.optionalFieldOf("derivative").forGetter(Aspect::getDerivativeAspectData)
    ).apply(instance, Aspect::new));

    private String name;
    private List<Integer> color;
    private ResourceLocation texture;
    @Nullable
    private DerivativeAspectData derivativeAspectData;

    public Aspect(String name, List<Integer> color, ResourceLocation texture, Optional<DerivativeAspectData> derivativeData)
    {
        this.name = name;
        this.color = color;
        this.texture = texture;
        this.derivativeAspectData = derivativeData.orElse(null);
    }

    public String getName()
    {
        return name;
    }

    public List<Integer> getColor()
    {
        return color;
    }

    public ResourceLocation getTexture()
    {
        return texture;
    }

    public Optional<DerivativeAspectData> getDerivativeAspectData()
    {
        return Optional.ofNullable(this.derivativeAspectData);
    }

    public static class DerivativeAspectData
    {
        private final ResourceKey<Aspect> aspectA;
        private final ResourceKey<Aspect> aspectB;

        public static final Codec<DerivativeAspectData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RESOURCE_KEY_CODEC.fieldOf("first_aspect").forGetter(DerivativeAspectData::getAspectA),
                RESOURCE_KEY_CODEC.fieldOf("second_aspect").forGetter(DerivativeAspectData::getAspectB)
        ).apply(instance, DerivativeAspectData::new));

        public DerivativeAspectData(ResourceKey<Aspect> aspectA, ResourceKey<Aspect> aspectB)
        {
            this.aspectA = aspectA;
            this.aspectB = aspectB;
        }

        public ResourceKey<Aspect> getAspectA()
        {
            return aspectA;
        }

        public ResourceKey<Aspect> getAspectB()
        {
            return aspectB;
        }
    }
}
