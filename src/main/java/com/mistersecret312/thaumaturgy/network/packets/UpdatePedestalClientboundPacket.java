package com.mistersecret312.thaumaturgy.network.packets;

import com.mistersecret312.thaumaturgy.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdatePedestalClientboundPacket
{
    public BlockPos pos;
    public ItemStack stack;

    public UpdatePedestalClientboundPacket(BlockPos pos, ItemStack stack)
    {
        this.pos = pos;
        this.stack = stack;
    }

    public static void write(UpdatePedestalClientboundPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItem(packet.stack);
    }

    public static UpdatePedestalClientboundPacket read(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        ItemStack stack = buffer.readItem();

        return new UpdatePedestalClientboundPacket(pos, stack);
    }

    public static void handle(UpdatePedestalClientboundPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandler.handleUpdatePedestalPacket(packet));
        context.get().setPacketHandled(true);
    }
}
