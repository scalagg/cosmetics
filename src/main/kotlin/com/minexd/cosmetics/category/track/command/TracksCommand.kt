package com.minexd.cosmetics.category.track.command

import com.minexd.cosmetics.category.track.menu.TrackEditorMenu
import com.minexd.cosmetics.category.track.menu.TracksMenu
import com.minexd.cosmetics.util.Permissions
import net.evilblock.cubed.acf.BaseCommand
import net.evilblock.cubed.acf.annotation.*
import org.bukkit.entity.Player

@CommandAlias("track|tracks")
class TracksCommand : BaseCommand() {

    @Default
    fun execute(player: Player) {
        TracksMenu().openMenu(player)
    }

    @Subcommand("editor|edit")
    @Description("Opens the tracks editor.")
    @CommandPermission(Permissions.TRACKS_EDITOR)
    fun onEditor(player: Player) {
        TrackEditorMenu().openMenu(player)
    }

}
