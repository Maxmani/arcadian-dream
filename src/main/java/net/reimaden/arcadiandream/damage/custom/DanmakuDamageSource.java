/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.damage.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.text.Text;

import javax.annotation.Nullable;

public class DanmakuDamageSource extends EntityDamageSource {

    @Nullable
    private final Entity attacker;

    public DanmakuDamageSource(String name, Entity source, @Nullable Entity attacker) {
        super(name, source);
        this.attacker = attacker;
    }

    @Override
    @Nullable
    public Entity getSource() {
        return this.source;
    }

    @Override
    @Nullable
    public Entity getAttacker() {
        return this.attacker;
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        Text text = this.attacker == null ? this.source.getDisplayName() : this.attacker.getDisplayName();
        String string = "death.attack." + this.name;
        String string2 = string + ".player";
        if (this.attacker == null) {
            return Text.translatable(string, entity.getDisplayName(), text);
        }
        return Text.translatable(string2, entity.getDisplayName(), text);
    }
}
