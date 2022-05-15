package com.minexd.cosmetics.category

import com.minexd.cosmetics.cosmetic.Cosmetic
import com.minexd.cosmetics.cosmetic.category.CosmeticCategory
import com.minexd.cosmetics.category.outfit.AstronautOutfit
import com.minexd.cosmetics.category.outfit.RainbowOutfit
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object OutfitsCategory : CosmeticCategory
{
    private val cosmetics = listOf<Cosmetic>(
        RainbowOutfit(), AstronautOutfit()
    )

    override fun getIcon(): ItemStack {
        return ItemBuilder.of(Material.ARMOR_STAND).build()
    }

    override fun getName(): String {
        return "Outfits"
    }

    override fun getDescription(): List<String> {
        return listOf(
            "${CC.GRAY}Equip our exclusive",
            "${CC.GRAY}outfits!"
        )
    }

    override fun getPluralName(): String {
        return "outfits"
    }

    override fun getCosmetics(): List<Cosmetic> {
        return cosmetics
    }
}
