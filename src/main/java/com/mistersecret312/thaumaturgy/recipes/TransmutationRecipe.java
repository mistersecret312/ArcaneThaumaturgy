package com.mistersecret312.thaumaturgy.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.containers.CrucibleContainer;
import com.mistersecret312.thaumaturgy.init.AspectInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransmutationRecipe implements Recipe<CrucibleContainer>
{
    public static final Codec<Ingredient> INGREDIENT_CODEC = new PrimitiveCodec<>()
    {
        @Override
        public <T> DataResult<Ingredient> read(DynamicOps<T> ops, T input)
        {
            try
            {
                return DataResult.success(CraftingHelper.getIngredient(ops.convertTo(JsonOps.INSTANCE, input).getAsJsonObject(), false));
            } catch (JsonSyntaxException error)
            {
                return DataResult.error(error::getMessage);
            }
        }

        @Override
        public <T> T write(DynamicOps<T> ops, Ingredient value)
        {
            return JsonOps.INSTANCE.convertTo(ops, value.toJson());
        }
    };

    public static final Codec<ItemStack> STACK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(ItemStack::getItem),
            Codec.INT.fieldOf("amount").forGetter(ItemStack::getCount),
            CompoundTag.CODEC.optionalFieldOf("tag").forGetter(stack -> Optional.ofNullable(stack.getTag()))
    ).apply(instance, TransmutationRecipe::createStack));

    public static ItemStack createStack(Item item, int amount, Optional<CompoundTag> tag)
    {
        CompoundTag nbt = tag.orElse(null);
        return new ItemStack(item, amount, nbt);
    }

    public static final Codec<TransmutationRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            AspectStack.CODEC.listOf().fieldOf("aspects").forGetter(TransmutationRecipe::getAspects),
            INGREDIENT_CODEC.fieldOf("catalyst").forGetter(TransmutationRecipe::getCatalyst),
            STACK_CODEC.fieldOf("result").forGetter(TransmutationRecipe::getResult)
    ).apply(instance, TransmutationRecipe::new));

    public ResourceLocation recipeID;
    public List<AspectStack> aspects;
    public Ingredient catalyst;
    public ItemStack result;

    public TransmutationRecipe(List<AspectStack> aspects, Ingredient catalyst, ItemStack result)
    {
        this.aspects = aspects;
        this.catalyst = catalyst;
        this.result = result;
    }

    public TransmutationRecipe(ResourceLocation recipeID, List<AspectStack> aspects, Ingredient catalyst, ItemStack result)
    {
        this.recipeID = recipeID;
        this.aspects = aspects;
        this.catalyst = catalyst;
        this.result = result;
    }

    public List<AspectStack> getAspects()
    {
        return aspects;
    }

    public Ingredient getCatalyst()
    {
        return catalyst;
    }

    public ItemStack getResult()
    {
        return result;
    }

    @Override
    public boolean matches(CrucibleContainer container, Level level)
    {
        boolean catalystTest = this.catalyst.test(container.catalyst);
        List<Boolean> aspectTest = new ArrayList<>();

        for (int i = 0; i < aspects.size(); i++)
        {
            AspectStack recipeStack = aspects.get(i);
            AspectStack containerStack = container.handler.getStackInSlot(recipeStack.getAspect());

            aspectTest.add(containerStack != null && !containerStack.isEmpty() && containerStack.getAmount() >= recipeStack.getAmount());
        }

        return catalystTest && aspectTest.stream().allMatch(bool -> bool.equals(true));
    }

    @Override
    public ItemStack assemble(CrucibleContainer container, RegistryAccess registryAccess)
    {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess)
    {
        return result.copy();
    }

    @Override
    public ResourceLocation getId()
    {
        return recipeID;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<TransmutationRecipe>
    {
        public static final Type INSTANCE = new Type();
        public static final String ID = "transmutation";

        private Type()
        {

        }
    }

    public static class Serializer implements RecipeSerializer<TransmutationRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "transmutation");

        @Override
        public TransmutationRecipe fromJson(ResourceLocation recipeID, JsonObject jsonRecipe)
        {
            TransmutationRecipe recipe = TransmutationRecipe.CODEC.parse(JsonOps.INSTANCE, jsonRecipe)
            .getOrThrow(false,
                    s -> {
                throw new JsonParseException(s);
            });

            return new TransmutationRecipe(recipeID, recipe.aspects, recipe.catalyst, recipe.result);
        }

        @Override
        public @Nullable TransmutationRecipe fromNetwork(ResourceLocation recipeID, FriendlyByteBuf buffer)
        {
            List<AspectStack> stacks = buffer.readCollection(i -> new ArrayList<>(), readBuffer -> {
                Aspect aspect = readBuffer.readRegistryId();
                int amount = readBuffer.readInt();

                AspectStack stack = new AspectStack(aspect, amount);
                return stack;
            });
            Ingredient catalyst = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new TransmutationRecipe(recipeID, stacks, catalyst, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TransmutationRecipe recipe)
        {
            buffer.writeCollection(recipe.aspects, (writeBuffer, aspectStack) -> {
                writeBuffer.writeRegistryId(AspectInit.ASPECT.get(), aspectStack.getAspect());
                writeBuffer.writeInt(aspectStack.getAmount());
            });
            recipe.catalyst.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
