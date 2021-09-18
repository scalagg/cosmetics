package com.minexd.cosmetics.category.track.command

import com.minexd.cosmetics.category.track.menu.TrackEditorMenu
import com.minexd.cosmetics.util.Permissions
import net.evilblock.cubed.command.Command
import org.bukkit.entity.Player

object TracksEditorCommand {

    @Command(
        names = ["tracks editor", "track editor"],
        description = "Opens the Track Editor",
        permission = Permissions.TRACKS_EDITOR
    )
    @JvmStatic
    fun execute(player: Player) {
        TrackEditorMenu().openMenu(player)
    }

}