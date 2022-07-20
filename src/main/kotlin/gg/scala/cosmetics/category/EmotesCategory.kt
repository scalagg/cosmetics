package gg.scala.cosmetics.category

import gg.scala.cosmetics.cosmetic.Cosmetic
import gg.scala.cosmetics.cosmetic.category.CosmeticCategory
import gg.scala.cosmetics.category.emote.EmotesBoxCosmetic
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object EmotesCategory : CosmeticCategory
{

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
