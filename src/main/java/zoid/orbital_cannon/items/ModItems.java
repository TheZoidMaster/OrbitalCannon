package zoid.orbital_cannon.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zoid.orbital_cannon.OrbitalCannon;

public class ModItems {
    public static final Item ATTUNED_CRYSTAL = register(
            new Item(new Item.Settings()),
            "attuned_crystal");

    public static final Item CANNON_REMOTE = register(
            new CannonRemoteItem(new Item.Settings().maxCount(1)),
            "cannon_remote");

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(OrbitalCannon.MOD_ID, id);
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);
        return registeredItem;
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(ModItems.ATTUNED_CRYSTAL));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.CANNON_REMOTE));
    }
}
