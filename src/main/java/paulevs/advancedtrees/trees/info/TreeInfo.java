package paulevs.advancedtrees.trees.info;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockBase;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.blocks.ATDynamicLogBlock;
import paulevs.advancedtrees.blocks.ATLeavesBlock;
import paulevs.advancedtrees.blocks.ATSaplingBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSaplingBlock;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.blocks.ATStemBlock;
import paulevs.advancedtrees.trees.structure.AdvancedTreeStructure;

import java.util.ArrayList;
import java.util.List;

public class TreeInfo {
	private final AdvancedTreeStructure structure;
	private final List<ItemConvertible> availableItems;
	private final ATSpawnerSaplingBlock spawnerSapling;
	private final ATDynamicLogBlock logDynamic;
	private final ATStaticLogBlock logStatic;
	private final ATLeavesBlock leaves;
	private final ATSaplingBlock sapling;
	private final ATStemBlock stem;
	private final BlockBase log;
	private final Identifier id;
	
	public TreeInfo(Identifier id, ATStaticLogBlock logStatic, ATDynamicLogBlock logDynamic, BlockBase log, ATStemBlock stem, ATLeavesBlock leaves, ATSaplingBlock sapling, ATSpawnerSaplingBlock spawnerSapling, AdvancedTreeStructure structure) {
		this.spawnerSapling = spawnerSapling;
		this.logDynamic = logDynamic;
		this.structure = structure;
		this.logStatic = logStatic;
		this.sapling = sapling;
		this.leaves = leaves;
		this.stem = stem;
		this.log = log;
		this.id = id;
		
		List<ItemConvertible> items = new ArrayList<>();
		if (sapling != null) items.add(sapling);
		if (spawnerSapling != null) items.add(spawnerSapling);
		if (stem != null) items.add(stem);
		if (log != null) items.add((ItemConvertible) log);
		if (leaves != null) items.add(leaves);
		availableItems = ImmutableList.copyOf(items);
	}
	
	public AdvancedTreeStructure getStructure() {
		return structure;
	}
	
	public ATStaticLogBlock getLogStatic() {
		return logStatic;
	}
	
	public ATDynamicLogBlock getLogDynamic() {
		return logDynamic;
	}
	
	public ATLeavesBlock getLeaves() {
		return leaves;
	}
	
	public ATSaplingBlock getSapling() {
		return sapling;
	}
	
	public ATSpawnerSaplingBlock getSpawnerSapling() {
		return spawnerSapling;
	}
	
	public ATStemBlock getStem() {
		return stem;
	}
	
	public BlockBase getLog() {
		return log;
	}
	
	public List<ItemConvertible> getAvailableItems() {
		return availableItems;
	}
	
	public Identifier getID() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof TreeInfo)) return false;
		TreeInfo info = (TreeInfo) obj;
		return info.id.equals(id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
