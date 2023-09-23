/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.misc;

import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.mixin.SmithingTemplateItemInvoker;

import java.util.List;

public class ModSmithingTemplateItem extends SmithingTemplateItem {

    private static final Formatting TITLE_FORMATTING = Formatting.GRAY;
    private static final Formatting DESCRIPTION_FORMATTING = Formatting.BLUE;
    private static final Text HIHIIROKANE_UPGRADE_TEXT = Text.translatable(Util.createTranslationKey("upgrade",
            new Identifier(ArcadianDream.MOD_ID, "hihiirokane_upgrade"))).formatted(TITLE_FORMATTING);
    private static final Text HIHIIROKANE_UPGRADE_APPLIES_TO_TEXT = Text.translatable(Util.createTranslationKey("item",
            new Identifier(ArcadianDream.MOD_ID, "smithing_template.hihiirokane_upgrade.applies_to"))).formatted(DESCRIPTION_FORMATTING);
    private static final Text HIHIIROKANE_UPGRADE_INGREDIENTS_TEXT = Text.translatable(Util.createTranslationKey("item",
            new Identifier(ArcadianDream.MOD_ID, "smithing_template.hihiirokane_upgrade.ingredients"))).formatted(DESCRIPTION_FORMATTING);
    private static final Text HIHIIROKANE_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item",
            new Identifier(ArcadianDream.MOD_ID, "smithing_template.hihiirokane_upgrade.base_slot_description")));
    private static final Text HIHIIROKANE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item",
            new Identifier(ArcadianDream.MOD_ID, "smithing_template.hihiirokane_upgrade.additions_slot_description")));

    public ModSmithingTemplateItem(Text appliesToText, Text ingredientsText, Text titleText, Text baseSlotDescriptionText,
                                   Text additionsSlotDescriptionText, List<Identifier> emptyBaseSlotTextures, List<Identifier> emptyAdditionsSlotTextures) {
        super(appliesToText, ingredientsText, titleText, baseSlotDescriptionText, additionsSlotDescriptionText, emptyBaseSlotTextures, emptyAdditionsSlotTextures);
    }

    public static SmithingTemplateItem createHihiirokaneUpgrade() {
        return new SmithingTemplateItem(HIHIIROKANE_UPGRADE_APPLIES_TO_TEXT, HIHIIROKANE_UPGRADE_INGREDIENTS_TEXT, HIHIIROKANE_UPGRADE_TEXT,
                HIHIIROKANE_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT, HIHIIROKANE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                SmithingTemplateItemInvoker.arcadiandream$getNetheriteUpgradeEmptyBaseSlotTextures(), SmithingTemplateItemInvoker.arcadiandream$getNetheriteUpgradeEmptyAdditionsSlotTextures());
    }
}
