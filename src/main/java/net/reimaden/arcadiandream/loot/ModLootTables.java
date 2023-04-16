package net.reimaden.arcadiandream.loot;

import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModLootTables {

    public static final Identifier ABANDONED_SHRINE_TREASURE_CHEST = createChestLootTable("abandoned_shrine_treasure");
    public static final Identifier ABANDONED_SHRINE_DANMAKU_CHEST = createChestLootTable("abandoned_shrine_danmaku");
    public static final Identifier ABANDONED_SHRINE_SUPPLY_CHEST = createChestLootTable("abandoned_shrine_supply");

    private static Identifier createLootTable(String id) {
        return new Identifier(ArcadianDream.MOD_ID, id);
    }

    private static Identifier createChestLootTable(String name) {
        return createLootTable("chests/" + name);
    }
}
