package com.mistersecret312.thaumaturgy.network;

import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.aspects.UndefinedAspectStackHandler;
import com.mistersecret312.thaumaturgy.block_entities.CrucibleBlockEntity;
import com.mistersecret312.thaumaturgy.block_entities.PedestalBlockEntity;
import com.mistersecret312.thaumaturgy.network.packets.UpdateCrucibleClientboundPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ClientPacketHandler
{
    public static void handleUpdateCruciblePacket(UpdateCrucibleClientboundPacket packet)
    {
        BlockEntity blockEntity = getBlockEntity(packet.pos);
        if(blockEntity instanceof CrucibleBlockEntity crucible)
        {
            UndefinedAspectStackHandler handler = new UndefinedAspectStackHandler(packet.stacks, true, 512);

            crucible.handler = handler;
        }


    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> T getEntity(int entityId) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null)
            return null;
        Entity entity = level.getEntity(entityId);
        return (T) entity;
    }

    public static <T extends BlockEntity> T getBlockEntity(BlockPos pos)
    {
        ClientLevel level = Minecraft.getInstance().level;
        if(level == null)
            return null;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return (T) blockEntity;
    }

    public static LocalPlayer getPlayer()
    {
        return Minecraft.getInstance().player;
    }
}
