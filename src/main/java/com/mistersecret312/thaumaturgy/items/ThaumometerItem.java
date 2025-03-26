package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.client.item.item.ThaumometerItemClientExtention;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ThaumometerItem extends Item {
    public ThaumometerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new ThaumometerItemClientExtention());
    }
}
