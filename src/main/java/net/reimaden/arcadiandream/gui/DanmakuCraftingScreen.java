/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class DanmakuCraftingScreen extends HandledScreen<DanmakuCraftingScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(ArcadianDream.MOD_ID, "textures/gui/danmaku_crafting_table.png");

    public DanmakuCraftingScreen(DanmakuCraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        backgroundWidth = 176;
        backgroundHeight = 187;
        playerInventoryTitleY = backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderIcons(context, x, y);
    }

    private void renderIcons(DrawContext context, int x, int y) {
        // Set some helper variables
        final int topY = 32;
        final int bottomY = 56;

        final int[][] iconPositions = {
                {15, topY, 176, 0},
                {39, topY, 176, 16},
                {27, bottomY, 176, 32},
                {80, topY, 176, 48},
                {68, bottomY, 176, 64},
                {92, bottomY, 176, 80}
        };

        for (int i = 0; i < iconPositions.length; i++) {
            int[] position = iconPositions[i];
            int slotIndex = i;
            if (i > 1) {
                slotIndex++;
            }
            if (slotIsEmpty(slotIndex)) {
                context.drawTexture(TEXTURE, x + position[0], y + position[1], position[2], position[3], 16, 16);
            }
        }
    }

    private boolean slotIsEmpty(int index) {
        return !handler.hasStack(handler.getSlot(index));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
