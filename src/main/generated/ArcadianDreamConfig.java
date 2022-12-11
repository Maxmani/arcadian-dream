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

