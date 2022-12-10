package net.reimaden.arcadiandream.config;

import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ArcadianDreamConfig extends ConfigWrapper<net.reimaden.arcadiandream.config.ModConfigModel> {

    private final Option<java.lang.Boolean> canUseChisel = this.optionForKey(new Option.Key("canUseChisel"));
    private final Option<java.lang.Integer> maxChiselDistance = this.optionForKey(new Option.Key("maxChiselDistance"));

    private ArcadianDreamConfig() {
        super(net.reimaden.arcadiandream.config.ModConfigModel.class);
    }

    public static ArcadianDreamConfig createAndLoad() {
        var wrapper = new ArcadianDreamConfig();
        wrapper.load();
        return wrapper;
    }

    public boolean canUseChisel() {
        return canUseChisel.value();
    }

    public void canUseChisel(boolean value) {
        canUseChisel.set(value);
    }

    public int maxChiselDistance() {
        return maxChiselDistance.value();
    }

    public void maxChiselDistance(int value) {
        maxChiselDistance.set(value);
    }




}

