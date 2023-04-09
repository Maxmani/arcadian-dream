/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import org.jetbrains.annotations.Nullable;

public class ModDamageSources {

    public static final RegistryKey<DamageType> DANMAKU = registerKey("danmaku");
    public static final RegistryKey<DamageType> DANMAKU_SHARP = registerKey("danmaku_sharp");

    public static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        damageTypeRegisterable.register(ModDamageSources.DANMAKU, new DamageType("danmaku", DamageScaling.NEVER, 0.1f));
        damageTypeRegisterable.register(ModDamageSources.DANMAKU_SHARP, new DamageType("danmakuSharp", DamageScaling.NEVER, 0.1f));
    }

    private static RegistryKey<DamageType> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(ArcadianDream.MOD_ID, name));
    }

    @SuppressWarnings("unused")
    private static DamageSource create(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }

    @SuppressWarnings("unused")
    private static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), attacker);
    }

    private static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), source, attacker);
    }

    public static DamageSource danmaku(World world, Entity source, @Nullable Entity attacker) {
        return create(world, DANMAKU, source, attacker);
    }

    public static DamageSource danmakuSharp(World world, Entity source, @Nullable Entity attacker) {
        return create(world, DANMAKU_SHARP, source, attacker);
    }
}
