/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.reimaden.arcadiandream.ArcadianDream;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    // SFX
    public static final SoundEvent ITEM_BOMB_ITEM_USE = registerSoundEvent("item.bomb_item.use");
    public static final SoundEvent ITEM_ARMOR_EQUIP_ORDINARY = registerSoundEvent("item.armor.equip_ordinary");
    public static final SoundEvent ITEM_ARMOR_EQUIP_MAKAITE = registerSoundEvent("item.armor.equip_makaite");
    public static final SoundEvent ITEM_EXTEND_ITEM_USE = registerSoundEvent("item.extend_item.use");
    public static final SoundEvent ITEM_WALL_PASSING_CHISEL_USE = registerSoundEvent("item.wall_passing_chisel.use");
    public static final SoundEvent ITEM_IBUKI_GOURD_FILL = registerSoundEvent("item.ibuki_gourd.fill");
    public static final SoundEvent ITEM_HEALING_CHARM_USE = registerSoundEvent("item.healing_charm.use");
    public static final SoundEvent ITEM_DEATH_SCYTHE_TELEPORT = registerSoundEvent("item.death_scythe.teleport");
    public static final SoundEvent ITEM_DEATH_SCYTHE_TELEPORT_GENERIC = registerSoundEvent("item.death_scythe.teleport_generic");
    public static final SoundEvent ITEM_HOURAI_ELIXIR_USE = registerSoundEvent("item.hourai_elixir.use");
    public static final SoundEvent ITEM_MIRACLE_MALLET_USE = registerSoundEvent("item.miracle_mallet.use");
    public static final SoundEvent ITEM_GHASTLY_LANTERN_USE = registerSoundEvent("item.ghastly_lantern.use");
    public static final SoundEvent ITEM_FOLDING_CHAIR_HIT = registerSoundEvent("item.folding_chair.hit");
    public static final SoundEvent ITEM_HIHIIROKANE_AUTOSMELT_ON = registerSoundEvent("item.hihiirokane.autosmelt.on");
    public static final SoundEvent ITEM_HIHIIROKANE_AUTOSMELT_OFF = registerSoundEvent("item.hihiirokane.autosmelt.off");
    public static final SoundEvent ITEM_FAIRY_CHARM_USE = registerSoundEvent("item.fairy_charm.use");
    public static final SoundEvent ITEM_ARMOR_EQUIP_HIHIIROKANE = registerSoundEvent("item.armor.equip_hihiirokane");

    public static final SoundEvent BLOCK_ONBASHIRA_ADD_ITEM = registerSoundEvent("block.onbashira.add_item");
    public static final SoundEvent BLOCK_RITUAL_SHRINE_USE = registerSoundEvent("block.ritual_shrine.use");
    public static final SoundEvent BLOCK_ONBASHIRA_CREATE = registerSoundEvent("block.onbashira.create");

    public static final SoundEvent ENTITY_DANMAKU_FIRE = registerSoundEvent("entity.danmaku.fire");
    public static final SoundEvent ENTITY_DANMAKU_HIT = registerSoundEvent("entity.danmaku.hit");
    public static final SoundEvent ENTITY_GENERIC_RESURRECT = registerSoundEvent("entity.generic.resurrect");
    public static final SoundEvent ENTITY_FAIRY_AMBIENT = registerSoundEvent("entity.fairy.ambient");
    public static final SoundEvent ENTITY_FAIRY_HURT = registerSoundEvent("entity.fairy.hurt");
    public static final SoundEvent ENTITY_FAIRY_DEATH = registerSoundEvent("entity.fairy.death");
    public static final SoundEvent ENTITY_VILLAGER_WORK_ANTIQUARIAN = registerSoundEvent("entity.villager.work_antiquarian");

    // Music
    public static final SoundEvent MUSIC_DISC_FAIRY_PLAYGROUND = registerSoundEvent("music_disc.fairy_playground");
    public static final SoundEvent MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN = registerSoundEvent("music_disc.the_shrine_long_forgotten");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ArcadianDream.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering sounds for " + ArcadianDream.MOD_ID);
    }
}
