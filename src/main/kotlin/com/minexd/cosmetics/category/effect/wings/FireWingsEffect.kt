package com.minexd.cosmetics.category.effect.wings

import com.minexd.cosmetics.cosmetic.category.CosmeticCategory
import com.minexd.cosmetics.category.EffectsCategory
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object FireWingsEffect : WingsEffect(Color.RED, Color.ORANGE, Color.YELLOW) {

    override fun getID(): String {
        return "fire-wings"
    }

    override fun getName(): String {
        return "Fire Wings"
    }

    override fun getCategory(): CosmeticCategory
    {
        return EffectsCategory
    }

    override fun getIcon(): ItemStack {
        return ItemBuilder.of(Material.BLAZE_POWDER).build()
    }

    override fun getPermission(): String {
        return "cosmetics.fire-wings"
    }

}
