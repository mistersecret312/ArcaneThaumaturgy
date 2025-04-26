package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NitorBlock extends Block implements EntityBlock {

    public static EnumProperty<NitorType> TYPE = EnumProperty.create("type", NitorType.class);
    private static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public NitorBlock(Properties properties){
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, NitorType.IGNIS));
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(TYPE);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new NitorBlockEntity(pPos, pState);
    }

    public enum NitorType implements StringRepresentable
    {
        ORDO("ordo"),
        IGNIS("ignis"),
        AER("aer"),
        TERRA("terra"),
        PERDITIO("perditio"),
        AQUA("aqua");

        String name;
        NitorType(String name)
        {
            this.name = name;
        }

        @Override
        public String getSerializedName()
        {
            return name;
        }
    }
}
