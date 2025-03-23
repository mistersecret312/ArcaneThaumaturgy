package com.mistersecret312.thaumaturgy.data;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.util.AspectUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;

public class AspectCompositions extends SavedData
{
    public static final String FILE_NAME = ArcaneThaumaturgyMod.MODID + "-aspect_compositions";

    public static final String ASPECT_COMPOSITIONS = "aspect_compositions";

    public HashMap<Item, AspectComposition.Serializible> aspectCompositions = new HashMap<>();

    private MinecraftServer server;

    public final void updateData(MinecraftServer server)
    {
        AspectCompositions.get(server).eraseData(server);
        AspectCompositions.get(server).generateData(server);
    }

    public void eraseData(MinecraftServer server)
    {
        this.aspectCompositions.clear();
        this.setDirty();
    }

    public void generateData(MinecraftServer server)
    {
        Registry<AspectComposition> registry = server.registryAccess().registryOrThrow(AspectComposition.REGISTRY_KEY);
        registry.entrySet().forEach(entry -> {
            Item item = entry.getValue().getItem();
            AspectComposition composition = entry.getValue();

            this.aspectCompositions.put(item, new AspectComposition.Serializible(item, composition.isInheriting(), composition.getAspects()));
        });

        this.setDirty();
    }

    private CompoundTag serialize()
    {
        CompoundTag tag = new CompoundTag();

        tag.put(ASPECT_COMPOSITIONS, serializeAspectCompositions());

        return tag;
    }

    private CompoundTag serializeAspectCompositions()
    {
        CompoundTag aspectCompositionsTag = new CompoundTag();

        this.aspectCompositions.forEach(((item, aspectComposition) -> {
            aspectCompositionsTag.put(BuiltInRegistries.ITEM.getKey(item).toString(), aspectComposition.serialize());
        }));

        return aspectCompositionsTag;
    }

    private void deserialize(CompoundTag tag)
    {
        deserializeAspectCompositions(tag);
    }

    private void deserializeAspectCompositions(CompoundTag tag)
    {
        final RegistryAccess registries = server.registryAccess();
        final Registry<AspectComposition> compositionRegistry = registries.registryOrThrow(AspectComposition.REGISTRY_KEY);

        tag.getAllKeys().forEach(key -> {
            AspectComposition.Serializible composition = AspectComposition.Serializible.deserialize(tag.getCompound(key));
            this.aspectCompositions.put(BuiltInRegistries.ITEM.get(AspectUtil.getItem(tag.getCompound(key))), composition);
        });
    }

    public AspectCompositions(MinecraftServer server)
    {
        this.server = server;
    }

    public static AspectCompositions create(MinecraftServer server)
    {
        return new AspectCompositions(server);
    }

    public static AspectCompositions load(MinecraftServer server, CompoundTag tag)
    {
        AspectCompositions data = create(server);

        data.server = server;
        data.deserialize(tag);

        return data;
    }

    public CompoundTag save(CompoundTag tag)
    {
        tag = serialize();

        return tag;
    }

    @Nonnull
    public static AspectCompositions get(Level level)
    {
        if(level.isClientSide())
            throw new RuntimeException("Don't access this client-side!");

        return AspectCompositions.get(level.getServer());
    }

    @Nonnull
    public static AspectCompositions get(MinecraftServer server)
    {
        DimensionDataStorage storage = server.overworld().getDataStorage();

        return storage.computeIfAbsent((tag) -> load(server, tag), () -> create(server), FILE_NAME);
    }
}
