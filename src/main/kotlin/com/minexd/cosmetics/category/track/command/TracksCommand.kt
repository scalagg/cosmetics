package com.minexd.cosmetics.category.track.command

import com.minexd.cosmetics.category.track.menu.TracksMenu
import net.evilblock.cubed.command.Command
import org.bukkit.entity.Player

object TracksCommand {

    @Command(
            names = ["tracks", "track"],
            description = "Open the Tracks GUI"
    )
    @JvmStatic
    fun execute(player: Player) {
        TracksMenu().openMenu(player)
    }

}