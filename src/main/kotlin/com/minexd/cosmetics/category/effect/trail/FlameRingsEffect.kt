package com.minexd.cosmetics.category.effect.trail

import com.minexd.cosmetics.cosmetic.category.CosmeticCategory
import com.minexd.cosmetics.category.EffectsCategory
import net.evilblock.cubed.util.nms.MinecraftReflection
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object FlameRingsEffect : RingsEffect(MinecraftReflection.getEnumParticle("FLAME")!!) {

    override fun getCategory(): CosmeticCategory
    {
        return EffectsCategory
    }

    override fun getID(): String {
        return "flame-rings"
    }

    override fun getName(): String {
        return "Flame Rings"
    }

    override fun getDescription(): String {
        return "Surround yourself with rings made of flames."
    }

    override fun getPermission(): String {
        return "cosmetics.flame-rings"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.FIREBALL)
    }

}
