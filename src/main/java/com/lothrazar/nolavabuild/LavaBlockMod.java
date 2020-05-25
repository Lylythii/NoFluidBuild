package com.lothrazar.nolavabuild;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = LavaBlockMod.MODID)
public class LavaBlockMod {

  private String certificateFingerprint = "@FINGERPRINT@";
  //  public static final IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
  public static final String MODID = "nolavabuild";
  private static final Logger LOGGER = LogManager.getLogger();

  public LavaBlockMod() {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void onRightClickBlock(BlockEvent.EntityPlaceEvent event) {
    IBlockState current = event.getBlockSnapshot().getReplacedBlock();
    if (!event.getWorld().isRemote
        && event.getEntity() instanceof EntityPlayer
        && current.getBlock() == Blocks.LAVA) {
      //      System.out.println("? cancel " + current.getValue(BlockLiquid.LEVEL));
      if (current.getValue(BlockLiquid.LEVEL) == 0)
        event.setCanceled(true);
    }
    //    IBlockState current = event.getWorld().getBlockState(
    //        event.getPos().offset(event.getFace()));
    //    && current.getFluidState().isSource()
    //    if (current.getBlock() == Blocks.LAVA) {//  && current.getValue(BlockLiquid.LEVEL) == 0
    //      System.out.println("? build " + current.getValue(BlockLiquid.LEVEL));
    //      if (current.getValue(BlockLiquid.LEVEL) == 0) {
    //        event.setCanceled(true);
    //        return;
    //      }
    //      System.out.println("do not cancel " + current.getValue(BlockLiquid.LEVEL));
    //    }
    //    BlockEvent.EntityPlaceEvent
  }
  //  @SubscribeEvent
  //  public void onFluidPlaceBlockEvent(BlockEvent.FluidPlaceBlockEvent event) {
  //    LOGGER.info("BlockEvent.FluidPlaceBlockEvent " + event.getNewState());
  //    if (event.getNewState().getBlock() == Blocks.COBBLESTONE) {
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
