/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockCactus
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 */
package client.modules.misc;

import client.eventapi.EventTarget;
import client.eventapi.events.BoundingBoxEvent;
import client.management.module.Mod;
import client.management.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@Mod
public class AntiCactus
extends Module {
    @EventTarget
    private void onBoundingBox(BoundingBoxEvent event) {
        if (event.block instanceof BlockCactus) {
            event.boundingBox = new AxisAlignedBB((double)event.pos.getX(), (double)event.pos.getY(), (double)event.pos.getZ(), (double)(event.pos.getX() + 1), (double)(event.pos.getY() + 1), (double)(event.pos.getZ() + 1));
        }
    }
}

