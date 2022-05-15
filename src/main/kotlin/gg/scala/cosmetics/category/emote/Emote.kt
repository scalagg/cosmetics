package gg.scala.cosmetics.category.emote

import org.bukkit.Location
import org.bukkit.inventory.ItemStack

interface Emote {

    fun getId(): String

    fun getDisplayName(): String

    fun getDescription(): List<String>

    fun getIcon(): ItemStack

    fun playEffect(location: Location)

}
