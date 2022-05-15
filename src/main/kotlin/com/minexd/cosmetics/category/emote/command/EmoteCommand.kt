package com.minexd.cosmetics.category.emote.command

import com.minexd.cosmetics.category.emote.Emote
import com.minexd.cosmetics.category.emote.menu.EmoteBoxMenu
import gg.scala.commons.acf.annotation.CommandAlias
import gg.scala.commons.acf.annotation.Description
import gg.scala.commons.annotations.commands.AutoRegister
import gg.scala.commons.command.ScalaCommand
import org.bukkit.entity.Player

@AutoRegister
object EmoteCommand : ScalaCommand()
{
    @CommandAlias("emote|emotes")
    @Description("Open the Emotes GUI.")
    fun emotesMenu(player: Player)
    {
        EmoteBoxMenu().openMenu(player)
    }

    @CommandAlias("perform")
    @Description("Open the Emotes GUI.")
    fun perform(player: Player, emote: Emote)
    {
        emote.playEffect(player.eyeLocation.clone().add(player.location.direction))
    }
}
