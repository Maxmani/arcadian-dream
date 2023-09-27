package net.reimaden.arcadiandream.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ArcadianDreamConfig extends ConfigWrapper<net.reimaden.arcadiandream.config.ModConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Boolean> chiselOptions_canUse = this.optionForKey(this.keys.chiselOptions_canUse);
    private final Option<java.lang.Integer> chiselOptions_maxDistance = this.optionForKey(this.keys.chiselOptions_maxDistance);
    private final Option<java.lang.Boolean> nueTridentOptions_canHaveImpaling = this.optionForKey(this.keys.nueTridentOptions_canHaveImpaling);
    private final Option<java.lang.Integer> hisouSwordOptions_minHeightForPeaches = this.optionForKey(this.keys.hisouSwordOptions_minHeightForPeaches);
    private final Option<java.lang.Boolean> hisouSwordOptions_canDisableShields = this.optionForKey(this.keys.hisouSwordOptions_canDisableShields);
    private final Option<java.lang.Boolean> mochiMalletOptions_lowViolence = this.optionForKey(this.keys.mochiMalletOptions_lowViolence);
    private final Option<java.lang.Boolean> houraiElixirOptions_canDrink = this.optionForKey(this.keys.houraiElixirOptions_canDrink);
    private final Option<java.lang.Boolean> houraiElixirOptions_nerfElixir = this.optionForKey(this.keys.houraiElixirOptions_nerfElixir);
    private final Option<java.lang.Boolean> houraiElixirOptions_allowDying = this.optionForKey(this.keys.houraiElixirOptions_allowDying);
    private final Option<java.lang.Integer> houraiElixirOptions_elixirFatigueThreshold = this.optionForKey(this.keys.houraiElixirOptions_elixirFatigueThreshold);
    private final Option<java.lang.Double> foldingChairOptions_knockbackStrength = this.optionForKey(this.keys.foldingChairOptions_knockbackStrength);
    private final Option<java.lang.Boolean> icicleSwordOptions_meltsInstantly = this.optionForKey(this.keys.icicleSwordOptions_meltsInstantly);
    private final Option<java.lang.Boolean> fairyCharmOptions_canPreventSpawning = this.optionForKey(this.keys.fairyCharmOptions_canPreventSpawning);
    private final Option<java.lang.Double> fairyCharmOptions_distance = this.optionForKey(this.keys.fairyCharmOptions_distance);
    private final Option<java.lang.Integer> danmakuCooldownMultiplier = this.optionForKey(this.keys.danmakuCooldownMultiplier);
    private final Option<java.lang.Float> danmakuDamageMultiplier = this.optionForKey(this.keys.danmakuDamageMultiplier);
    private final Option<java.lang.Boolean> cooldownPerBulletType = this.optionForKey(this.keys.cooldownPerBulletType);
    private final Option<java.lang.Boolean> fairyOptions_spawnFairies = this.optionForKey(this.keys.fairyOptions_spawnFairies);
    private final Option<java.lang.Boolean> fairyOptions_spawnSunflowerFairies = this.optionForKey(this.keys.fairyOptions_spawnSunflowerFairies);
    private final Option<java.lang.Boolean> fairyOptions_spawnIceFairies = this.optionForKey(this.keys.fairyOptions_spawnIceFairies);

    private ArcadianDreamConfig() {
        super(net.reimaden.arcadiandream.config.ModConfigModel.class);
    }

    private ArcadianDreamConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(net.reimaden.arcadiandream.config.ModConfigModel.class, janksonBuilder);
    }

    public static ArcadianDreamConfig createAndLoad() {
        var wrapper = new ArcadianDreamConfig();
        wrapper.load();
        return wrapper;
    }

    public static ArcadianDreamConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new ArcadianDreamConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public final ChiselOptions_ chiselOptions = new ChiselOptions_();
    public class ChiselOptions_ implements ChiselOptions {
        public boolean canUse() {
            return chiselOptions_canUse.value();
        }

        public void canUse(boolean value) {
            chiselOptions_canUse.set(value);
        }

        public int maxDistance() {
            return chiselOptions_maxDistance.value();
        }

        public void maxDistance(int value) {
            chiselOptions_maxDistance.set(value);
        }

    }
    public final NueTridentOptions_ nueTridentOptions = new NueTridentOptions_();
    public class NueTridentOptions_ implements NueTridentOptions {
        public boolean canHaveImpaling() {
            return nueTridentOptions_canHaveImpaling.value();
        }

        public void canHaveImpaling(boolean value) {
            nueTridentOptions_canHaveImpaling.set(value);
        }

    }
    public final HisouSwordOptions_ hisouSwordOptions = new HisouSwordOptions_();
    public class HisouSwordOptions_ implements HisouSwordOptions {
        public int minHeightForPeaches() {
            return hisouSwordOptions_minHeightForPeaches.value();
        }

        public void minHeightForPeaches(int value) {
            hisouSwordOptions_minHeightForPeaches.set(value);
        }

        public boolean canDisableShields() {
            return hisouSwordOptions_canDisableShields.value();
        }

        public void canDisableShields(boolean value) {
            hisouSwordOptions_canDisableShields.set(value);
        }

    }
    public final MochiMalletOptions_ mochiMalletOptions = new MochiMalletOptions_();
    public class MochiMalletOptions_ implements MochiMalletOptions {
        public boolean lowViolence() {
            return mochiMalletOptions_lowViolence.value();
        }

        public void lowViolence(boolean value) {
            mochiMalletOptions_lowViolence.set(value);
        }

    }
    public final HouraiElixirOptions_ houraiElixirOptions = new HouraiElixirOptions_();
    public class HouraiElixirOptions_ implements HouraiElixirOptions {
        public boolean canDrink() {
            return houraiElixirOptions_canDrink.value();
        }

        public void canDrink(boolean value) {
            houraiElixirOptions_canDrink.set(value);
        }

        public boolean nerfElixir() {
            return houraiElixirOptions_nerfElixir.value();
        }

        public void nerfElixir(boolean value) {
            houraiElixirOptions_nerfElixir.set(value);
        }

        public boolean allowDying() {
            return houraiElixirOptions_allowDying.value();
        }

        public void allowDying(boolean value) {
            houraiElixirOptions_allowDying.set(value);
        }

        public int elixirFatigueThreshold() {
            return houraiElixirOptions_elixirFatigueThreshold.value();
        }

        public void elixirFatigueThreshold(int value) {
            houraiElixirOptions_elixirFatigueThreshold.set(value);
        }

    }
    public final FoldingChairOptions_ foldingChairOptions = new FoldingChairOptions_();
    public class FoldingChairOptions_ implements FoldingChairOptions {
        public double knockbackStrength() {
            return foldingChairOptions_knockbackStrength.value();
        }

        public void knockbackStrength(double value) {
            foldingChairOptions_knockbackStrength.set(value);
        }

    }
    public final IcicleSwordOptions_ icicleSwordOptions = new IcicleSwordOptions_();
    public class IcicleSwordOptions_ implements IcicleSwordOptions {
        public boolean meltsInstantly() {
            return icicleSwordOptions_meltsInstantly.value();
        }

        public void meltsInstantly(boolean value) {
            icicleSwordOptions_meltsInstantly.set(value);
        }

    }
    public final FairyCharmOptions_ fairyCharmOptions = new FairyCharmOptions_();
    public class FairyCharmOptions_ implements FairyCharmOptions {
        public boolean canPreventSpawning() {
            return fairyCharmOptions_canPreventSpawning.value();
        }

        public void canPreventSpawning(boolean value) {
            fairyCharmOptions_canPreventSpawning.set(value);
        }

        public double distance() {
            return fairyCharmOptions_distance.value();
        }

        public void distance(double value) {
            fairyCharmOptions_distance.set(value);
        }

    }
    public int danmakuCooldownMultiplier() {
        return danmakuCooldownMultiplier.value();
    }

    public void danmakuCooldownMultiplier(int value) {
        danmakuCooldownMultiplier.set(value);
    }

    public float danmakuDamageMultiplier() {
        return danmakuDamageMultiplier.value();
    }

    public void danmakuDamageMultiplier(float value) {
        danmakuDamageMultiplier.set(value);
    }

    public boolean cooldownPerBulletType() {
        return cooldownPerBulletType.value();
    }

    public void cooldownPerBulletType(boolean value) {
        cooldownPerBulletType.set(value);
    }

    public final FairyOptions_ fairyOptions = new FairyOptions_();
    public class FairyOptions_ implements FairyOptions {
        public boolean spawnFairies() {
            return fairyOptions_spawnFairies.value();
        }

        public void spawnFairies(boolean value) {
            fairyOptions_spawnFairies.set(value);
        }

        public boolean spawnSunflowerFairies() {
            return fairyOptions_spawnSunflowerFairies.value();
        }

        public void spawnSunflowerFairies(boolean value) {
            fairyOptions_spawnSunflowerFairies.set(value);
        }

        public boolean spawnIceFairies() {
            return fairyOptions_spawnIceFairies.value();
        }

        public void spawnIceFairies(boolean value) {
            fairyOptions_spawnIceFairies.set(value);
        }

    }
    public interface ChiselOptions {
        boolean canUse();
        void canUse(boolean value);
        int maxDistance();
        void maxDistance(int value);
    }
    public interface NueTridentOptions {
        boolean canHaveImpaling();
        void canHaveImpaling(boolean value);
    }
    public interface HisouSwordOptions {
        int minHeightForPeaches();
        void minHeightForPeaches(int value);
        boolean canDisableShields();
        void canDisableShields(boolean value);
    }
    public interface MochiMalletOptions {
        boolean lowViolence();
        void lowViolence(boolean value);
    }
    public interface HouraiElixirOptions {
        boolean canDrink();
        void canDrink(boolean value);
        boolean nerfElixir();
        void nerfElixir(boolean value);
        boolean allowDying();
        void allowDying(boolean value);
        int elixirFatigueThreshold();
        void elixirFatigueThreshold(int value);
    }
    public interface FoldingChairOptions {
        double knockbackStrength();
        void knockbackStrength(double value);
    }
    public interface IcicleSwordOptions {
        boolean meltsInstantly();
        void meltsInstantly(boolean value);
    }
    public interface FairyCharmOptions {
        boolean canPreventSpawning();
        void canPreventSpawning(boolean value);
        double distance();
        void distance(double value);
    }
    public interface FairyOptions {
        boolean spawnFairies();
        void spawnFairies(boolean value);
        boolean spawnSunflowerFairies();
        void spawnSunflowerFairies(boolean value);
        boolean spawnIceFairies();
        void spawnIceFairies(boolean value);
    }
    public static class Keys {
        public final Option.Key chiselOptions_canUse = new Option.Key("chiselOptions.canUse");
        public final Option.Key chiselOptions_maxDistance = new Option.Key("chiselOptions.maxDistance");
        public final Option.Key nueTridentOptions_canHaveImpaling = new Option.Key("nueTridentOptions.canHaveImpaling");
        public final Option.Key hisouSwordOptions_minHeightForPeaches = new Option.Key("hisouSwordOptions.minHeightForPeaches");
        public final Option.Key hisouSwordOptions_canDisableShields = new Option.Key("hisouSwordOptions.canDisableShields");
        public final Option.Key mochiMalletOptions_lowViolence = new Option.Key("mochiMalletOptions.lowViolence");
        public final Option.Key houraiElixirOptions_canDrink = new Option.Key("houraiElixirOptions.canDrink");
        public final Option.Key houraiElixirOptions_nerfElixir = new Option.Key("houraiElixirOptions.nerfElixir");
        public final Option.Key houraiElixirOptions_allowDying = new Option.Key("houraiElixirOptions.allowDying");
        public final Option.Key houraiElixirOptions_elixirFatigueThreshold = new Option.Key("houraiElixirOptions.elixirFatigueThreshold");
        public final Option.Key foldingChairOptions_knockbackStrength = new Option.Key("foldingChairOptions.knockbackStrength");
        public final Option.Key icicleSwordOptions_meltsInstantly = new Option.Key("icicleSwordOptions.meltsInstantly");
        public final Option.Key fairyCharmOptions_canPreventSpawning = new Option.Key("fairyCharmOptions.canPreventSpawning");
        public final Option.Key fairyCharmOptions_distance = new Option.Key("fairyCharmOptions.distance");
        public final Option.Key danmakuCooldownMultiplier = new Option.Key("danmakuCooldownMultiplier");
        public final Option.Key danmakuDamageMultiplier = new Option.Key("danmakuDamageMultiplier");
        public final Option.Key cooldownPerBulletType = new Option.Key("cooldownPerBulletType");
        public final Option.Key fairyOptions_spawnFairies = new Option.Key("fairyOptions.spawnFairies");
        public final Option.Key fairyOptions_spawnSunflowerFairies = new Option.Key("fairyOptions.spawnSunflowerFairies");
        public final Option.Key fairyOptions_spawnIceFairies = new Option.Key("fairyOptions.spawnIceFairies");
    }
}

