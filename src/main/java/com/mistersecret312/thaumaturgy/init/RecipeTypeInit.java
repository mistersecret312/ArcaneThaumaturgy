package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.recipes.ArcaneCraftingShapedRecipe;
import com.mistersecret312.thaumaturgy.recipes.CompositionRecipe;
import com.mistersecret312.thaumaturgy.recipes.IArcaneCraftingRecipe;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypeInit
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<RecipeSerializer<TransmutationRecipe>> TRANSMUTATION = SERIALIZERS.register("transmutation", () -> TransmutationRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ArcaneCraftingShapedRecipe>> ARCANE_CRAFTING = SERIALIZERS.register("arcane_crafting_shaped", () -> ArcaneCraftingShapedRecipe.Serializer.INSTANCE);

    public static class Types
    {
        public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ArcaneThaumaturgyMod.MODID);

        public static final RegistryObject<RecipeType<IArcaneCraftingRecipe>> ARCANE_CRAFTING = TYPES.register("arcane_crafting_shaped", () -> new RecipeType<>() {});
        public static final RegistryObject<RecipeType<CompositionRecipe>> COMPOSITION = TYPES.register("composition", () -> new RecipeType<>() {});


        public static void register(IEventBus bus)
        {
            TYPES.register(bus);
        }

    }

    public static void register(IEventBus bus)
    {
        SERIALIZERS.register(bus);
    }
}
