package com.mraof.minestuck.computer;

import com.mraof.minestuck.tileentity.ComputerTileEntity;
import com.mraof.minestuck.util.Debug;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

class TEComputerReference implements ComputerReference
{
	protected final GlobalPos location;
	
	TEComputerReference(GlobalPos location)
	{
		this.location = location;
	}
	
	static TEComputerReference create(CompoundTag nbt)
	{
		GlobalPos location = GlobalPos.CODEC.parse(NbtOps.INSTANCE, nbt.get("pos")).resultOrPartial(Debug::error).orElse(null);
		return new TEComputerReference(location);
	}
	
	@Override
	public CompoundTag write(CompoundTag nbt)
	{
		nbt.putString("type", "tile_entity");
		GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, location).resultOrPartial(Debug::error).ifPresent(tag -> nbt.put("pos", tag));
		return nbt;
	}
	
	@Override
	public ISburbComputer getComputer(MinecraftServer server)
	{
		Level level = server.getLevel(location.dimension());
		if(level == null)
			return null;
		BlockEntity te = level.getBlockEntity(location.pos());
		if(te instanceof ISburbComputer)
			return (ISburbComputer) te;
		else return null;
	}
	
	@Override
	public boolean matches(ISburbComputer computer)
	{
		if(computer instanceof ComputerTileEntity te)
		{
			return location.dimension() == Objects.requireNonNull(te.getLevel()).dimension() && location.pos().equals(te.getBlockPos());
		} else return false;
	}
	
	@Override
	public boolean isInNether()
	{
		return location.dimension() == Level.NETHER;
	}
	
	@Override
	public GlobalPos getPosForEditmode()
	{
		return location;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		TEComputerReference that = (TEComputerReference) o;
		return location.equals(that.location);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(location);
	}
}