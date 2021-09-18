package com.minexd.cosmetics.command

import com.minexd.cosmetics.profile.ProfileHandler
import net.evilblock.cubed.command.Command
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

object WipeCommand {

    @Command(names = ["cosmetics wipe-db"])
    @JvmStatic
    fun execute(sender: CommandSender) {
        if (sender !is CommandSender) {
            sender.sendMessage("${ChatColor.RED}You must execute this command through console!")
            return
        }

        ProfileHandler.getCollection().drop()
        sender.sendMessage("${ChatColor.GREEN}Wiped database!")
    }

}