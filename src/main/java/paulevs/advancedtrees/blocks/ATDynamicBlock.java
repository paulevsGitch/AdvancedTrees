package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.bhcore.util.ToolsUtil;

import java.util.Random;

public class ATDynamicBlock extends ATLoglikeBlock {
	private TreeBehaviour behaviour;
	private final int age;
	
	public ATDynamicBlock(Identifier identifier, int age) {
		this(identifier, Material.WOOD, age);
		ToolsUtil.setAxe(this, 0);
	}
	
	public ATDynamicBlock(Identifier identifier, Material material, int age) {
		super(identifier, material);
		setTicksRandomly(true);
		this.age = age;
	}
	
	public void linkBehaviour(TreeBehaviour behaviour) {
		this.behaviour = behaviour;
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random random) {
		TreeContext treeContext = TreeContext.getInstance();
		treeContext.update(level, x, y, z);
		behaviour.grow(treeContext);
	}
	
	@Override
	public int getAge(BlockState state) {
		return age;
	}
}
