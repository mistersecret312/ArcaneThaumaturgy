package com.mistersecret312.thaumaturgy.entities;

import com.mistersecret312.thaumaturgy.init.EntityTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

public class HoveringItemEntity extends ItemEntity
{

    public HoveringItemEntity(EntityType<? extends ItemEntity> pEntityType, Level pLevel)
    {
        super(pEntityType, pLevel);
    }

    public HoveringItemEntity(Level level)
    {
        this(EntityTypeInit.HOVERING_ITEM.get(), level);
    }
}
