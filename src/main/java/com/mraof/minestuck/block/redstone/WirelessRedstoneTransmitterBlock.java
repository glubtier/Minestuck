package com.mraof.minestuck.block.redstone;

import com.mraof.minestuck.block.BlockUtil;
import com.mraof.minestuck.client.gui.MSScreenFactories;
import com.mraof.minestuck.effects.CreativeShockEffect;
import com.mraof.minestuck.tileentity.MSTileEntityTypes;
import com.mraof.minestuck.tileentity.redstone.WirelessRedstoneTransmitterTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Checks for redstone power inputs and transmits that signal to any wireless redstone receiver present at the location stored in the tile entity
 * GUI is limited by creative shock
 */
public class WirelessRedstoneTransmitterBlock extends HorizontalDirectionalBlock implements EntityBlock
{
	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED; //used for texture purposes
	
	public WirelessRedstoneTransmitterBlock(Properties properties)
	{
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWER, 0).setValue(POWERED, false));
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new WirelessRedstoneTransmitterTileEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> placedType)
	{
		return !level.isClientSide ? BlockUtil.checkTypeForTicker(placedType, MSTileEntityTypes.WIRELESS_REDSTONE_TRANSMITTER.get(), WirelessRedstoneTransmitterTileEntity::serverTick) : null;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
	{
		if(!CreativeShockEffect.doesCreativeShockLimit(player, CreativeShockEffect.LIMIT_MACHINE_INTERACTIONS))
		{
			if(level.getBlockEntity(pos) instanceof WirelessRedstoneTransmitterTileEntity transmitter)
			{
				if(level.isClientSide)
				{
					MSScreenFactories.displayWirelessRedstoneTransmitterScreen(transmitter);
				}
				
				return InteractionResult.SUCCESS;
			}
		}
		
		return InteractionResult.PASS;
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
	{
		super.neighborChanged(state, level, pos, blockIn, fromPos, isMoving);
		updatePower(level, pos);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		super.onPlace(state, level, pos, oldState, isMoving);
		updatePower(level, pos);
	}
	
	public void updatePower(Level level, BlockPos pos)
	{
		if(!level.isClientSide)
		{
			BlockState state = level.getBlockState(pos);
			int powerInt = level.getBestNeighborSignal(pos);
			level.setBlockAndUpdate(pos, state.setValue(POWER, powerInt));
			
			if(state.getValue(POWERED) != powerInt > 0)
				level.setBlockAndUpdate(pos, state.setValue(POWERED, powerInt > 0));
			else level.sendBlockUpdated(pos, state, state, 2);
		}
	}
	
	@Override
	public void animateTick(BlockState stateIn, Level level, BlockPos pos, Random rand)
	{
		if(rand.nextInt(15) < stateIn.getValue(POWER))
		{
			BlockUtil.spawnParticlesAroundSolidBlock(level, pos, () -> DustParticleOptions.REDSTONE);
		}
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
		builder.add(POWER);
		builder.add(POWERED);
	}
}
