package com.lothrazar.nolavabuild;
import com.lothrazar.nolavabuild.setup.ClientProxy;
import com.lothrazar.nolavabuild.setup.ConfigHandler;
import com.lothrazar.nolavabuild.setup.IProxy;
import com.lothrazar.nolavabuild.setup.ServerProxy;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("nolavabuild")
public class LavaBlockMod {

  private String certificateFingerprint = "@FINGERPRINT@";
  public static final IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
  public static final String MODID = "nolavabuild";
  private static final Logger LOGGER = LogManager.getLogger();

  public LavaBlockMod() {
    //only for server starting
    MinecraftForge.EVENT_BUS.register(this);
    ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));
  }


  @SubscribeEvent
  public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    //    Blocks.WATER;
    BlockState current = event.getWorld().getBlockState(
        event.getPos().offset(event.getFace()));
    if (current.getBlock() == Blocks.LAVA
        && current.getFluidState().isSource()) {
      event.setCanceled(true);
    }
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
    System.out.println(msg);
  }
}
