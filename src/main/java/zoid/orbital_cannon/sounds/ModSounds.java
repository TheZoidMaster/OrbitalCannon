package zoid.orbital_cannon.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import zoid.orbital_cannon.OrbitalCannon;

public class ModSounds {
    public static SoundEvent CANNON_CHARGE = register("cannon_charge");
    public static SoundEvent CANNON_FIRE = register("cannon_fire");

    public static SoundEvent register(String id) {
        Identifier identifier = Identifier.of(OrbitalCannon.MOD_ID + ":" + id);
        SoundEvent soundEvent = SoundEvent.of(identifier);
        Registry.register(Registries.SOUND_EVENT, identifier, soundEvent);
        return soundEvent;
    }

    public static void initialize() {
    }
}
