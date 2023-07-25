/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class BulletsCancelledCriterion extends AbstractCriterion<BulletsCancelledCriterion.Conditions> {

    private static final Identifier ID = new Identifier(ArcadianDream.MOD_ID, "bullets_cancelled");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        NumberRange.IntRange intRange = NumberRange.IntRange.fromJson(obj.get("amount"));
        Boolean fromExtend = obj.has("from_extend") ? obj.get("from_extend").getAsBoolean() : null;
        return new Conditions(playerPredicate, intRange, fromExtend);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, int amount, Boolean fromExtend) {
        this.trigger(player, conditions -> conditions.matches(amount, fromExtend));
    }

    public static class Conditions extends AbstractCriterionConditions {

        private final NumberRange.IntRange amount;
        private final Boolean fromExtend;

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("amount", amount.toJson());
            jsonObject.addProperty("from_extend", fromExtend);
            return jsonObject;
        }

        public Conditions(LootContextPredicate entity, NumberRange.IntRange amount, Boolean fromExtend) {
            super(ID, entity);
            this.amount = amount;
            this.fromExtend = fromExtend;
        }

        public static Conditions create() {
            return create(null);
        }

        public static Conditions create(Boolean fromExtend) {
            return new Conditions(LootContextPredicate.EMPTY, NumberRange.IntRange.ANY, fromExtend);
        }

        public static Conditions amount(NumberRange.IntRange amount) {
            return amount(amount, null);
        }

        public static Conditions amount(NumberRange.IntRange amount, Boolean fromExtend) {
            return new Conditions(LootContextPredicate.EMPTY, amount, fromExtend);
        }

        public boolean matches(int amount, Boolean fromExtend) {
            return this.amount.test(amount) && (this.fromExtend == null || this.fromExtend == fromExtend);
        }
    }
}
