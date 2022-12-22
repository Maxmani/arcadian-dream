/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModCriterion extends AbstractCriterion<ModCriterion.Conditions> {

    private final Identifier ID;

    public ModCriterion(String name) {
        ID = new Identifier(ArcadianDream.MOD_ID, name);
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new ModCriterion.Conditions(ID, playerPredicate);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        super.trigger(player, conditions -> true);
    }

    public static class Conditions extends AbstractCriterionConditions {

        public Conditions(Identifier id, EntityPredicate.Extended entity) {
            super(id, entity);
        }
    }
}
