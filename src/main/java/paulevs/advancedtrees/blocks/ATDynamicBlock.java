package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.trees.TreeBehaviour;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.bhcore.util.ToolsUtil;

import java.util.Random;

public class ATDynamicBlock extends ATLoglikeBlock {
	private final TreeBehaviour behaviour;
	private final ATStaticLogBlock staticLogBlock;
	private final int age;
	
	public ATDynamicBlock(Identifier identifier, int age, TreeBehaviour behaviour, ATStaticLogBlock staticLogBlock) {
		this(identifier, Material.WOOD, age, behaviour, staticLogBlock);
		ToolsUtil.setAxe(this, 0);
	}
	
	public ATDynamicBlock(Identifier identifier, Material material, int age, TreeBehaviour behaviour, ATStaticLogBlock staticLogBlock) {
		super(identifier, material);
		setTicksRandomly(true);
		this.behaviour = behaviour;
		this.age = age;
		this.staticLogBlock = staticLogBlock;
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random random) {
		TreeContext treeContext = TreeContext.getInstance();
		treeContext.update(level, x, y, z, staticLogBlock);
		behaviour.grow(level, treeContext);
	}
	
	@Override
	public int getAge(BlockState state) {
		return age;
	}
}
