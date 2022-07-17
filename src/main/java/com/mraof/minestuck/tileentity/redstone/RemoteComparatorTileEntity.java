package com.mraof.minestuck.tileentity.redstone;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.redstone.RemoteComparatorBlock;
import com.mraof.minestuck.tileentity.MSTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RemoteComparatorTileEntity extends BlockEntity
{
	private int tickCycle;
	
	public RemoteComparatorTileEntity(BlockPos pos, BlockState state)
	{
		super(MSTileEntityTypes.REMOTE_COMPARATOR.get(), pos, state);
	}
	
	public static void serverTick(Level level, BlockPos pos, BlockState state, RemoteComparatorTileEntity blockEntity)
	{
		if(blockEntity.tickCycle >= MinestuckConfig.SERVER.puzzleBlockTickRate.get())
		{
			blockEntity.sendUpdate();
			blockEntity.tickCycle = 0;
		}
		blockEntity.tickCycle++;
	}
	
	private void sendUpdate()
	{
		if(level != null)
		{
			boolean shouldBePowered = RemoteComparatorBlock.isMatch(level, worldPosition);
			
			if(getBlockState().getValue(RemoteComparatorBlock.POWERED) != shouldBePowered)
				level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(RemoteComparatorBlock.POWERED, shouldBePowered));
		}
	}
	
	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		tickCycle = compound.getInt("tickCycle");
	}
	
	@Override
	public void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		
		compound.putInt("tickCycle", tickCycle);
	}
}