package client.modules.player;

import client.management.module.Mod;
import client.management.module.Module;
import client.management.value.Val;

@Mod
public class SpeedMine
  extends Module
{
  @Val(min=1.0D, max=10.0D, increment=1.0D)
  public static double amplifier = 1.0D;
}
