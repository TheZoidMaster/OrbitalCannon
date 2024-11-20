package zoid.orbital_cannon;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import zoid.orbital_cannon.blocks.ModBlocks;
import zoid.orbital_cannon.damage_types.ModDamageTypes;
import zoid.orbital_cannon.items.ModItems;
import zoid.orbital_cannon.sounds.ModSounds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.architectury.registry.ReloadListenerRegistry;
import mod.chloeprime.aaaparticles.client.loader.EffekAssetLoader;

public class OrbitalCannon implements ModInitializer {
    public static final String MOD_ID = "orbital_cannon";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.warn("KABOOM");

        ReloadListenerRegistry.register(ResourceType.CLIENT_RESOURCES, new EffekAssetLoader(),
                Identifier.of(MOD_ID, "effeks"));

        ModItems.initialize();
        ModBlocks.initialize();
        ModDamageTypes.initialize();
        ModSounds.initialize();
    }
}