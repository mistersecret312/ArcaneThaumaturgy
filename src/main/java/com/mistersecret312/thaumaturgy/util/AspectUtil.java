package com.mistersecret312.thaumaturgy.util;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class AspectUtil
{
    public static final String ASPECT = "aspect";

    public static void setAspect(ItemStack stack, ResourceKey<Aspect> aspect)
    {
        stack.getOrCreateTag().putString(ASPECT, aspect.location().toString());
    }

    public static ResourceKey<Aspect> getAspect(CompoundTag tag)
    {
        String ID = tag.getString(ASPECT);
        if(!ID.isBlank())
        {
            ResourceLocation id = ResourceLocation.tryParse(ID);
            if(id == null)
                id = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "empty");
            return ResourceKey.create(Aspect.REGISTRY_KEY, id);
        }

        return ResourceKey.create(Aspect.REGISTRY_KEY, new ResourceLocation(ArcaneThaumaturgyMod.MODID, "ordo"));
    }

    public static ResourceKey<Item> getItem(CompoundTag tag)
    {
        String ID = tag.getString(AspectComposition.Serializible.ITEM);
        if(!ID.isBlank())
        {
            ResourceLocation id = ResourceLocation.tryParse(ID);
            if(id == null)
                id = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "dirt");
            return ResourceKey.create(Registries.ITEM, id);
        }

        return ResourceKey.create(Registries.ITEM, new ResourceLocation("dirt"));
    }
}
