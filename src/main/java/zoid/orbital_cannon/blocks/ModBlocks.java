package zoid.orbital_cannon.blocks;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import zoid.orbital_cannon.OrbitalCannon;

public class ModBlocks {
    public static final Block BURNT_BLOCK = register("burnt_block",
            new Block(Block.Settings.copy(Blocks.DRIPSTONE_BLOCK)));

    public static final Block DEBRIS = register("debris",
            new Block(Block.Settings.copy(Blocks.POINTED_DRIPSTONE).nonOpaque().noCollision()) {
                @Override
                public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
                    return world.getBlockState(pos.down()).isSolidBlock(world, pos);
                }

                @Override
                public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos,
                        ShapeContext context) {
                    return VoxelShapes.cuboid(0.1f, 0f, 0.1f, 0.9f, 0.5f, 0.9f);
                }
            });

    private static <T extends Block> T register(String path, T block) {
        Registry.register(Registries.BLOCK, Identifier.of(OrbitalCannon.MOD_ID, path), block);
        Registry.register(Registries.ITEM, Identifier.of(OrbitalCannon.MOD_ID, path),
                new BlockItem(block, new Item.Settings()));
        return block;
    }

    public static void initialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEBRIS, RenderLayer.getCutout());
    }
}
