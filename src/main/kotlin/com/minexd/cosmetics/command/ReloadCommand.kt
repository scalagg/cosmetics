package com.minexd.cosmetics.command

import com.minexd.cosmetics.CosmeticsPlugin
import net.evilblock.cubed.command.Command
import org.bukkit.command.CommandSender

object ReloadCommand {

    @Command(
            names = ["cosmetics reload"],
            description = "Reloads the Cosmetics configuration",
            permission = "op"
    )
    @JvmStatic
    fun execute(sender: CommandSender) {
        CosmeticsPlugin.instance.reloadConfig()
    }

}