package virtuoel.kanostestground;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;

@Mod(modid = KanosTestground.MOD_ID, version = MinecraftForge.MC_VERSION + "-@VERSION@", certificateFingerprint = "@FINGERPRINT@")
public class KanosTestground
{
	public static final String MOD_ID = "kanostestground";
	
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	@Mod.EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event)
	{
		LOGGER.error("Expecting signature {}, however there is no signature matching that description. The file {} may have been tampered with. This version will NOT be supported by the author!", event.getExpectedFingerprint(), event.getSource().getName());
	}
}
