package com.mistersecret312.thaumaturgy.init;

import net.minecraft.world.food.FoodProperties;

public class FoodsInit {
    FoodsInit() {
    }

    public static final FoodProperties GREAT_SYRUP_BOTTLE = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.3F).build();
    public static final FoodProperties GREAT_SAP_BOTTLE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.1F).build();
}
