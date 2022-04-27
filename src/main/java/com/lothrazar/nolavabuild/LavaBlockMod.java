package com.lothrazar.nolavabuild;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

@Mod(modid = LavaBlockMod.MODID, certificateFingerprint = "@FINGERPRINT@")
public class LavaBlockMod {

  public static final String MODID = "nolavabuild";
  //  private static final Logger LOGGER = LogManager.getLogger();

  @EventHandler
  public void onPreInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    IBlockState current = event.getWorld().getBlockState(event.getPos().offset(event.getFace()));
    if ((current.getBlock() == FluidRegistry.WATER
        || current.getBlock() == FluidRegistry.WATER)
        && current.getValue(BlockLiquid.LEVEL) == 0) {
      event.setCanceled(true);
    }
  }
  @SubscribeEvent
  public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    IBlockState current = event.getWorld().getBlockState(event.getPos().offset(event.getFace()));
    if ((current.getBlock() == FluidRegistry.LAVA
        || current.getBlock() == FluidRegistry.LAVA)
        && current.getValue(BlockLiquid.LEVEL) == 0) {
      event.setCanceled(true);
    }
  }

  @EventHandler
  public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
    // https://tutorials.darkhax.net/tutorials/jar_signing/
    String source = (event.getSource() == null) ? "" : event.getSource().getName() + " ";
    String msg = MODID + "Invalid fingerprint detected! The file " + source + "may have been tampered with. This version will NOT be supported by the author!";
    System.out.println(msg);
  }
}
