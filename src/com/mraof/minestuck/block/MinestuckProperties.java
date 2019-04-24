package com.mraof.minestuck.block;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;

/**
 * Minestuck version of {@link net.minecraft.state.properties.BlockStateProperties}
 */
public class MinestuckProperties
{
	public static final BooleanProperty MAIN = BooleanProperty.create("main");
	public static final BooleanProperty HAS_CARD = BooleanProperty.create("has_card");
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
	public static final BooleanProperty BROKEN = BooleanProperty.create("broken");
	public static final BooleanProperty ALPHA = BooleanProperty.create("alpha");
	public static final BooleanProperty OMEGA = BooleanProperty.create("omega");
	public static final BooleanProperty RED = BooleanProperty.create("red");
	public static final BooleanProperty GREEN = BooleanProperty.create("green");
	public static final BooleanProperty BLUE = BooleanProperty.create("blue");
	
	public static final IntegerProperty COUNT_0_2 = IntegerProperty.create("count", 0, 2);
	
	public static final EnumProperty<DowelType> DOWEL = EnumProperty.create("dowel", DowelType.class, DowelType.DOWEL, DowelType.CARVED_DOWEL);
	public static final EnumProperty<DowelType> DOWEL_OR_NONE = EnumProperty.create("dowel", DowelType.class);
}