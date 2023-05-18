package net.reimaden.arcadiandream.item.custom.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IcicleSwordItem extends SwordItem {

    public IcicleSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // We are not returning super here because we don't want to damage the item
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        // We are not returning super here because we don't want to damage the item
        return true;
    }
}
