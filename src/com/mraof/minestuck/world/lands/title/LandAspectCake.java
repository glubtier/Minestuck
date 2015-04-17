package com.mraof.minestuck.world.lands.title;

import com.mraof.minestuck.world.lands.decorator.CakeDecorator;
import com.mraof.minestuck.world.lands.gen.ChunkProviderLands;

public class LandAspectCake extends TitleAspect
{
	
	@Override
	public String getPrimaryName()
	{
		return "Cake";
	}
	
	@Override
	public String[] getNames()
	{
		return new String[] {"cake", "dessert"};
	}
	
	@Override
	protected void prepareChunkProvider(ChunkProviderLands chunkProvider)
	{
		if(chunkProvider.decorators != null)
		{
			chunkProvider.decorators.add(new CakeDecorator());
			chunkProvider.sortDecorators();
		}
		
	}
	
}