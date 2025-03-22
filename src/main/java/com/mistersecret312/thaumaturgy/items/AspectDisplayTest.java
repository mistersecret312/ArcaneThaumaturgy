package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.tooltipcomponents.AspectTooltipComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AspectDisplayTest extends Item
{
    public static final String ASPECT = "aspect";

    public AspectDisplayTest(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced)
    {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if(this.getAspect(pStack) != null)
        {
            Minecraft minecraft = Minecraft.getInstance();
            ClientPacketListener clientPacketListener = minecraft.getConnection();
            RegistryAccess registries = clientPacketListener.registryAccess();
            Registry<Aspect> aspectsRegistry = registries.registryOrThrow(Aspect.REGISTRY_KEY);

            Aspect aspect = aspectsRegistry.get(this.getAspect(pStack));
            if(aspect != null)
            {
                Component aspectText = Component.translatable("thaumaturgy.aspect." + aspect.getName());
                pTooltipComponents.add(aspectText);
            }
        }
    }

    public static ItemStack create(ResourceKey<Aspect> aspect)
    {
        AspectDisplayTest item = ItemInit.DISPLAY_TEST.get();
        ItemStack stack = item.getDefaultInstance();

        item.setAspect(stack, aspect);

        return stack;
    }

    public ResourceKey<Aspect> getAspect(ItemStack stack)
    {
        String ID = "";
        if(stack.getTag() != null && stack.getTag().contains(ASPECT))
        {
            ID = stack.getTag().getString(ASPECT);
        }
        if(!ID.isBlank())
        {
            ResourceLocation id = ResourceLocation.tryParse(ID);
            if(id == null)
                id = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "empty");
            return ResourceKey.create(Aspect.REGISTRY_KEY, id);
        }

        return null;
    }

    public void setAspect(ItemStack stack, ResourceKey<Aspect> aspect)
    {
        stack.getOrCreateTag().putString(ASPECT, aspect.location().toString());
    }
}
