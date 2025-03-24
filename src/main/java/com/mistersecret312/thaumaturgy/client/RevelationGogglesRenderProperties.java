package com.mistersecret312.thaumaturgy.client;

import com.mistersecret312.thaumaturgy.client.model.RevelationGogglesModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class RevelationGogglesRenderProperties implements IClientItemExtensions
{
    public static final RevelationGogglesRenderProperties INSTANCE = new RevelationGogglesRenderProperties();

    private RevelationGogglesRenderProperties()
    {

    }

    @Override
    public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                           EquipmentSlot equipmentSlot, HumanoidModel<?> original)
    {
        return RevelationGogglesModel.INSTANCE;
    }
}
