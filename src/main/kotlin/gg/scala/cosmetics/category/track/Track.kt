package gg.scala.cosmetics.category.track

import gg.scala.cosmetics.cosmetic.Cosmetic
import gg.scala.cosmetics.cosmetic.category.CosmeticCategory
import gg.scala.cosmetics.category.TracksCategory
import net.evilblock.cubed.util.math.Chance
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

class Track(private val id: String) : Cosmetic(), Listener {

    var displayName: String = id.toLowerCase()
    var displayIcon: ItemStack? = null
    var desc: String = ""

    var radius: Int = 5
    var hidden: Boolean = false

    var blockTime: Long = 3000L
    val blockTypes: MutableList<TrackBlock> = arrayListOf()

    override fun getCategory(): CosmeticCategory
    {
        return TracksCategory
    }

    override fun getID(): String {
        return "track-$id"
    }

    fun getRawID(): String {
        return id
    }

    override fun getName(): String {
        return "$displayName Track"
    }

    override fun getIcon(): ItemStack {
        return displayIcon ?: ItemStack(Material.RAILS)
    }

    override fun getDescription(): String {
        return desc
    }

    override fun getPermission(): String {
        return "cosmetics.tracks.${ChatColor.stripColor(displayName.toLowerCase())}"
    }

    fun nextBlockType(): TrackBlock {
        if (blockTypes.isEmpty()) {
            throw IllegalStateException("Track has no block types registered")
        }

        if (!blockTypes.any { it.chance > 0.0 }) {
            throw IllegalStateException("Track has no block types with a chance more than 0")
        }

        return Chance.weightedPick(blockTypes) { it.chance }
    }

}
