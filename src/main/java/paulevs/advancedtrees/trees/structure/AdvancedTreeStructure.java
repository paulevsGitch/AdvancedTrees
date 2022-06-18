package paulevs.advancedtrees.trees.structure;

import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.advancedtrees.blocks.ATDynamicLogBlock;
import paulevs.advancedtrees.blocks.ATSaplingBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSaplingBlock;
import paulevs.advancedtrees.tileentities.TreeBlockEntity;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.advancedtrees.trees.info.TreeBlockSet;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AdvancedTreeStructure extends Structure {
	private static final Direction[] DIRECTIONS = Direction.values();
	private final ATDynamicLogBlock block;
	private final TreeBehaviour behaviour;
	private final SpawnChecker checker;
	
	public AdvancedTreeStructure(TreeBlockSet info, TreeBehaviour behaviour, SpawnChecker checker) {
		this.behaviour = behaviour;
		this.checker = checker;
		this.block = info.getLogDynamic();
		ATSaplingBlock sapling = info.getSapling();
		if (sapling != null) sapling.setBehaviour(behaviour);
		ATSpawnerSaplingBlock spawnerSapling = info.getSpawnerSapling();
		if (spawnerSapling != null) spawnerSapling.setStructure(this);
	}
	
	@Override
	public boolean generate(Level level, Random random, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(level, x, y, z);
		if (!(state.getBlock() instanceof ATSpawnerSaplingBlock)) {
			if (!checker.canSpawn(level, x, y, z, 1) || !state.getMaterial().isReplaceable()) {
				return false;
			}
		}
		
		BlocksUtil.setBlockState(level, x, y, z, block.getDefaultState());
		TreeBlockEntity entity = block.createEntity(level, x, y, z, behaviour);
		
		List<Set<Vec3I>> buffers = new ArrayList<>();
		buffers.add(new HashSet<>());
		buffers.add(new HashSet<>());
		buffers.get(0).add(new Vec3I(x, y, z));
		
		int index = 0;
		boolean run = true;
		Vec3I blockPos = new Vec3I();
		final int maxAge = entity.getMaxAge() + 1;
		for (int age = 0; age <= maxAge && run; age++) {
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
