package gg.scala.cosmetics.category

import gg.scala.cosmetics.cosmetic.category.CosmeticCategory
import gg.scala.cosmetics.category.track.Track
import gg.scala.cosmetics.category.track.TrackHandler
import net.evilblock.cubed.util.CC
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object TracksCategory : CosmeticCategory
{

    override fun getName(): String {
        return "Tracks"
    }

    override fun getPluralName(): String {
        return "tracks"
    }

    override fun getDescription(): List<String> {
        return listOf(
            "${CC.GRAY}Change the blocks",
            "${CC.GRAY}beneath you to a",
            "${CC.GRAY}randomized set of",
            "${CC.GRAY}matching blocks!"
        )
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.RAILS)
    }

    override fun getCosmetics(): Collection<Track> {
        return TrackHandler.getTracks()
    }

}
