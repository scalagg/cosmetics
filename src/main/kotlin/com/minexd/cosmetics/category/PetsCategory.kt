package com.minexd.cosmetics.category

import com.minexd.cosmetics.cosmetic.Cosmetic
import com.minexd.cosmetics.cosmetic.category.CosmeticCategory
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object PetsCategory : CosmeticCategory
{

    override fun getName(): String {
        return "Pets"
    }

    override fun getPluralName(): String {
        return "pets"
    }

    override fun getIcon(): ItemStack {
        return ItemBuilder.of(Material.DIAMOND_BARDING).build()
    }

    override fun getDescription(): List<String> {
        return listOf(
            "${CC.GRAY}Get yourself an awesome",
            "${CC.GRAY}pet that follows you around!"
        )
    }

    override fun getCosmetics(): Collection<Cosmetic> {
        return emptyList()
    }

}
