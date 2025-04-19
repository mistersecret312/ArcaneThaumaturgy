package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.client.renderer.FakeAspectRenderer;
import com.mistersecret312.thaumaturgy.init.AspectInit;
import com.mistersecret312.thaumaturgy.init.ItemInit;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class AspectItem extends Item
{
    public AspectItem()
    {
        super(new Properties().stacksTo(2048));
    }

    public static ItemStack create(Aspect aspect)
    {
        AspectItem item = (AspectItem) ItemInit.ASPECT.get();
        ItemStack stack = new ItemStack(item);

        item.setAspect(stack, new AspectStack(aspect));

        return stack;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced)
    {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        List<Integer> rgb = AspectInit.getAspect(getAspect(pStack)).getColor();
        int color = (rgb.get(0) << 16) | (rgb.get(1) << 8) | rgb.get(2);


        pTooltipComponents.add(Component.translatable("aspect."+getAspect(pStack).toLanguageKey()+".description").withStyle(style -> style.withColor(color)));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {

            private final BlockEntityWithoutLevelRenderer renderer = new FakeAspectRenderer();
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return renderer;
            }


        });
    }

    @Override
    public Component getName(ItemStack pStack)
    {
        return new AspectStack(AspectInit.getAspect(getAspect(pStack))).getTranslatable();
    }

    public void setAspect(ItemStack stack, AspectStack aspectStack)
    {
        stack.setCount(aspectStack.getAmount());
        stack.getOrCreateTag().putString("aspect", aspectStack.getAspect().getTexture().toString());
        stack.getOrCreateTag().putString("aspectKey", AspectInit.ASPECT.get().getKey(aspectStack.getAspect()).toString());
    }

    public static ResourceLocation getAspect(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains("aspectKey"))
        {
            String id = stack.getTag().getString("aspectKey");
            return ResourceLocation.parse(id);
        }
        return ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "ordo");
    }

    public ResourceLocation getTexture(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains("aspect"))
            return ResourceLocation.parse(stack.getTag().getString("aspect"));
        else return new ResourceLocation(ArcaneThaumaturgyMod.MODID, "textures/aspect/error.png");
    }
}
