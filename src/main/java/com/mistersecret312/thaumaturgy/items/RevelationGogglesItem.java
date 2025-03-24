package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.client.RevelationGogglesRenderProperties;
import com.mistersecret312.thaumaturgy.client.model.RevelationGogglesModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class RevelationGogglesItem extends ArmorItem
{

    public RevelationGogglesItem(Properties pProperties)
    {
        super(ArmorMaterials.DIAMOND, Type.HELMET, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(RevelationGogglesRenderProperties.INSTANCE);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type)
    {
        return ArcaneThaumaturgyMod.MODID + ":textures/armor/revelation_goggles.png";
    }
}
