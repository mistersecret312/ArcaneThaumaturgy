package com.mistersecret312.thaumaturgy.datapack;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class AspectComposition
{
    public static final ResourceLocation THAUMATURGY_LOCATION = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "aspect_composition");
    public static final ResourceKey<Registry<AspectComposition>> REGISTRY_KEY = ResourceKey.createRegistryKey(THAUMATURGY_LOCATION);
    public static final Codec<ResourceKey<AspectComposition>> RESOURCE_KEY_CODEC = ResourceKey.codec(REGISTRY_KEY);

    public static final Codec<AspectComposition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.mapEither(Codec.mapEither(BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity"),
                            ForgeRegistries.FLUIDS.getCodec().fieldOf("fluid")),
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item")).forGetter(AspectComposition::getObject),
            Codec.BOOL.optionalFieldOf("inherit_crafting_aspects", false).forGetter(AspectComposition::isInheriting),
            AspectStack.CODEC.listOf().fieldOf("aspects").forGetter(AspectComposition::getAspects)
    ).apply(instance, AspectComposition::new));

    private Either<Either<EntityType<?>, Fluid>, Item> object;
    private boolean inherit;
    private List<AspectStack> aspects;
    public AspectComposition(Either<Either<EntityType<?>, Fluid>, Item> object, boolean inherit, List<AspectStack> aspects)
    {
        this.object = object;
        this.inherit = inherit;
        this.aspects = aspects;
    }

    protected Either<Either<EntityType<?>, Fluid>, Item> getObject()
    {
        return object;
    }

    @Nullable
    public Item getItem()
    {
        return object.right().orElse(null);
    }

    @Nullable
    public EntityType<?> getEntity()
    {
        Either<EntityType<?>, Fluid> either = object.left().orElse(null);
        if(either != null)
            return either.left().orElse(null);
        else return null;
    }

    @Nullable
    public Fluid getFluid()
    {
        Either<EntityType<?>, Fluid> either = object.left().orElse(null);
        if(either != null)
            return either.right().orElse(null);
        else return null;
    }

    public boolean isInheriting()
    {
        return inherit;
    }

    public List<AspectStack> getAspects()
    {
        return aspects;
    }


}
