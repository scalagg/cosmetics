package com.minexd.cosmetics.category

import com.minexd.cosmetics.CosmeticCategory
import com.minexd.cosmetics.category.track.Track
import com.minexd.cosmetics.category.track.TrackHandler
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object TracksCategory : CosmeticCategory {

    override fun getName(): String {
        return "Tracks"
    }

    override fun getPluralName(): String {
        return "tracks"
    }

    override fun getDescription(): List<String> {
        return listOf()
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.RAILS)
    }

    override fun getCosmetics(): Collection<Track> {
        return TrackHandler.getTracks()
    }

}
