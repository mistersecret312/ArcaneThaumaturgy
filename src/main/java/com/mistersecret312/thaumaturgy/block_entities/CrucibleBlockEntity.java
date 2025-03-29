package com.mistersecret312.thaumaturgy.block_entities;

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

public class CrucibleBlockEntity extends BlockEntity
{
    private int waterLevel;
    private int itemsCrafted;


    public UndefinedAspectStackHandler handler;
    public CrucibleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.CRUCIBLE.get(), pPos, pBlockState);
        waterLevel = 0; //Cap 4
        itemsCrafted = 0; //Cap 4, 4 items can be crafted per 1 water level
        handler = new UndefinedAspectStackHandler(16, true, 256);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrucibleBlockEntity crucible)
    {
        if (!level.isClientSide)
        {

        }
    }

    public void itemThrown(ItemEntity itemEntity)
    {
        Optional<TransmutationRecipe> recipe = level.getRecipeManager().getRecipeFor(TransmutationRecipe.Type.INSTANCE, new CrucibleContainer(handler, itemEntity.getItem()), level);
        if(this.getWaterLevel() > 0 && this.getBlockState().getValue(CrucibleBlock.IS_BOILING))
        {
            if(recipe.isPresent())
            {
                TransmutationRecipe realRecipe = recipe.get();
                itemEntity.getItem().shrink(1);
                realRecipe.aspectStacks.forEach(aspect ->
                        handler.extractAspect(aspect.getAspect().get(), aspect.getAmount(), false));
                this.setItemsCrafted(this.getItemsCrafted()+1);
                if(this.getItemsCrafted() >= 4)
                {
                    this.setWaterLevel(this.getWaterLevel()-1);
                    this.setItemsCrafted(0);
                }

                HoveringItemEntity result = new HoveringItemEntity(level);
                result.setItem(recipe.get().getResult());
                result.setPos(this.getBlockPos().getCenter().x, this.getBlockPos().getY()+1.2, this.getBlockPos().getCenter().z);
                level.addFreshEntity(result);
            }
            if(recipe.isEmpty())
            {
                Optional<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> composition = level.getServer().registryAccess().registryOrThrow(AspectComposition.REGISTRY_KEY).entrySet().stream()
                        .filter(filter -> itemEntity.getItem().is(filter.getValue().getItem())).findFirst();

                composition.ifPresent(comp -> {
                    comp.getValue().getAspects().forEach(aspect ->
                    {
                        handler.insertAspect(aspect, false);
                    });
                    itemEntity.getItem().shrink(1);
                });

            }
        }
    }

    public int getWaterLevel()
    {
        return waterLevel;
    }


    public void setWaterLevel(int newLevel)
    {
        waterLevel = newLevel;
    }

    public int getItemsCrafted()
    {
        return itemsCrafted;
    }

    public void setItemsCrafted(int newItems)
    {
        itemsCrafted = newItems;
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        waterLevel = compound.getInt("water_level");
        itemsCrafted = compound.getInt("items_crafted");

        handler.deserializeNBT(((CompoundTag) compound.get("essentia")));
    }

    @Override
    protected void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.putInt("water_level", waterLevel);
        compound.putInt("items_crafted", itemsCrafted);

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
