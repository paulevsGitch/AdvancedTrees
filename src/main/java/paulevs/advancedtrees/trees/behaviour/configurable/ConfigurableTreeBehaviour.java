package paulevs.advancedtrees.trees.behaviour.configurable;

import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.bhcore.util.MathUtil;

import java.util.Random;

public class ConfigurableTreeBehaviour implements TreeBehaviour {
	private final TreeConfig config;
	
	public ConfigurableTreeBehaviour(TreeConfig config) {
		this.config = config;
	}
	
	@Override
	public void grow(TreeContext context) {
	
	}
	
	@Override
	public int getAge(Random random) {
		return MathUtil.randomRange(config.getMinAge(), config.getMaxAge(), random);
	}
}
