package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.tileentities.TreeBlockEntity;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.advancedtrees.trees.info.TreeBlockSet;
import paulevs.bhcore.util.ToolsUtil;

import java.util.Random;

public class ATDynamicLogBlock extends ATLoglikeBlock {
	private TreeBlockSet treeInfo;
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
	
	public void setTree(TreeBlockSet treeInfo) {
		this.treeInfo = treeInfo;
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random random) {
		TreeContext.getInstance().updateAndGrow(level, x, y, z, treeInfo);
	}
	
	@Override
	public int getAge(BlockState state) {
		return age;
	}
	
	public TreeBlockEntity createEntity(Level level, int x, int y, int z, TreeBehaviour behaviour) {
		TreeBlockEntity entity = new TreeBlockEntity(behaviour.getAge(level.rand), behaviour);
		level.setBlockEntity(x, y, z, entity);
		return entity;
	}
}
