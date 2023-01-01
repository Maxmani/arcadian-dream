/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class ModEvents {

    public static void register() {
        ServerPlayerEvents.COPY_FROM.register(new ModPlayerEventCopyFrom());
    }
}
