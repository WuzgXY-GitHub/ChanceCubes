package chanceCubes.rewards.giantRewards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.IChanceCubeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.CustomEntry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ChunkReverserReward implements IChanceCubeReward
{

	private List<Entry<Block, Block>> swappedMap = new ArrayList<Entry<Block, Block>>();

	public ChunkReverserReward()
	{
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.STONE, Blocks.DIRT));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.DIRT, Blocks.COBBLESTONE));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.GRASS, Blocks.STONE));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.GRAVEL, Blocks.SAND));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.SAND, Blocks.GRAVEL));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.IRON_ORE, Blocks.GOLD_ORE));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.COAL_ORE, Blocks.DIAMOND_ORE));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.DIAMOND_ORE, Blocks.COAL_ORE));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.GOLD_ORE, Blocks.IRON_ORE));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.LAVA, Blocks.WATER));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.WATER, Blocks.LAVA));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.OAK_LOG, Blocks.OAK_LEAVES));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.OAK_LEAVES, Blocks.OAK_LOG));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.ACACIA_LOG, Blocks.ACACIA_LEAVES));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.JUNGLE_LOG, Blocks.JUNGLE_LEAVES));
		swappedMap.add(new CustomEntry<Block, Block>(Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG));
	}

	@Override
	public void trigger(World world, BlockPos pos, EntityPlayer player)
	{
		player.sendMessage(new TextComponentString("Initiating Block Inverter"));
		List<OffsetBlock> blocks = new ArrayList<OffsetBlock>();
		int delay = 0;
		for(int yy = 256; yy > 0; yy--)
		{
			int xx = 0, zz = 0, dx = 0, dy = -1;
			int t = 16;
			int maxI = t * t;

			for(int i = 0; i < maxI; i++)
			{
				if((-16 / 2 <= xx) && (xx <= 16 / 2) && (-16 / 2 <= zz) && (zz <= 16 / 2))
				{
					Block blockAt = world.getBlockState(new BlockPos(pos.getX() + xx, yy, pos.getZ() + zz)).getBlock();
					Block toSwapTo = null;
					for(Entry<Block, Block> blockSwap : swappedMap)
					{
						if(blockSwap.getKey().equals(blockAt))
						{
							toSwapTo = blockSwap.getValue();
						}
					}
					if(toSwapTo != null)
					{
						blocks.add(new OffsetBlock(xx, yy - pos.getY(), zz, toSwapTo, false, delay / 5));
						delay++;
					}
				}

				if((xx == zz) || ((xx < 0) && (xx == -zz)) || ((xx > 0) && (xx == 1 - zz)))
				{
					t = dx;
					dx = -dy;
					dy = t;
				}
				xx += dx;
				zz += dy;
			}
		}

		player.sendMessage(new TextComponentString("Inverting " + blocks.size() + " Blocks... May take a minute..."));
		for(OffsetBlock b : blocks)
			b.spawnInWorld(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public int getChanceValue()
	{
		return 0;
	}

	@Override
	public String getName()
	{
		return CCubesCore.MODID + ":Chuck_Reverse";
	}

}