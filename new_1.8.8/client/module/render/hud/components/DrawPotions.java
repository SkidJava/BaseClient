package client.module.render.hud.components;

import org.lwjgl.opengl.GL11;

import client.event.EventTarget;
import client.event.events.render.EventRender2D;
import client.manager.managers.SettingManager;
import client.module.render.hud.HUD;
import client.utils.ColorUtils;
import client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class DrawPotions extends HUD {

	private ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");

	@EventTarget
	public void onRender(EventRender2D e) {
		if (!client.getManagers().getManager(SettingManager.class).getSetting(HUD.class, "PotionEffect").getBooleanValue()) {

			int x = e.width - 20;
			for (PotionEffect p : Minecraft.getMinecraft().thePlayer.getActivePotionEffects()) {
				Potion potion = Potion.potionTypes[p.getPotionID()];
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				if (potion.hasStatusIcon()) {
					RenderUtils.drawFullCircle(x + 5, e.height - 11, 10, ColorUtils.backColor);
					RenderUtils.drawCircle(x + 5, e.height - 11, 10, ColorUtils.arrayColor);
					GL11.glColor4f(1, 1, 1, 1);

					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.disableLighting();

					int i1 = potion.getStatusIconIndex();
					GL11.glScalef((float) 0.8f, (float) 0.8f, (float) 0.8f);
					GlStateManager.enableBlend();
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					Minecraft.getMinecraft().getTextureManager().bindTexture(inventoryBackground);
					Gui.drawTexturedModalRect1((int) (x * 1.25f) - 2, (int) (e.height * 1.25f) - 23, 0 + i1 % 8 * 18,
							198 + i1 / 8 * 18, 18, 18);
					GL11.glScalef((float) 1.25f, (float) 1.25f, (float) 1.25f);
					GlStateManager.disableBlend();

					int i = p.getDuration() / 20;
					int j = i / 60;
					i = i % 60;
					String time = (String) (i < 10 ? j + ":0" + i : j + ":" + i);

					arraylist.drawStringWithShadow(time, x - arraylist.getStringWidth(time) + 12,
							e.height - arraylist.getHeight() - 2, -1);
					if (p.getAmplifier() != 0)
					arraylist.drawStringWithShadow((p.getAmplifier() + 1) + "",
							x - arraylist.getStringWidth((p.getAmplifier() + 1) + "") + 7,
							e.height - arraylist.getHeight() - 13, -1);
				}

				if (Minecraft.getMinecraft().thePlayer.getActivePotionEffects().size() > 5)
					x -= 90 / (Minecraft.getMinecraft().thePlayer.getActivePotionEffects().size() - 1);
				else
					x -= 24;
			}
		}
	}
}
