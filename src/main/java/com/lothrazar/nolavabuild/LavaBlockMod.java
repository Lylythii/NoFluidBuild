package com.lothrazar.nolavabuild;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;

@Mod(LavaBlockMod.MODID)
public class LavaBlockMod {

  public static final String MODID = "nolavabuild";

  public LavaBlockMod() {
    //only for server starting
    MinecraftForge.EVENT_BUS.register(this);
    //    ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));
  }

  @SubscribeEvent
  public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    BlockState current = event.getWorld().getBlockState(
        event.getPos().offset(event.getFace()));
    if (current.getBlock() == Blocks.LAVA
        && current.getFluidState().isSource()) {
      event.setCanceled(true);
    }
  }
  //  @SubscribeEvent
  //  public void onFluidPlaceBlockEvent(BlockEvent.EntityPlaceEvent event) {
  //    System.out.println("BlockEvent. bbb  " + event.getPlacedBlock());
  //    System.out.println("BlockEvent. eee " + event.getEntity());
  //    if (event.getEntity() instanceof FallingBlockEntity) {
  //      event.setCanceled(true);
  //    }
  //  }

  @SubscribeEvent
  public static void onFingerprintViolation(FMLFingerprintViolationEvent event) {
    // https://tutorials.darkhax.net/tutorials/jar_signing/
    String source = (event.getSource() == null) ? "" : event.getSource().getName() + " ";
    String msg = MODID + "Invalid fingerprint detected! The file " + source + "may have been tampered with. This version will NOT be supported by the author!";
    //    System.out.println(msg);
  }
}
