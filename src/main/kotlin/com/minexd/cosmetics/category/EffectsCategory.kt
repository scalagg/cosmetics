package com.minexd.cosmetics.category

import com.minexd.cosmetics.Cosmetic
import com.minexd.cosmetics.CosmeticCategory
import com.minexd.cosmetics.category.effect.BubblesEffect
import com.minexd.cosmetics.category.effect.trail.FlameRingsEffect
import com.minexd.cosmetics.category.effect.trail.LavaRingsEffect
import com.minexd.cosmetics.category.effect.trail.SnowRingsEffect
import com.minexd.cosmetics.category.effect.trail.WaterRingsEffect
import com.minexd.cosmetics.category.effect.wings.FireWingsEffect
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object EffectsCategory : CosmeticCategory {

    override fun getName(): String {
        return "Effects"
    }

    override fun getPluralName(): String {
        return "effects"
    }

    override fun getDescription(): List<String> {
        return listOf(
            "${CC.GRAY}Equip and show off",
            "${CC.GRAY}your in-game effects!"
        )
    }

    override fun getIcon(): ItemStack {
        return ItemBuilder
            .of(Material.BLAZE_POWDER)
            .build()
    }

    override fun getCosmetics(): List<Cosmetic> {
        return listOf(
            BubblesEffect,
            LavaRingsEffect,
            FlameRingsEffect,
            WaterRingsEffect,
            SnowRingsEffect,
            FireWingsEffect
        )
    }

}
