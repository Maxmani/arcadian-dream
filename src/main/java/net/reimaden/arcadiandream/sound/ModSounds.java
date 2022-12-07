package net.reimaden.arcadiandream.sound;

import net.reimaden.arcadiandream.ArcadianDream;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    // SFX
    public static final SoundEvent ENTITY_DANMAKU_FIRE = registerSoundEvent("entity.danmaku.fire");
    public static final SoundEvent ENTITY_DANMAKU_HIT = registerSoundEvent("entity.danmaku.hit");
    public static final SoundEvent ITEM_BOMB_ITEM_USE = registerSoundEvent("item.bomb_item.use");
    public static final SoundEvent ITEM_ARMOR_EQUIP_ORDINARY = registerSoundEvent("item.armor.equip_ordinary");
    public static final SoundEvent ITEM_ARMOR_EQUIP_MAKAITE = registerSoundEvent("item.armor.equip_makaite");
    public static final SoundEvent ITEM_EXTEND_ITEM_USE = registerSoundEvent("item.extend_item.use");
    public static final SoundEvent BLOCK_ONBASHIRA_ADD_ITEM = registerSoundEvent("block.onbashira.add_item");
    public static final SoundEvent BLOCK_RITUAL_SHRINE_USE = registerSoundEvent("block.ritual_shrine.use");

    // Music
    public static final SoundEvent MUSIC_DISC_FAIRY_PLAYGROUND = registerSoundEvent("music_disc.fairy_playground");
    public static final SoundEvent MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN = registerSoundEvent("music_disc.the_shrine_long_forgotten");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ArcadianDream.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering sounds for " + ArcadianDream.MOD_ID);
    }
}
