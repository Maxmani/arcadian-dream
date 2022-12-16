package net.reimaden.arcadiandream.entity.client.armor;

import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.custom.armor.OrdinaryArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OrdinaryArmorModel extends AnimatedGeoModel<OrdinaryArmorItem> {

    @Override
    public Identifier getModelResource(OrdinaryArmorItem object) {
        return new Identifier(ArcadianDream.MOD_ID, "geo/ordinary_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(OrdinaryArmorItem object) {
        return new Identifier(ArcadianDream.MOD_ID, "textures/models/armor/ordinary_armor_texture.png");
    }

    @Override
    public Identifier getAnimationResource(OrdinaryArmorItem animatable) {
        return new Identifier(ArcadianDream.MOD_ID, "animations/armor_animation.json");
    }
}
