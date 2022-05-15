package com.minexd.cosmetics.category.track.command

import com.minexd.cosmetics.category.track.menu.TrackEditorMenu
import com.minexd.cosmetics.category.track.menu.TracksMenu
import gg.scala.commons.acf.annotation.*
import gg.scala.commons.annotations.commands.AutoRegister
import gg.scala.commons.command.ScalaCommand
import org.bukkit.entity.Player

@AutoRegister
@CommandAlias("track|tracks")
object TracksCommand : ScalaCommand()
{
    @Default
    fun onDefault(player: Player)
    {
        TracksMenu().openMenu(player)
    }

    @Subcommand("editor|edit")
    @Description("Opens the tracks editor.")
    @CommandPermission("cosmetics.tracks.editor")
    fun onEditor(player: Player)
    {
        TrackEditorMenu().openMenu(player)
    }
}
