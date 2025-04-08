package com.mistersecret312.thaumaturgy.network.packets;

import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.init.AspectInit;
import com.mistersecret312.thaumaturgy.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UpdateCrucibleClientboundPacket
{
    public BlockPos pos;
    public List<AspectStack> stacks;

    public UpdateCrucibleClientboundPacket(BlockPos pos, List<AspectStack> stacks)
    {
        this.pos = pos;
        this.stacks = stacks;
    }

    public static void write(UpdateCrucibleClientboundPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(packet.pos);
        buffer.writeCollection(packet.stacks, (writer, stack) -> {
            if(!stack.isEmpty())
            {
                writer.writeRegistryId(AspectInit.ASPECT.get(), stack.getAspect());
                writer.writeInt(stack.getAmount());
            }
        });
    }

    public static UpdateCrucibleClientboundPacket read(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        List<AspectStack> stacks;
        stacks = buffer.readCollection(i -> new ArrayList<>(), reader -> {
            Aspect aspect = reader.readRegistryId();
            int amount = reader.readInt();

            return new AspectStack(aspect, amount);
        });

        return new UpdateCrucibleClientboundPacket(pos, stacks);
    }

    public static void handle(UpdateCrucibleClientboundPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandler.handleUpdateCruciblePacket(packet));
        context.get().setPacketHandled(true);
    }
}
