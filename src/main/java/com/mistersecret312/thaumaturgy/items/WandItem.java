package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.aspects.DefinedAspectStackHandler;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.entities.HoveringItemEntity;
import com.mistersecret312.thaumaturgy.init.AspectInit;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mistersecret312.thaumaturgy.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WandItem extends Item
{
    public static final String VIS_STORAGE = "vis_storage";

    public WandItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock().equals(Blocks.BOOKSHELF)) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

            HoveringItemEntity result = new HoveringItemEntity(level);
            result.setNoGravity(true);
            result.setItem(ItemInit.THAUMONOMICON.get().getDefaultInstance());
            result.setPos(pos.getCenter().x, pos.getCenter().y + 0.5, pos.getCenter().z);
            result.setDeltaMovement(0, 0, 0);
            level.addFreshEntity(result);

            level.playSound(null, pos, SoundInit.WAND_USE.get(), SoundSource.BLOCKS, 1, 1);

            level.addAlwaysVisibleParticle(ParticleTypes.EXPLOSION, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 0, 0, 0);

            return InteractionResult.SUCCESS;
        }
        if (state.getBlock().equals(Blocks.CAULDRON)) {
            level.setBlockAndUpdate(pos, BlockInit.CRUCIBLE.get().defaultBlockState());

            level.playSound(null, pos, SoundInit.WAND_USE.get(), SoundSource.BLOCKS, 1, 1);

            level.addAlwaysVisibleParticle(ParticleTypes.EXPLOSION, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 0, 0, 0);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static ItemStack create(Item item, DefinedAspectStackHandler handler)
    {
        ItemStack stack = new ItemStack(item);
        if(item instanceof WandItem wand)
        {
            wand.setAspects(stack, handler);
        }
        return stack;
    }

    public static ItemStack createPrimal(Item item, int capacity, boolean full)
    {
        ItemStack stack = new ItemStack(item);
        List<Aspect> basePrimals = List.of(AspectInit.AER.get(), AspectInit.TERRA.get(), AspectInit.IGNIS.get(), AspectInit.AQUA.get(), AspectInit.ORDO.get(), AspectInit.PERDITIO.get());
        DefinedAspectStackHandler handler = new DefinedAspectStackHandler(basePrimals, capacity);

        basePrimals.forEach(primal -> handler.setStackInSlot(primal, new AspectStack(primal, full ? capacity : 0)));

        if(item instanceof WandItem wand)
        {
            wand.setAspects(stack, handler);
        }

        return stack;
    }



    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced)
    {
        super.appendHoverText(stack, pLevel, pTooltipComponents, pIsAdvanced);

        DefinedAspectStackHandler aspects = this.getAspects(stack);
        List<Aspect> basePrimals = List.of(AspectInit.AER.get(), AspectInit.TERRA.get(), AspectInit.IGNIS.get(), AspectInit.AQUA.get(), AspectInit.ORDO.get(), AspectInit.PERDITIO.get());
        if(new HashSet<>(aspects.getAspectTypes()).containsAll(basePrimals))
        {
            basePrimals.forEach(aspect ->
            {
                AspectStack aspectStack = aspects.getStackInSlot(aspect);
                if (aspectStack.isEmpty()) aspectStack = new AspectStack(aspect, 0);

                List<Integer> rgb = aspect.getColor();
                int color = (rgb.get(0) << 16) | (rgb.get(1) << 8) | rgb.get(2);

                pTooltipComponents.add(aspectStack.getTranslatable().append(": " + aspectStack.getAmount()).withStyle(style -> style.withColor(color)));

            });
        }
        else
        {
            aspects.getAspectTypes().forEach(aspect ->
            {
                AspectStack aspectStack = aspects.getStackInSlot(aspect);
                if (aspectStack.isEmpty()) aspectStack = new AspectStack(aspect, 0);

                List<Integer> rgb = aspect.getColor();
                int color = (rgb.get(0) << 16) | (rgb.get(1) << 8) | rgb.get(2);

                pTooltipComponents.add(aspectStack.getTranslatable().append(": " + aspectStack.getAmount()).withStyle(style -> style.withColor(color)));

            });
        }
    }

    public DefinedAspectStackHandler getAspects(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains(VIS_STORAGE))
            return DefinedAspectStackHandler.deserializeNBT(stack.getTag().getCompound(VIS_STORAGE));
        else
        {
            List<Aspect> basePrimals = List.of(AspectInit.AER.get(), AspectInit.TERRA.get(), AspectInit.IGNIS.get(), AspectInit.AQUA.get(), AspectInit.ORDO.get(), AspectInit.PERDITIO.get());
            return new DefinedAspectStackHandler(basePrimals, 1);
        }
    }

    public void setAspects(ItemStack stack, DefinedAspectStackHandler handler)
    {
        stack.getOrCreateTag().put(VIS_STORAGE, handler.serializeNBT());
    }


}
