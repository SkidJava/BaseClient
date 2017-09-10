
package client.util;

import java.net.UnknownHostException;

import client.eventapi.EventManager;
import client.eventapi.EventTarget;
import client.eventapi.events.Render2DEvent;
import client.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.entity.player.EntityPlayer;

public class NetworkUtils
{
    private OldServerPinger pinger;
    private Minecraft mc;
    private Timer timer;
    private long ping;
    private long lastTime;
    private int prevDebugFPS;
    public long updatedPing;
    
    public NetworkUtils() {
        this.pinger = new OldServerPinger();
        this.mc = Minecraft.getMinecraft();
        this.timer = new Timer();
        EventManager.register(this);
        final PingThread pingThread = new PingThread();
        pingThread.start();
    }
    
    public long getPing() {
        return this.ping;
    }
    
    public int getPlayerPing(final String name) {
        final EntityPlayer player = this.mc.theWorld.getPlayerEntityByName(name);
        if (player instanceof EntityOtherPlayerMP) {
            return ((EntityOtherPlayerMP)player).playerInfo.getResponseTime();
        }
        return 0;
    }
    
    @EventTarget
    private void on2DRender(final Render2DEvent event) {
        if (Minecraft.debugFPS != this.prevDebugFPS) {
            this.prevDebugFPS = Minecraft.debugFPS;
            this.ping = this.updatedPing;
        }
    }
    
    private class PingThread extends Thread
    {
        @Override
        public void run() {
            while (true) {
                if (NetworkUtils.this.mc.getCurrentServerData() != null) {
                    try {
                        if (NetworkUtils.this.mc.currentScreen == null || !(NetworkUtils.this.mc.currentScreen instanceof GuiMultiplayer)) {
                            NetworkUtils.this.pinger.ping(NetworkUtils.this.mc.getCurrentServerData());
                        }
                    }
                    catch (UnknownHostException ex) {}
                }
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

