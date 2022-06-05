package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.tileentities.TreeTileEntity;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.advancedtrees.trees.info.TreeInfo;
import paulevs.bhcore.util.ToolsUtil;

import java.util.Random;

public class ATDynamicLogBlock extends ATLoglikeBlock {
	private TreeBehaviour behaviour;
	private TreeInfo treeInfo;
	private final int age;
	
	public ATDynamicLogBlock(Identifier identifier, int age) {
		this(identifier, Material.WOOD, age);
		ToolsUtil.setAxe(this, 0);
	}
	
	public ATDynamicLogBlock(Identifier identifier, Material material, int age) {
		super(identifier, material);
		setTicksRandomly(true);
		this.age = age;
	}
	
	public void setTree(TreeBehaviour behaviour, TreeInfo treeInfo) {
		this.behaviour = behaviour;
		this.treeInfo = treeInfo;
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random random) {
		TreeContext treeContext = TreeContext.getInstance();
		treeContext.update(level, x, y, z, treeInfo);
		if (treeContext.isValid()) {
			behaviour.grow(treeContext);
			treeContext.restoreEntity();
		}
	}
	
	@Override
	public int getAge(BlockState state) {
		return age;
	}
	
	public void createEntity(Level level, int x, int y, int z) {
		int age = behaviour.getAge(level.rand);
		TreeTileEntity treeTileEntity = new TreeTileEntity(age);
		level.setTileEntity(x, y, z, treeTileEntity);
	}
}
