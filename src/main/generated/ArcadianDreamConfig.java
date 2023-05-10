package net.reimaden.arcadiandream.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ArcadianDreamConfig extends ConfigWrapper<net.reimaden.arcadiandream.config.ModConfigModel> {

    private final Option<java.lang.Boolean> chiselOptions_canUse = this.optionForKey(new Option.Key("chiselOptions.canUse"));
    private final Option<java.lang.Integer> chiselOptions_maxDistance = this.optionForKey(new Option.Key("chiselOptions.maxDistance"));
    private final Option<java.lang.Boolean> nueTridentOptions_canHaveImpaling = this.optionForKey(new Option.Key("nueTridentOptions.canHaveImpaling"));
    private final Option<java.lang.Integer> hisouSwordOptions_minHeightForPeaches = this.optionForKey(new Option.Key("hisouSwordOptions.minHeightForPeaches"));
    private final Option<java.lang.Boolean> hisouSwordOptions_canDisableShields = this.optionForKey(new Option.Key("hisouSwordOptions.canDisableShields"));
    private final Option<java.lang.Boolean> mochiMalletOptions_lowViolence = this.optionForKey(new Option.Key("mochiMalletOptions.lowViolence"));
    private final Option<java.lang.Boolean> houraiElixirOptions_canDrink = this.optionForKey(new Option.Key("houraiElixirOptions.canDrink"));
    private final Option<java.lang.Boolean> houraiElixirOptions_nerfElixir = this.optionForKey(new Option.Key("houraiElixirOptions.nerfElixir"));
    private final Option<java.lang.Boolean> houraiElixirOptions_allowDying = this.optionForKey(new Option.Key("houraiElixirOptions.allowDying"));
    private final Option<java.lang.Integer> houraiElixirOptions_elixirFatigueThreshold = this.optionForKey(new Option.Key("houraiElixirOptions.elixirFatigueThreshold"));
    private final Option<java.lang.Double> foldingChairOptions_knockbackStrength = this.optionForKey(new Option.Key("foldingChairOptions.knockbackStrength"));
    private final Option<java.lang.Integer> danmakuCooldownMultiplier = this.optionForKey(new Option.Key("danmakuCooldownMultiplier"));
    private final Option<java.lang.Float> danmakuDamageMultiplier = this.optionForKey(new Option.Key("danmakuDamageMultiplier"));
    private final Option<java.lang.Boolean> cooldownPerBulletType = this.optionForKey(new Option.Key("cooldownPerBulletType"));
    private final Option<java.lang.Boolean> fairyOptions_spawnFairies = this.optionForKey(new Option.Key("fairyOptions.spawnFairies"));
    private final Option<java.lang.Boolean> fairyOptions_spawnSunflowerFairies = this.optionForKey(new Option.Key("fairyOptions.spawnSunflowerFairies"));

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

    }

    public interface FairyOptions {
        boolean spawnFairies();
        void spawnFairies(boolean value);
        boolean spawnSunflowerFairies();
        void spawnSunflowerFairies(boolean value);
    }
    public interface FoldingChairOptions {
        double knockbackStrength();
        void knockbackStrength(double value);
    }
    public interface HisouSwordOptions {
        int minHeightForPeaches();
        void minHeightForPeaches(int value);
        boolean canDisableShields();
        void canDisableShields(boolean value);
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
    public interface MochiMalletOptions {
        boolean lowViolence();
        void lowViolence(boolean value);
    }
    public interface NueTridentOptions {
        boolean canHaveImpaling();
        void canHaveImpaling(boolean value);
    }
    public interface ChiselOptions {
        boolean canUse();
        void canUse(boolean value);
        int maxDistance();
        void maxDistance(int value);
    }

}

