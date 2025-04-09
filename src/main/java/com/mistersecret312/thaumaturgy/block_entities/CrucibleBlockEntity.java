package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.aspects.UndefinedAspectStackHandler;
import com.mistersecret312.thaumaturgy.containers.CrucibleContainer;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.entities.HoveringItemEntity;
import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import com.mistersecret312.thaumaturgy.init.NetworkInit;
import com.mistersecret312.thaumaturgy.init.SoundInit;
import com.mistersecret312.thaumaturgy.network.packets.UpdateCrucibleClientboundPacket;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.BubbleParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mistersecret312.thaumaturgy.blocks.CrucibleBlock.IS_BOILING;
import static com.mistersecret312.thaumaturgy.blocks.CrucibleBlock.LEVEL;

public class CrucibleBlockEntity extends BlockEntity
{
    public static final int MAX_CAPACITY = 512;

    public UndefinedAspectStackHandler handler;
    public CrucibleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.CRUCIBLE.get(), pPos, pBlockState);
        handler = new UndefinedAspectStackHandler(16, true, MAX_CAPACITY)
        {
            @Override
            public void onContentsChanged(Aspect aspect)
            {
                markUpdated();
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrucibleBlockEntity crucible)
    {
        if(!level.isClientSide() && state.getValue(LEVEL) >= 3 && state.getValue(IS_BOILING))
        {
            Aspect randomAspect = crucible.handler.getRandomAspect();
            if (level.getGameTime() % 400 == 0 && randomAspect != null)
            {
                AspectStack stack = crucible.handler.extractAspect(randomAspect, 1, false);
                if (!stack.getAspect().isPrimal() && stack.getAspect().getDerivationData() != null)
                {
                    if (level.random.nextBoolean())
                    {
                        crucible.handler.insertAspect(new AspectStack(stack.getAspect().getDerivationData().aspectA), false);
                    } else
                        crucible.handler.insertAspect(new AspectStack(stack.getAspect().getDerivationData().aspectB), false);
                }
            }

            int aspects = crucible.handler.getTotalStored();
            int value = Math.min(aspects / 170, 3);

            //state.setValue(LEVEL, value+3);
            level.setBlockAndUpdate(pos, state.setValue(LEVEL, value + 3));
        }
        if (state.getValue(IS_BOILING))
        {
            particleTick(level, pos, state, crucible);
        }


    }

    public static void particleTick(Level level, BlockPos pos, BlockState state, CrucibleBlockEntity crucible)
    {
        RandomSource random = level.getRandom();
        double pixelHeight = 0.0625;
        double waterHeight = 13 + state.getValue(LEVEL) - 3;
        double y = pos.getY() + waterHeight * pixelHeight;

        if (random.nextDouble() > 1 - 0.15 * state.getValue(LEVEL))
        {
            double x = pos.getX() + 0.5D + (random.nextDouble() * 0.6D - 0.3D);
            double z = pos.getZ() + 0.5D + (random.nextDouble() * 0.6D - 0.3D);

            level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE, x, y, z, 0, 0.02 * state.getValue(LEVEL), 0);
            level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_POP, x, y, z, 0, 0.02 * state.getValue(LEVEL), 0);
        }

        if (random.nextDouble() > 0.95)
        {
            level.playSound(null, crucible.getBlockPos(), SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 1, 0.2f + (level.random.nextFloat() * 0.5f));
        }
    }

    public void itemThrown(ItemEntity itemEntity)
    {
        Optional<TransmutationRecipe> recipe = level.getRecipeManager().getRecipeFor(TransmutationRecipe.Type.INSTANCE, new CrucibleContainer(handler, itemEntity.getItem()), level);
        if(recipe.isPresent())
        {
            TransmutationRecipe realRecipe = recipe.get();
            itemEntity.getItem().shrink(1);
            realRecipe.aspects.forEach(aspect ->
                    handler.extractAspect(aspect.getAspect(), aspect.getAmount(), false));

            HoveringItemEntity result = new HoveringItemEntity(level);
            result.setNoGravity(true);
            result.setItem(recipe.get().getResult());
            result.setPos(this.getBlockPos().getCenter().x, this.getBlockPos().getY()+2, this.getBlockPos().getCenter().z);
            result.setDeltaMovement(0, 0, 0);
            level.addFreshEntity(result);
            level.playSound(null, this.getBlockPos(), SoundInit.CRUCIBLE_BUBBLE.get(), SoundSource.BLOCKS, 1, 0.75f);
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
                    handler.insertAspect(aspectCopy, false);
                });
                itemEntity.discard();
                level.playSound(null, this.getBlockPos(), SoundInit.CRUCIBLE_BUBBLE.get(), SoundSource.BLOCKS, 1, 0.75f);
            });
        }
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

    private void markUpdated()
    {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        if (level != null && !level.isClientSide())
            NetworkInit.sendToTracking(this, new UpdateCrucibleClientboundPacket(this.getBlockPos(), this.handler.toList()));
    }
}
