package zoid.orbital_cannon.items;

import astrinox.stellum.handlers.explosion.ExplosionHandler;
import astrinox.stellum.handlers.schedule.ScheduleHandler;
import astrinox.stellum.handlers.schedule.Task;
import astrinox.stellum.handlers.schedule.TaskType;
import astrinox.stellum.registry.BurnMapRegistry;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import zoid.orbital_cannon.OrbitalCannon;
import zoid.orbital_cannon.sounds.ModSounds;

public class CannonRemoteItem extends Item {
    public CannonRemoteItem(Settings settings) {
        super(settings);
    }

    private static final ParticleEmitterInfo LASER = new ParticleEmitterInfo(
            Identifier.of(OrbitalCannon.MOD_ID, "laser"));

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        HitResult hitResult = user.raycast(100.0, 0.0F, false);
        if (hitResult instanceof BlockHitResult) {
            BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
            if (world.getBlockState(pos).isOf(Blocks.AIR) || !world.isSkyVisible(pos.up())) {
                return TypedActionResult.fail(itemStack);
            }

            if (!world.isClient()) {
                world.getEntitiesByClass(PlayerEntity.class,
                        user.getBoundingBox().expand(10.0), entity -> true)
                        .forEach(entity -> {
                            world.playSound(null, entity.getBlockPos(), ModSounds.CANNON_CHARGE,
                                    SoundCategory.PLAYERS,
                                    1.0F,
                                    1.0F);
                        });
            }

            ScheduleHandler.addTask(Identifier.of(OrbitalCannon.MOD_ID, "charge_cannon"), new Task(() -> {
                if (!world.isClient()) {
                    world.getEntitiesByClass(PlayerEntity.class,
                            user.getBoundingBox().expand(10.0), entity -> true)
                            .forEach(entity -> {
                                world.playSound(null, entity.getBlockPos(), ModSounds.CANNON_FIRE,
                                        SoundCategory.PLAYERS,
                                        2.0F,
                                        1.0F);
                            });

                }
                AAALevel.addParticle(world, false,
                        LASER.clone().position(pos.getX() + 0.5d, pos.getY() + 1.0d, pos.getZ() +
                                0.5d)
                                .rotation(new Vec2f(0.0f, (float) (Math.random() * 360.0))));
            }, TaskType.ONCE, 120));

            ScheduleHandler.addTask(Identifier.of(OrbitalCannon.MOD_ID, "fire_cannon"), new Task(() -> {
                ExplosionHandler explosionHandler = new ExplosionHandler()
                        .setPos(pos)
                        .setBreakBlocks(true)
                        .setBurnBlocks(true)
                        .setSize(16)
                        .setBurnSize(10)
                        .setNoiseMultiplier(50)
                        .setNoiseScale(0.3)
                        .setBurnDoFire(true)
                        .setDoScreenshake(true)
                        .setScreenshakeDurationMs(1000)
                        .setScreenshakeIntensity(1.0f)
                        .setDoScreenshakeFade(true)
                        .setBurnMap(BurnMapRegistry.getBurnMap(Identifier.of("stellum", "cannon")))
                        .setDamage(500)
                        .setHurtEntities(true);
                explosionHandler.trigger(world);
            }, TaskType.ONCE, 127));
        }

        ((PlayerEntity) user).getItemCooldownManager().set(this, 300);
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
