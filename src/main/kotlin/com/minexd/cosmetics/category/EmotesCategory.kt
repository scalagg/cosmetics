package com.minexd.cosmetics.category

import com.minexd.cosmetics.Cosmetic
import com.minexd.cosmetics.CosmeticCategory
import com.minexd.cosmetics.category.emote.EmotesBoxCosmetic
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object EmotesCategory : CosmeticCategory {

    private val cosmetics = arrayListOf<Cosmetic>(
        EmotesBoxCosmetic
    )

    override fun getName(): String {
        return "Emotes"
    }

    override fun getPluralName(): String {
        return "emotes"
    }

    override fun getDescription(): List<String> {
        return listOf(
            "${CC.GRAY}Perform emotes in front",
            "${CC.GRAY}of other players!"
        )
    }

    override fun getIcon(): ItemStack {
        return ItemBuilder.of(Material.RAW_FISH)
            .data(3)
            .build()
    }

    override fun getCosmetics(): List<Cosmetic> {
        return cosmetics
    }

}
