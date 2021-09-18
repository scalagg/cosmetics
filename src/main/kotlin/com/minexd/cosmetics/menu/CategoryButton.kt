package com.minexd.cosmetics.menu

import com.minexd.cosmetics.CosmeticCategory
import com.minexd.cosmetics.category.EmotesCategory
import com.minexd.cosmetics.category.TracksCategory
import com.minexd.cosmetics.category.track.menu.TracksMenu
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.math.Numbers
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class CategoryButton(private val category: CosmeticCategory) : Button() {

    override fun getName(player: Player): String {
        return "${ChatColor.YELLOW}${ChatColor.BOLD}${category.getName()}"
    }

    override fun getDescription(player: Player): List<String> {
        return arrayListOf<String>().also { lore ->
            val unlocked = category.getCosmetics().count { player.hasPermission(it.getPermission()) }
            val total = category.getCosmetics().size

            lore.add(buildString {
                append("${ChatColor.GRAY}Unlocked: ")
                append("${ChatColor.GREEN}${Numbers.format(unlocked)}")
                append("${ChatColor.GRAY}/${Numbers.format(total)}")
            })

            lore.add("")
            lore.add("${ChatColor.YELLOW}Click to browse all ${category.getPluralName()}")
        }
    }

    override fun getButtonItem(player: Player): ItemStack {
        return ItemBuilder.copyOf(category.getIcon())
                .name(getName(player))
                .setLore(getDescription(player))
                .addFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                .build()
    }

    override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
        when (category) {
            TracksCategory -> {
                TracksMenu().openMenu(player)
            }
            EmotesCategory -> {
                EmoteBoxMenu().openMenu(player)
            }
            else -> {
                CosmeticsMenu(category).openMenu(player)
            }
        }
    }

}