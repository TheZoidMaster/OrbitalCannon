package zoid.orbital_cannon.util;

import java.util.List;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeAccess;
import zoid.orbital_cannon.blocks.ModBlocks;
import zoid.orbital_cannon.damage_types.ModDamageTypes;

public class ExplosionHandler {
    private final int radius;
    private final World world;

    public ExplosionHandler(int radius, World world) {
        this.radius = radius;
        this.world = world;
    }

    public void explode(BlockPos pos) {
        if (!(world instanceof ServerWorld))
            return;

        int radiusSquared = radius * radius;
        Mutable blockPos = new Mutable();
        Box box = new Box(pos).expand(radius);
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box, e -> e.isAlive());

        for (LivingEntity entity : entities) {
            entity.damage(ModDamageTypes.of(world, ModDamageTypes.OBLITERATION), 500);
        }

        for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++) {
            for (int y = pos.getY() - radius; y <= pos.getY() + radius; y++) {
                for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++) {
                    int dx = x - pos.getX();
                    int dy = y - pos.getY();
                    int dz = z - pos.getZ();

                    int distanceSquared = dx * dx + dy * dy + dz * dz;

                    if (distanceSquared <= radiusSquared + (int) (Math.random() * 4 - 2)) {
                        blockPos.set(x, y, z);
                        world.removeBlock(blockPos, false);
                    }
                }
            }
        }

        int radius2 = radius + 2;
        int radius2Squared = radius2 * radius2;

        for (int x = pos.getX() - radius2; x <= pos.getX() + radius2; x++) {
            for (int y = pos.getY() - radius2; y <= pos.getY() + radius2; y++) {
                for (int z = pos.getZ() - radius2; z <= pos.getZ() + radius2; z++) {
                    int dx = x - pos.getX();
                    int dy = y - pos.getY();
                    int dz = z - pos.getZ();

                    int distanceSquared = dx * dx + dy * dy + dz * dz;

                    if (distanceSquared <= radius2Squared + (int) (Math.random() * 10 - 5)) {
                        blockPos.set(x, y, z);
                        if (world.getBlockState(blockPos).isAir() &&
                                world.getBlockState(blockPos.down()).isSolidBlock(world, blockPos) &&
                                Math.random() < 0.1) {
                            world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
                        }
                        if (world.getBlockState(blockPos).isAir() &&
                                world.getBlockState(blockPos.down()).isSolidBlock(world, blockPos) &&
                                Math.random() < 0.1) {
                            world.setBlockState(blockPos, ModBlocks.DEBRIS.getDefaultState());
                        }
                        if (world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                            world.setBlockState(blockPos, ModBlocks.BURNT_BLOCK.getDefaultState());
                        }
                        if (world.getBlockState(blockPos).isSolidBlock(world, blockPos) && Math.random() < 0.2) {
                            world.setBlockState(blockPos, Blocks.SMOOTH_BASALT.getDefaultState());
                        }
                        if (world.getBlockState(blockPos).isSolidBlock(world, blockPos) && Math.random() < 0.2) {
                            world.setBlockState(blockPos, Blocks.MAGMA_BLOCK.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}
