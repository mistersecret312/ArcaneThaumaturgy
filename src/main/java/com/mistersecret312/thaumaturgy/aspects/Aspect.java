package com.mistersecret312.thaumaturgy.aspects;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class Aspect
{
    public static final ResourceLocation ASPECT_LOCATION = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "aspect");
    public static final ResourceKey<Registry<Aspect>> REGISTRY_KEY = ResourceKey.createRegistryKey(ASPECT_LOCATION);
    public static final Codec<ResourceKey<Aspect>> RESOURCE_KEY_CODEC = ResourceKey.codec(REGISTRY_KEY);

    private List<Integer> color;
    private ResourceLocation texture;
    @Nullable
    private DerivedAspect.DerivationData derivationData = null;
    public Aspect(List<Integer> color, ResourceLocation texture)
    {
        this.color = color;
        this.texture = texture;
    }

    public List<Integer> getColor()
    {
        return color;
    }

    public ResourceLocation getTexture()
    {
        return texture;
    }

    public Aspect withDerivation(Aspect aspectA, Aspect aspectB)
    {
        derivationData = new DerivationData(aspectA, aspectB);
        return this;
    }

    public static class DerivationData
    {
        public Aspect aspectA;
        public Aspect aspectB;

        public DerivationData(Aspect aspectA, Aspect aspectB)
        {
            this.aspectA = aspectA;
            this.aspectB = aspectB;
        }

        public Aspect getAspectA()
        {
            return aspectA;
        }

        public Aspect getAspectB()
        {
            return aspectB;
        }
    }
}
