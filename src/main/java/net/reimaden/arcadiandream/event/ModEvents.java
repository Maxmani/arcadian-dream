/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class ModEvents {

    public static void register() {
        ServerPlayerEvents.COPY_FROM.register(new PlayerEventCopyFrom());
        AttackBlockCallback.EVENT.register(new EtherealEvent());
        AttackEntityCallback.EVENT.register(new EtherealEvent());
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(new EtherealEvent());
        ServerLivingEntityEvents.ALLOW_DEATH.register(new ElixirEvent());
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(new IcicleSwordEvent());
    }
}
