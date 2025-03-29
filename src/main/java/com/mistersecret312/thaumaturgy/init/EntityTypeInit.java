package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.entities.HoveringItemEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeInit
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<EntityType<HoveringItemEntity>> HOVERING_ITEM = ENTITY_TYPES.register("hovering_item_entity",
            () -> EntityType.Builder.<HoveringItemEntity>of(HoveringItemEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(64).setCustomClientFactory(((spawnEntity, level) -> new HoveringItemEntity(level))).build("hovering_item_entity"));

    public static void register(IEventBus bus)
    {
        ENTITY_TYPES.register(bus);
    }

}
