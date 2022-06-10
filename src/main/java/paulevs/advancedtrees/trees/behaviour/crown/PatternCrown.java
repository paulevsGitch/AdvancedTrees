package paulevs.advancedtrees.trees.behaviour.crown;

import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.blocks.ATLeavesBlock;
import paulevs.advancedtrees.trees.info.TreeInfo;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.MathUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PatternCrown implements TreeCrown {
	private static final Map<Character, Direction> DEFAULT_MAP = new HashMap<>();
	final List<BlockInfo> blocks = new ArrayList<>();
	
	public PatternCrown(String[][] slices, Vec3I center) {
		this(slices, center, DEFAULT_MAP);
	}
	
	public PatternCrown(String[][] slices, Vec3I center, Map<Character, Direction> directionMap) {
		/*int dxz = slices[0][0].length() >> 1;
		for (int y = 0; y < slices.length; y++) {
			int py = y + yOffset;
			for (int x = 0; x < slices[y].length; x++) {
				int px = x - dxz;
				char[] line = slices[y][x].toCharArray();
				for (int z = 0; z < line.length; z++) {
					int pz = z - dxz;
					Direction dir = directionMap.get(line[z]);
					if (dir != null) {
						blocks.add(new BlockInfo(new Vec3I(px, py, -pz), dir));
						//blocks.add(new BlockInfo(new Vec3I(-px, py, -pz), dir));
					}
				}
			}
		}
		Collections.sort(blocks);*/
		
		final int sizeY = slices.length;
		final int sizeX = slices[0].length;
		final int sizeZ = slices[0][0].length();
		
		List<Vec3I> points = new ArrayList<>();
		points.add(center);
		
		List<Set<Vec3I>> buffers = new ArrayList<>(2);
		buffers.add(new HashSet<>());
		buffers.add(new HashSet<>());
		buffers.get(0).add(center.clone());
		
		int index = 0;
		Vec3I pos = new Vec3I();
		boolean run = true;
		while (run) {
			Set<Vec3I> ends = buffers.get(index);
			index = (index + 1) & 1;
			Set<Vec3I> newEnds = buffers.get(index);
			
			ends.forEach(p -> {
				for (Direction dir: MathUtil.DIRECTIONS) {
					pos.set(p).move(dir);
					if (pos.x >= 0 && pos.y >= 0 && pos.z >= 0 && pos.x < sizeX && pos.y < sizeY && pos.z < sizeZ) {
						if (slices[pos.y][pos.x].charAt(pos.z) == '#') {
							if (!points.contains(pos) && !newEnds.contains(pos)) {
								Vec3I pos2 = pos.clone();
								points.add(pos2);
								newEnds.add(pos2);
								blocks.add(new BlockInfo(pos.clone().subtract(center), dir.getOpposite()));
							}
						}
					}
				}
			});
			ends.clear();
			run = !newEnds.isEmpty();
		}
		
		System.out.println("Size " + blocks.size());
		System.out.println(blocks);
	}
	
	@Override
	public void place(Level level, Vec3I pos, TreeInfo info) {
		Vec3I p = new Vec3I();
		ATLeavesBlock leafBlock = info.getLeaves();
		blocks.forEach(blockInfo -> {
			/*p.set(pos).add(blockInfo.pos).move(blockInfo.dir);
			BlockState state = BlocksUtil.getBlockState(level, p);
			boolean isLog = state.getBlock() instanceof ATLogBlock;
			//System.out.println((state.getBlock() == leafBlock) + " " + isLog);
			//System.out.println(state.getBlock() + " " +  leafBlock);
			//if (state.getBlock() == leafBlock || isLog) {
				p.move(blockInfo.dir, -1);
				if (BlocksUtil.getBlockState(level, p).getMaterial().isReplaceable()) {
					state = leafBlock.getDefaultState().with(ATBlockProperties.CONNECTED, isLog).with(ATBlockProperties.DIRECTION, blockInfo.dir);
					BlocksUtil.setBlockState(level, p, state);
				}
			//}*/
			
			Direction face = blockInfo.dir.getOpposite();
			p.set(pos).add(blockInfo.pos);//.move(face);
			int facing = face.ordinal();
			if (leafBlock.canPlaceAt(level, p.x, p.y, p.z, facing)) {
				BlockState state = leafBlock.getDefaultState();
				if (state.getProperties().contains(ATBlockProperties.DIRECTION)) {
					state = state.with(ATBlockProperties.DIRECTION, blockInfo.dir);
				}
				BlocksUtil.setBlockState(level, p, state);
				leafBlock.onBlockPlaced(level, p.x, p.y, p.z, facing);
			}
		});
	}
	
	record BlockInfo(Vec3I pos, Direction dir) implements Comparable<BlockInfo> {
		@Override
		public int compareTo(BlockInfo info) {
			int l1 = Math.abs(pos.x) + Math.abs(pos.y) + Math.abs(pos.z);
			int l2 = Math.abs(info.pos.x) + Math.abs(info.pos.y) + Math.abs(info.pos.z);
			return Integer.compare(l1, l2);
		}
	}
	
	static {
		DEFAULT_MAP.put('u', Direction.UP);
		DEFAULT_MAP.put('d', Direction.DOWN);
		DEFAULT_MAP.put('n', Direction.NORTH);
		DEFAULT_MAP.put('s', Direction.SOUTH);
		DEFAULT_MAP.put('e', Direction.EAST);
		DEFAULT_MAP.put('w', Direction.WEST);
	}
}
