package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.aspects.UndefinedAspectStackHandler;
import com.mistersecret312.thaumaturgy.blocks.CrucibleBlock;
import com.mistersecret312.thaumaturgy.containers.CrucibleContainer;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.entities.HoveringItemEntity;
import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

import static com.mistersecret312.thaumaturgy.blocks.CrucibleBlock.IS_BOILING;
import static com.mistersecret312.thaumaturgy.blocks.CrucibleBlock.LEVEL;

public class CrucibleBlockEntity extends BlockEntity
{
    public UndefinedAspectStackHandler handler;
    public CrucibleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.CRUCIBLE.get(), pPos, pBlockState);
        handler = new UndefinedAspectStackHandler(16, true, 512);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrucibleBlockEntity crucible)
    {
        if (!level.isClientSide)
        {
            if (state.getValue(LEVEL) >= 3) {
                if (state.getValue(LEVEL) != 512 / crucible.handler.getTotalStored()) {
                    level.setBlockAndUpdate(pos, state.setValue(LEVEL, crucible.handler.getTotalStored()));
                }
            }
        }
    }

    public void itemThrown(ItemEntity itemEntity)
    {
        Optional<TransmutationRecipe> recipe = level.getRecipeManager().getRecipeFor(TransmutationRecipe.Type.INSTANCE, new CrucibleContainer(handler, itemEntity.getItem()), level);
        if(recipe.isPresent())
        {
            TransmutationRecipe realRecipe = recipe.get();
            itemEntity.getItem().shrink(1);
            realRecipe.aspectStacks.forEach(aspect ->
                    handler.extractAspect(aspect.getAspect().get(), aspect.getAmount(), false));

            HoveringItemEntity result = new HoveringItemEntity(level);
            result.setNoGravity(true);
            result.setItem(recipe.get().getResult());
            result.setPos(this.getBlockPos().getCenter().x, this.getBlockPos().getY()+2, this.getBlockPos().getCenter().z);
            result.setDeltaMovement(0, 0, 0);
            level.addFreshEntity(result);
        }
        if(recipe.isEmpty())
        {
            Optional<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> composition = level.getServer().registryAccess().registryOrThrow(AspectComposition.REGISTRY_KEY).entrySet().stream()
                    .filter(filter -> itemEntity.getItem().is(filter.getValue().getItem())).findFirst();

            composition.ifPresent(comp -> {
                comp.getValue().getAspects().forEach(aspect ->
                {
                    AspectStack aspectCopy = aspect.copy();
                    aspectCopy.setAmount(aspectCopy.getAmount()*itemEntity.getItem().getCount());
                    handler.insertAspect(aspect, false);
                });
                itemEntity.discard();
            });
        }
        markUpdated();
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        handler.deserializeNBT(((CompoundTag) compound.get("essentia")));
    }

    @Override
    protected void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.put("essentia", handler.serializeNBT());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    private void markUpdated() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }
}
