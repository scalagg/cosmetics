package com.minexd.cosmetics.category.outfit

import com.minexd.cosmetics.CosmeticCategory
import com.minexd.cosmetics.CosmeticsPlugin
import com.minexd.cosmetics.TickableCosmetic
import com.minexd.cosmetics.category.OutfitsCategory
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.ItemUtils.colorLeatherArmor
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

class AstronautOutfit : TickableCosmetic(), Listener {

    override fun getCategory(): CosmeticCategory {
        return OutfitsCategory
    }

    override fun getID(): String {
        return "astronaut-armor"
    }

    override fun getName(): String {
        return "${CC.AQUA}Astronaut Outfit"
    }

    override fun getDescription(): String {
        return "A set of armor which makes you look like an astronaut."
    }

    override fun getIcon(): ItemStack {
        return ItemBuilder.of(Material.GLASS).data(3).build()
    }

    override fun getPermission(): String {
        return "cosmetics.astronaut-armor"
    }

    override fun canEnable(player: Player): Boolean {
        if (CosmeticsPlugin.instance.isGameServer()) {
            if (player.inventory.helmet != null
                || player.inventory.chestplate != null
                || player.inventory.leggings != null
                || player.inventory.boots != null
            ) {
                player.sendMessage("${ChatColor.RED}You must unequip all of your armor to enable this cosmetic!")
                return false
            }
        }

        return true
    }

    override fun onEnable(player: Player) {
        player.inventory.helmet =
            ItemBuilder.of(Material.GLASS)
                .name("${CC.DARK_AQUA}Astronaut Helmet")
                .data(8).build()
        player.inventory.chestplate =
            colorLeatherArmor(
                ItemBuilder.of(Material.LEATHER_CHESTPLATE)
                    .name("${CC.DARK_AQUA}Astronaut Jacket").build(),
                Color.AQUA
            )
        player.inventory.leggings =
            colorLeatherArmor(
                ItemBuilder.of(Material.LEATHER_LEGGINGS)
                    .name("${CC.DARK_AQUA}Astronaut Pants").build(),
                Color.AQUA
            )
        player.inventory.boots =
            colorLeatherArmor(
                ItemBuilder.of(Material.LEATHER_BOOTS)
                    .name("${CC.DARK_AQUA}Astronaut Boots").build(),
                Color.TEAL
            )

        player.updateInventory()
    }

    override fun onDisable(player: Player) {
        player.inventory.helmet = null
        player.inventory.chestplate = null
        player.inventory.leggings = null
        player.inventory.boots = null
        player.updateInventory()
    }

}
