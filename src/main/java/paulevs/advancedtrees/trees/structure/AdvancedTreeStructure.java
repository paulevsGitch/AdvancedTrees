package paulevs.advancedtrees.trees.structure;

import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.advancedtrees.blocks.ATDynamicLogBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSaplingBlock;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AdvancedTreeStructure extends Structure {
	private static final Direction[] DIRECTIONS = Direction.values();
	private final SpawnChecker checker;
	private final ATDynamicLogBlock block;
	
	public AdvancedTreeStructure(ATDynamicLogBlock block, SpawnChecker checker) {
		this.checker = checker;
		this.block = block;
	}
	
	@Override
	public boolean generate(Level level, Random random, int x, int y, int z) {
		if (!checker.canSpawn(level, x, y, z) && !(BlocksUtil.getBlockState(level, x, y, z).getBlock() instanceof ATSpawnerSaplingBlock)) {
			return false;
		}
		BlocksUtil.setBlockState(level, x, y, z, block.getDefaultState());
		block.createEntity(level, x, y, z);
		
		List<Set<Vec3I>> buffers = new ArrayList<>();
		buffers.add(new HashSet<>());
		buffers.add(new HashSet<>());
		buffers.get(0).add(new Vec3I(x, y, z));
		
		int index = 0;
		boolean run = true;
		Vec3I blockPos = new Vec3I();
		while (run) {
			Set<Vec3I> tips = buffers.get(index & 1);
			Set<Vec3I> newTips = buffers.get((index + 1) & 1);
			index++;
			
			tips.forEach(pos -> {
				block.onScheduledTick(level, pos.x, pos.y, pos.z, level.rand);
				for (Direction dir: DIRECTIONS) {
					if (BlocksUtil.getBlockState(level, blockPos.set(pos).move(dir)).getBlock() == block) {
						newTips.add(blockPos.clone());
					}
				}
			});
			newTips.removeAll(tips);
			tips.clear();
			run = !newTips.isEmpty();
		}
		
		return true;
	}
}
