package paulevs.advancedtrees.mixin;

import net.minecraft.level.biome.Biome;
import net.minecraft.level.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.advancedtrees.trees.structure.TreeStructures;

import java.util.Random;

@Mixin(Biome.class)
public class BiomeMixin {
	@Inject(
		method = "getTree(Ljava/util/Random;)Lnet/minecraft/level/structure/Structure;",
		at = @At("HEAD"),
		cancellable = true
	)
	private void at_replaceTree(Random random, CallbackInfoReturnable<Structure> info) {
		info.setReturnValue(TreeStructures.OAK_TREE);
	}
}
