package zoid.orbital_cannon.damage_types;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import zoid.orbital_cannon.OrbitalCannon;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> OBLITERATION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
            Identifier.of(OrbitalCannon.MOD_ID, "obliteration"));

    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }

    public static void initialize() {
    }
}
