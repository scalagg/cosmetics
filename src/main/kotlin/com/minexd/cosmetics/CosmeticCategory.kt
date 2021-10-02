package com.minexd.cosmetics

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface CosmeticCategory {

    fun getIcon(): ItemStack

    fun getName(): String

    fun getPluralName(): String

    fun getDescription(): List<String>

    fun getCosmetics(): Collection<Cosmetic>

    fun getAccessibleCosmetics(player: Player): List<Cosmetic> {
        return getCosmetics()
                .filter { cosmetic -> player.hasPermission(cosmetic.getPermission()) }
                .toList()
    }

}
