package com.lothrazar.nolavabuild;
import com.lothrazar.nolavabuild.setup.ClientProxy;
import com.lothrazar.nolavabuild.setup.ConfigHandler;
import com.lothrazar.nolavabuild.setup.IProxy;
import com.lothrazar.nolavabuild.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
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
    // Register the setup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    //only for server starting
    MinecraftForge.EVENT_BUS.register(this);
    ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));
  }

  private void setup(final FMLCommonSetupEvent event) {

  }

  @SubscribeEvent
  public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    //
    LOGGER.info("RightClickBlock"  );
    BlockState current = event.getWorld().getBlockState(event.getPos());
    if (current.getBlock() == Blocks.LAVA
        && current.getFluidState().isSource()) {
      event.setCanceled(true);
    }
  }

  @SubscribeEvent
  public void onFluidPlaceBlockEvent(BlockEvent.FluidPlaceBlockEvent event) {
    LOGGER.info("BlockEvent.FluidPlaceBlockEvent " + event.getNewState());
    if(event.getNewState().getBlock()==Blocks.COBBLESTONE){
      event.setCanceled(true );
    }
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onEntityPlaceEvent(BlockEvent.EntityPlaceEvent event) {
    if(event.getEntity() instanceof PlayerEntity==false){
      return;
    }
    PlayerEntity player=(PlayerEntity)event.getEntity();
    LOGGER.info("EntityPlaceEvent ");

    // do something when the server starts
    BlockState current = event.getBlockSnapshot().getReplacedBlock();//world.getBlockState(pos);
    LOGGER.info("HELLO from server starting" + event.gets);
    LOGGER.info("HELLO from server starting" + event.getBlockSnapshot().getReplacedBlock().getBlock());
    if (current.getBlock() == Blocks.LAVA
        && current.getFluidState().isSource()) {
      event.setCanceled(true);
    }
  }

  @SubscribeEvent
  public static void onFingerprintViolation(FMLFingerprintViolationEvent event) {
    // https://tutorials.darkhax.net/tutorials/jar_signing/
    String source = (event.getSource() == null) ? "" : event.getSource().getName() + " ";
    String msg = MODID + "Invalid fingerprint detected! The file " + source + "may have been tampered with. This version will NOT be supported by the author!";
    System.out.println(msg);
  }
}
