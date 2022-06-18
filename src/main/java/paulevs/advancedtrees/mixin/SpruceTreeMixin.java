package paulevs.advancedtrees.mixin;

import net.minecraft.level.Level;
import net.minecraft.level.structure.SpruceTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.advancedtrees.trees.VanillaTrees;

import java.util.Random;

@Mixin(SpruceTree.class)
public class SpruceTreeMixin {
	@Inject(
		method = "generate(Lnet/minecraft/level/Level;Ljava/util/Random;III)Z",
		at = @At("HEAD"),
		cancellable = true
	)
	private void at_replaceTree(Level level, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue(VanillaTrees.SPRUCE_STRUCTURE.generate(level, random, x, y, z));
	}
}
