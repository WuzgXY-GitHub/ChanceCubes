package chanceCubes.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemSilkTouchPendant extends BaseChanceCubesItem
{
	public String itemNameID = "silk_Touch_Pendant";

	public ItemSilkTouchPendant()
	{
		super((new Item.Builder()).maxStackSize(1), "silk_Touch_Pendant");
		super.addLore("Use this pendant to retrieve Chance Cubes");
		super.addLore("Player must hold this in hand to get the cube!");
	}

	@OnlyIn(Dist.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}
