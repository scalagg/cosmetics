package com.minexd.cosmetics.category.emote.command

import com.minexd.cosmetics.category.emote.Emote
import com.minexd.cosmetics.category.emote.EmotesBoxCosmetic
import com.minexd.cosmetics.menu.EmoteBoxMenu
import net.evilblock.cubed.command.Command
import net.evilblock.cubed.command.data.parameter.Param
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object EmoteCommand {

    @Command(
            names = ["emote", "emotes"],
            description = "Open the Emotes GUI"
    )
    @JvmStatic
    fun emotesMenu(player: Player) {
        EmoteBoxMenu().openMenu(player)
    }

    @Command(
        names = ["perform"]
    )
    @JvmStatic
    fun perform(player: Player, @Param(name = "emote") emoteId: String) {
        var matchingEmote: Emote? = null
        for (emote in EmotesBoxCosmetic.emotes) {
            if (emote.getId().equals(emoteId, ignoreCase = true)) {
                matchingEmote = emote
                break
            }
        }

        if (matchingEmote == null) {
            player.sendMessage("${ChatColor.RED}Invalid emote!")
            return
        }

        matchingEmote.playEffect(player.eyeLocation.clone().add(player.location.direction))
    }

}