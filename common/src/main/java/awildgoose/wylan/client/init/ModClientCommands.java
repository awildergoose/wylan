package awildgoose.wylan.client.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.SharedSuggestionProvider;

public class ModClientCommands {
	public static <T extends SharedSuggestionProvider> void init(CommandDispatcher<T> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<T>literal("oilup").executes(context -> {
			ModRendering.lavaTransitionState = ModRendering.LavaTransitionState.LAVA_TO_OIL;
			return 1;
		}));

		dispatcher.register(LiteralArgumentBuilder.<T>literal("oildown").executes(context -> {
			ModRendering.lavaTransitionState = ModRendering.LavaTransitionState.OIL_TO_LAVA;
			return 1;
		}));
	}
}
