package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NitorBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public NitorBlock(Properties properties){
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context){
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context){
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        double x = pPos.getX() + .5;
        double y = pPos.getY() + .5;
        double z = pPos.getZ() + .5;
        for(int i = 0; i < 3; i++){
            double vX = pRandom.nextGaussian() / 12;
            double vY = pRandom.nextGaussian() / 12;
            double vZ = pRandom.nextGaussian() / 12;
            pLevel.addParticle(pRandom.nextBoolean() ? ParticleTypes.SMOKE : ParticleTypes.FLAME, x + vX, y + vY, z + vZ, vX / 16, vY / 16, vZ / 16);
        }
        super.animateTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new NitorBlockEntity(pPos, pState);
    }
}
