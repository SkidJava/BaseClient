package client.modules.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import client.Client;
import client.eventapi.EventTarget;
import client.eventapi.events.NametagRenderEvent;
import client.eventapi.events.Render3DEvent;
import client.management.friend.FriendManager;
import client.management.module.Mod;
import client.management.module.Module;
import client.management.option.Op;
import client.management.value.Val;
import client.util.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

@Mod
public class Tags extends Module
{
    @Val(min = 1.0, max = 20.0, increment = 1.0)
    private double distance;
    @Val(min = 0.0, max = 2.0, increment = 0.1)
    private double scale;
    @Op
    private boolean armor;
    private Character formatChar;
    public static Map<EntityLivingBase, double[]> entityPositions;

    static {
        Tags.entityPositions = new HashMap<EntityLivingBase, double[]>();
    }

    public Tags() {
        this.distance = 8.0;
        this.scale = 1.0;
        this.armor = true;
        this.formatChar = new Character('§');
    }

    @EventTarget(4)
    private void onRender3DEvent(final Render3DEvent event) {
        GlStateManager.pushMatrix();
        for (Object o : this.mc.theWorld.loadedEntityList) {
        	Entity ent = (Entity)o;
            if (ent == this.mc.thePlayer) continue;
            if (ent instanceof EntityPlayer) {
                final double posX = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * event.partialTicks - RenderManager.renderPosX;
                final double posY = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * event.partialTicks - RenderManager.renderPosY + ent.height + 0.5;
                final double posZ = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * event.partialTicks - RenderManager.renderPosZ;
                String str = ent.getDisplayName().getFormattedText();
                if (FriendManager.isFriend(ent.getName())) {
                    str = str.replace(ent.getName(), this.formatChar + "b" + FriendManager.getAliasName(ent.getName()));
                }
                String colorString = this.formatChar.toString();
                final double health = MathUtils.round(((EntityPlayer)ent).getHealth(), 2);
                if (health >= 12.0) {
                    colorString = String.valueOf(colorString) + "2";
                }
                else if (health >= 4.0) {
                    colorString = String.valueOf(colorString) + "6";
                }
                else {
                    colorString = String.valueOf(colorString) + "4";
                }
                str = String.valueOf(str) + " " + colorString + Math.round(health / 2);
                final float dist = this.mc.thePlayer.getDistanceToEntity(ent);
                float scale = 0.02672f;
                final float factor = (float)((dist <= this.distance) ? (this.distance * this.scale) : (dist * this.scale));
                scale *= factor;
                GlStateManager.pushMatrix();
                GlStateManager.disableDepth();
                GlStateManager.translate(posX, posY, posZ);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(-this.mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(this.mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                GlStateManager.scale(-scale, -scale, scale);
                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                final Tessellator tessellator = Tessellator.getInstance();
                final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
                GlStateManager.disableTexture2D();
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                final int stringWidth = Client.font.getStringWidth(str) / 2;
                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glLineWidth(1.0E-6f);
                GL11.glBegin(3);
                GL11.glVertex2d((double)(-stringWidth - 2), -0.8);
                GL11.glVertex2d((double)(-stringWidth - 2), 8.8);
                GL11.glVertex2d((double)(-stringWidth - 2), 8.8);
                GL11.glVertex2d((double)(stringWidth + 2), 8.8);
                GL11.glVertex2d((double)(stringWidth + 2), 8.8);
                GL11.glVertex2d((double)(stringWidth + 2), -0.8);
                GL11.glVertex2d((double)(stringWidth + 2), -0.8);
                GL11.glVertex2d((double)(-stringWidth - 2), -0.8);
                GL11.glEnd();
                worldRenderer.pos((double) (-stringWidth - 2), (double) (-0.8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
				worldRenderer.pos((double) (-stringWidth - 2), (double) (8.8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
				worldRenderer.pos((double) (stringWidth + 2), (double) (8.8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
				worldRenderer.pos((double) (stringWidth + 2), (double) (-0.8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();

                tessellator.draw();
                GlStateManager.enableTexture2D();
                Client.font.drawString(str, -Client.font.getStringWidth(str) / 2, 0, -1);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                if (this.armor && ent instanceof EntityPlayer) {
                    final List<ItemStack> itemsToRender = new ArrayList<ItemStack>();
                    for (int i = 0; i < 5; ++i) {
                        final ItemStack stack = ((EntityPlayer)ent).getEquipmentInSlot(i);
                        if (stack != null) {
                            itemsToRender.add(stack);
                        }
                    }
                    int x = -(itemsToRender.size() * 8);
                    final Iterator<ItemStack> iterator2 = itemsToRender.iterator();
                    while (iterator2.hasNext()) {
                        final ItemStack stack = iterator2.next();
                        GlStateManager.disableDepth();
                        RenderHelper.enableGUIStandardItemLighting();
                        this.mc.getRenderItem().zLevel = -100.0f;
                        this.mc.getRenderItem().renderItemIntoGUI(stack, x, -18);
                        this.mc.getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, stack, x, -18);
                        this.mc.getRenderItem().zLevel = 0.0f;
                        RenderHelper.disableStandardItemLighting();
                        GlStateManager.enableDepth();
                        final String text = "";
                        if (stack != null) {
                            int y = 0;
                            final int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
                            final int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
                            final int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
                            if (sLevel > 0) {
                                GL11.glDisable(2896);
                                drawEnchantTag("Sh" + sLevel, x, y);
                                y -= 9;
                            }
                            if (fLevel > 0) {
                                GL11.glDisable(2896);
                                drawEnchantTag("Fir" + fLevel, x, y);
                                y -= 9;
                            }
                            if (kLevel > 0) {
                                GL11.glDisable(2896);
                                drawEnchantTag("Kb" + kLevel, x, y);
                            }
                            else if (stack.getItem() instanceof ItemArmor) {
                                final int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
                                final int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
                                final int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
                                if (pLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("P" + pLevel, x, y);
                                    y -= 9;
                                }
                                if (tLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Th" + tLevel, x, y);
                                    y -= 9;
                                }
                                if (uLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Unb" + uLevel, x, y);
                                }
                            }
                            else if (stack.getItem() instanceof ItemBow) {
                                final int powLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
                                final int punLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
                                final int fireLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
                                if (powLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Pow" + powLevel, x, y);
                                    y -= 9;
                                }
                                if (punLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Pun" + punLevel, x, y);
                                    y -= 9;
                                }
                                if (fireLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Fir" + fireLevel, x, y);
                                }
                            }
                            else if (stack.getRarity() == EnumRarity.EPIC) {
                                drawEnchantTag(this.formatChar + "lGod", x, y);
                            }
                            x += 16;
                        }
                    }
                }
                GlStateManager.popMatrix();
            }
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    private static void drawEnchantTag(final String text, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        x *= (int)1.75;
        y -= 4;
        GL11.glScalef(0.57f, 0.57f, 0.57f);
        Client.font.drawStringWithShadow(text, x, -36 - y, -1286);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    @EventTarget
    private void onNametagRender(final NametagRenderEvent event) {
        event.setCancelled(true);
    }
}
