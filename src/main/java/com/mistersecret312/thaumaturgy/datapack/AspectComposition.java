package com.mistersecret312.thaumaturgy.datapack;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.util.AspectUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AspectComposition
{
    public static final ResourceLocation THAUMATURGY_LOCATION = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "aspect_composition");
    public static final ResourceKey<Registry<AspectComposition>> REGISTRY_KEY = ResourceKey.createRegistryKey(THAUMATURGY_LOCATION);
    public static final Codec<ResourceKey<AspectComposition>> RESOURCE_KEY_CODEC = ResourceKey.codec(REGISTRY_KEY);

    public static final Codec<AspectComposition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(AspectComposition::getItem),
            Codec.BOOL.optionalFieldOf("inherit_crafting_aspects", false).forGetter(AspectComposition::isInheriting),
            AspectStack.CODEC.listOf().fieldOf("aspects").forGetter(AspectComposition::getAspects)
    ).apply(instance, AspectComposition::new));

    private Item item;
    private boolean inherit;
    private List<AspectStack> aspects;
    public AspectComposition(Item item, boolean inherit, List<AspectStack> aspects)
    {
        this.item = item;
        this.inherit = inherit;
        this.aspects = aspects;
    }

    public Item getItem()
    {
        return item;
    }

    public boolean isInheriting()
    {
        return inherit;
    }

    public List<AspectStack> getAspects()
    {
        return aspects;
    }

    public static class AspectStack
    {
        public static final Codec<AspectStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceKey.codec(Aspect.REGISTRY_KEY).fieldOf("aspect").forGetter(AspectStack::getAspect),
                Codec.INT.fieldOf("amount").forGetter(AspectStack::getAmount)
        ).apply(instance, AspectStack::new));

        private ResourceKey<Aspect> aspect;
        private int amount;

        public AspectStack(ResourceKey<Aspect> aspect, int amount)
        {
            this.aspect = aspect;
            this.amount = amount;
        }

        public ResourceKey<Aspect> getAspect()
        {
            return aspect;
        }

        public int getAmount()
        {
            return amount;
        }
    }
}
