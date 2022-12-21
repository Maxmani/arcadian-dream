package net.reimaden.arcadiandream.config;

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
    private final Option<java.lang.Boolean> mochiHammerOptions_lowViolence = this.optionForKey(new Option.Key("mochiHammerOptions.lowViolence"));
    private final Option<java.lang.Integer> danmakuCooldownMultiplier = this.optionForKey(new Option.Key("danmakuCooldownMultiplier"));

    private ArcadianDreamConfig() {
        super(net.reimaden.arcadiandream.config.ModConfigModel.class);
    }

    public static ArcadianDreamConfig createAndLoad() {
        var wrapper = new ArcadianDreamConfig();
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
    public final MochiHammerOptions_ mochiHammerOptions = new MochiHammerOptions_();
    public class MochiHammerOptions_ implements MochiHammerOptions {
        public boolean lowViolence() {
            return mochiHammerOptions_lowViolence.value();
        }

        public void lowViolence(boolean value) {
            mochiHammerOptions_lowViolence.set(value);
        }

    }
    public int danmakuCooldownMultiplier() {
        return danmakuCooldownMultiplier.value();
    }

    public void danmakuCooldownMultiplier(int value) {
        danmakuCooldownMultiplier.set(value);
    }


    public interface ChiselOptions {
        boolean canUse();
        void canUse(boolean value);
        int maxDistance();
        void maxDistance(int value);
    }
    public interface MochiHammerOptions {
        boolean lowViolence();
        void lowViolence(boolean value);
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

}
