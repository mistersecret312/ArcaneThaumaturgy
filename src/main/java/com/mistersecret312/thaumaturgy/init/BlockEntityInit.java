package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.block_entities.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<BlockEntityType<ArcaneCraftingTableBlockEntity>> ARCANE_WORKBENCH = BLOCK_ENTITIES.register("arcane_workbench",
            () -> BlockEntityType.Builder.of(ArcaneCraftingTableBlockEntity::new, BlockInit.ARCANE_WORKBENCH.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE = BLOCK_ENTITIES.register("crucible",
            () -> BlockEntityType.Builder.of(CrucibleBlockEntity::new, BlockInit.CRUCIBLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL = BLOCK_ENTITIES.register("pedestal",
            () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, BlockInit.ARCANE_STONE_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<NitorBlockEntity>> NITOR = BLOCK_ENTITIES.register("nitor",
            () -> BlockEntityType.Builder.of(NitorBlockEntity::new, BlockInit.NITOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<ResearchTableBlockEntity>> RESEARCH_TABLE = BLOCK_ENTITIES.register("research_table",
            () -> BlockEntityType.Builder.of(ResearchTableBlockEntity::new, BlockInit.RESEARCH_TABLE.get()).build(null));

    public static void register(IEventBus bus)
    {
        BLOCK_ENTITIES.register(bus);
    }
}
