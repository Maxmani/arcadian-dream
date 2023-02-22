/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class ArcadianDreamPreLaunch implements PreLaunchEntrypoint {

    @Override
    public void onPreLaunch() {
        MixinExtrasBootstrap.init();
    }
}
