package com.minexd.cosmetics.category.emote.command

import com.minexd.cosmetics.category.emote.Emote
import com.minexd.cosmetics.category.emote.menu.EmoteBoxMenu
import net.evilblock.cubed.acf.BaseCommand
import net.evilblock.cubed.acf.annotation.CommandAlias
import net.evilblock.cubed.acf.annotation.Description
import net.evilblock.cubed.acf.annotation.Syntax
import org.bukkit.entity.Player

class EmoteCommand : BaseCommand() {

    @CommandAlias("emote|emotes")
    @Description("Open the Emotes GUI.")
    fun emotesMenu(player: Player) {
        EmoteBoxMenu().openMenu(player)
    }

    @Syntax("<emote>")
    @CommandAlias("perform")
    @Description("Open the Emotes GUI.")
    fun perform(player: Player, emote: Emote) {
        emote.playEffect(player.eyeLocation.clone().add(player.location.direction))
    }

}
