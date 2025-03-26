package com.mistersecret312.thaumaturgy.client.item.item;

import com.mistersecret312.thaumaturgy.client.renderer.ThaumometerInfoRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ThaumometerItemClientExtention implements IClientItemExtensions {

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return ThaumometerInfoRenderer.INSTANCE;
    }
}
