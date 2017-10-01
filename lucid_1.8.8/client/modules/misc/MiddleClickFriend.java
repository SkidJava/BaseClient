/*
 * Decompiled with CFR 0_114.
 *
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.MovingObjectPosition
 */
package client.modules.misc;

import client.eventapi.EventTarget;
import client.eventapi.events.MouseEvent;
import client.management.friend.FriendManager;
import client.management.module.Mod;
import client.management.module.Module;
import client.util.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

@Mod
public class MiddleClickFriend
extends Module {
    @EventTarget
    private void onMouseClick(MouseEvent event) {
        if (event.key == 2 && this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit != null && this.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().objectMouseOver.entityHit;
            String name = player.getName();
            if (FriendManager.isFriend(name)) {
                FriendManager.removeFriend(name);
                ChatUtils.sendClientMessage("Removed: " + name);
            } else {
                FriendManager.addFriend(name, name);
                ChatUtils.sendClientMessage("Added: " + name);
            }
        }
    }
}

