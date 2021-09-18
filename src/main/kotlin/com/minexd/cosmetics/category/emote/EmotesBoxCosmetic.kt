package com.minexd.cosmetics.category.emote

import com.minexd.cosmetics.Cosmetic
import com.minexd.cosmetics.CosmeticCategory
import com.minexd.cosmetics.category.EmotesCategory
import com.minexd.cosmetics.category.emote.impl.HeartEmote
import com.minexd.cosmetics.menu.EmoteBoxMenu
import net.evilblock.cubed.util.bukkit.ItemUtils
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object EmotesBoxCosmetic : Cosmetic() {

    val emotes = arrayListOf<Emote>(HeartEmote)

    override fun getCategory(): CosmeticCategory {
        return EmotesCategory
    }

    override fun getID(): String {
        return "emotes-box"
    }

    override fun getName(): String {
        return "Emotes Box"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.CHEST)
    }

    override fun getPermission(): String {
        return "cosmetics.emote-box"
    }

    override fun hasState(): Boolean {
        return false
    }

    override fun onEnable(player: Player) {

    }

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            val itemInHand = event.player.itemInHand
            if (itemInHand != null) {
                if (ItemUtils.itemTagHasKey(itemInHand, "EmoteBox")) {
                    EmoteBoxMenu(redirect = true).openMenu(event.player)
                }

                event.isCancelled = true
            }
        }
    }

}