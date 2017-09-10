package client.modules.render;

import client.eventapi.*;
import client.eventapi.events.*;
import client.management.module.*;
import net.minecraft.client.renderer.*;

@Mod
public class MotionBlur extends Module
{
    @EventTarget
    private void onTick(final TickEvent event) {
        final EntityRenderer er = this.mc.entityRenderer;
        this.mc.entityRenderer.useShader = true;
        if (this.mc.theWorld != null && (this.mc.entityRenderer.theShaderGroup == null || !this.mc.entityRenderer.theShaderGroup.getShaderGroupName().contains("phosphor"))) {
            if (er.theShaderGroup != null) {
                er.theShaderGroup.deleteShaderGroup();
            }
            er.loadShader(EntityRenderer.shaderResourceLocations[12]);
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.entityRenderer.useShader = true;
        if (this.mc.entityRenderer.theShaderGroup != null) {
            this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
        }
        super.onDisable();
    }
}
