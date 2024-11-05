package zoid.orbital_cannon;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrbitalCannon implements ModInitializer {
	public static final String MOD_ID = "orbital_cannon";
	public static final java.util.logging.Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.warning("KABOOM");
	}
}