package com.minexd.cosmetics.category.effect.trail

import com.minexd.cosmetics.cosmetic.category.CosmeticCategory
import com.minexd.cosmetics.category.EffectsCategory
import net.evilblock.cubed.util.nms.MinecraftReflection
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object LavaRingsEffect : RingsEffect(MinecraftReflection.getEnumParticle("DRIP_LAVA")!!) {

    override fun getCategory(): CosmeticCategory
    {
        return EffectsCategory
    }

    override fun getID(): String {
        return "lava-rings"
    }

    override fun getName(): String {
        return "Lava Rings"
    }

    override fun getDescription(): String {
        return "Surround yourself with rings made of lava."
    }

    override fun getPermission(): String {
        return "cosmetics.lava-rings"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.LAVA_BUCKET)
    }

}
