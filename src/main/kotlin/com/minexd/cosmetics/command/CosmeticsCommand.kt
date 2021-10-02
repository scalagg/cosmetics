package com.minexd.cosmetics.command

import com.minexd.cosmetics.CosmeticsPlugin
import com.minexd.cosmetics.menu.CategoriesMenu
import com.minexd.cosmetics.menu.CosmeticsMenu
import com.minexd.cosmetics.profile.ProfileHandler
import net.evilblock.cubed.acf.BaseCommand
import net.evilblock.cubed.acf.CommandHelp
import net.evilblock.cubed.acf.annotation.*
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

@CommandAlias("cosmetics|cosmetic")
class CosmeticsCommand : BaseCommand() {

    @Default
    fun onCosmetics(player: Player) {
        CategoriesMenu().openMenu(player)
    }

    @Default
    @HelpCommand
    @Syntax("[help]")
    @CommandPermission("op")
    fun onHelp(help: CommandHelp) {
        help.showHelp()
    }

    @Subcommand("reload")
    @CommandPermission("op")
    @Description("Reloads the Cosmetics configuration.")
    fun execute(sender: CommandSender) {
        CosmeticsPlugin.instance.reloadConfig()
    }

    @Subcommand("list")
    @CommandPermission("op")
    @Description("Lists all cosmetics and their permissions.")
    fun onList(sender: CommandSender) {
        val allCosmetics = CosmeticsPlugin.instance.categories.map { it.getCosmetics() }.flatten()
        sender.sendMessage("${ChatColor.GOLD}${ChatColor.BOLD}Cosmetics: ${allCosmetics.joinToString { "${ChatColor.RESET}${it.getName()} ${ChatColor.GRAY}[${ChatColor.WHITE}${it.getPermission()}${ChatColor.GRAY}]" }}")
    }

    @Subcommand("menu")
    @CommandPermission("op")
    @Description("Opens the main cosmetics menu.")
    fun onCosmeticsMenu(player: Player) {
        CategoriesMenu().openMenu(player)
    }

}
