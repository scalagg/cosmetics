package com.minexd.cosmetics.command

import com.minexd.cosmetics.CosmeticsPlugin
import com.minexd.cosmetics.profile.ProfileHandler
import net.evilblock.cubed.acf.BaseCommand
import net.evilblock.cubed.acf.CommandHelp
import net.evilblock.cubed.acf.annotation.*
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender

@CommandAlias("cosmetics")
@CommandPermission("op")
class CosmeticsCommand : BaseCommand() {

    @Default
    @HelpCommand
    @Syntax("[help]")
    fun onHelp(help: CommandHelp) {
        help.showHelp()
    }

    @Subcommand("reload")
    @Description("Reloads the Cosmetics configuration.")
    fun execute(sender: CommandSender) {
        CosmeticsPlugin.instance.reloadConfig()
    }

    @Subcommand("wipe-db")
    @Description("Wipes the cosmetics database.")
    fun onWipeDb(sender: ConsoleCommandSender) {
        ProfileHandler.getCollection().drop()
        sender.sendMessage("${ChatColor.GREEN}Wiped database!")
    }

    @Subcommand("list")
    @Description("Lists all cosmetics and their permissions.")
    fun onList(sender: CommandSender) {
        val allCosmetics = CosmeticsPlugin.instance.categories.map { it.getCosmetics() }.flatten()
        sender.sendMessage("${ChatColor.GOLD}${ChatColor.BOLD}Cosmetics: ${allCosmetics.joinToString { "${ChatColor.RESET}${it.getName()} ${ChatColor.GRAY}[${ChatColor.WHITE}${it.getPermission()}${ChatColor.GRAY}]" }}")
    }

}
