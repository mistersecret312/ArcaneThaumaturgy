package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypeInit
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<RecipeSerializer<TransmutationRecipe>> TRANSMUTATION = SERIALIZERS.register("transmutation", () -> TransmutationRecipe.Serializer.INSTANCE);

    public static void register(IEventBus bus)
    {
        SERIALIZERS.register(bus);
    }
}
