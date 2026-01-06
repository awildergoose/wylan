package awildgoose.wylan.mixin.client;

import awildgoose.wylan.WylanMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.*;

import java.util.Map;
import java.util.UUID;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin {
	@Shadow @Final Map<UUID, LerpingBossEvent> events;
	@Shadow @Final private Minecraft minecraft;
	@Shadow abstract void drawBar(GuiGraphics guiGraphics, int i, int j, BossEvent bossEvent);

	@Unique private final ResourceLocation BOSS_EMPTY = ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																							  "boss_empty");
	@Unique private final ResourceLocation BOSS_FILL = ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																							 "boss_fill");

	/**
	 * @author awildergoose
	 * @reason Custom overlay
	 */
	@Overwrite
	public void render(GuiGraphics guiGraphics) {
		if (!this.events.isEmpty()) {
			guiGraphics.nextStratum();
			ProfilerFiller profilerFiller = Profiler.get();
			profilerFiller.push("bossHealth");

			int guiWidth = guiGraphics.guiWidth();
			int barY = 12;

			for (LerpingBossEvent lerpingBossEvent : this.events.values()) {
				int barX = guiWidth / 2 - 91;
				Component component = lerpingBossEvent.getName();
				int textWidth = this.minecraft.font.width(component);
				int x = guiWidth / 2 - textWidth / 2;
				int y = barY - 9;

				final String ZELDER_BOSS_NAME = "ZELDERONIAN: THE OIL BATHER";

				if (component.getString().equals(ZELDER_BOSS_NAME)) {
					long time = System.currentTimeMillis();
					double angle = time / 200.0;

					int radius = 10;
					int centerX = guiGraphics.guiWidth() / 2 - 127;
					int centerY = 32;

					int ourX = centerX + (int)(Math.cos(angle) * radius);
					int ourY = centerY + (int)(Math.sin(angle) * radius);

					int l = (int)(lerpingBossEvent.getProgress() * 250.0f);

					guiGraphics.drawStringWithBackdrop(this.minecraft.font, Component.literal("ZELDERONIAN"), ourX,
													   ourY - 10, -1, 0xFF0000FF);
					guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
										   BOSS_EMPTY, ourX, ourY, 256, 16);
					guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
										   BOSS_FILL, 256, 16, 0, 0,
											ourX + 3, ourY + 1,
										   l, 10);
				} else {
					this.drawBar(guiGraphics, barX, barY, lerpingBossEvent);
					guiGraphics.drawString(this.minecraft.font, component, x, y, -1);
				}

				barY += 10 + 9;

				if (barY >= guiGraphics.guiHeight() / 3) {
					break;
				}
			}

			profilerFiller.pop();
		}
	}
}
