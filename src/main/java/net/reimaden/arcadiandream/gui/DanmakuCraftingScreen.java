/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class DanmakuCraftingScreen extends CottonInventoryScreen<DanmakuCraftingGuiDescription> {

    public DanmakuCraftingScreen(DanmakuCraftingGuiDescription description, PlayerInventory inventory, Text title) {
        super(description, inventory, title);
    }
}
