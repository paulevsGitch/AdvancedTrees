package paulevs.advancedtrees.trees.behaviour.configurable;

public class TreeConfig {
	private int minAge = 4;
	private int maxAge = 8;
	private int branchingStart = 2;
	private int branchingStep = 1;
	private BranchingRule branchingRule = BranchingRule.SINGLE_ROTATE_CW;
	
	public TreeConfig setMinAge(int minAge) {
		this.minAge = minAge;
		return this;
	}
	
	public TreeConfig setMaxAge(int maxAge) {
		this.maxAge = maxAge;
		return this;
	}
	
	public TreeConfig setBranchingStart(int branchingStart) {
		this.branchingStart = branchingStart;
		return this;
	}
	
	public TreeConfig setBranchingStep(int branchingStep) {
		this.branchingStep = branchingStep;
		return this;
	}
	
	public TreeConfig setBranchingRule(BranchingRule branchingRule) {
		this.branchingRule = branchingRule;
		return this;
	}
	
	public int getMinAge() {
		return minAge;
	}
	
	public int getMaxAge() {
		return maxAge;
	}
	
	public int getBranchingStart() {
		return branchingStart;
	}
	
	public int getBranchingStep() {
		return branchingStep;
	}
	
	public BranchingRule getBranchingRule() {
		return branchingRule;
	}
}
