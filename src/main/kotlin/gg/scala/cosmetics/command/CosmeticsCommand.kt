package gg.scala.cosmetics.command

import gg.scala.cosmetics.CosmeticsPlugin
import gg.scala.cosmetics.menu.CategoriesMenu
import gg.scala.commons.acf.CommandHelp
import gg.scala.commons.acf.annotation.*
import gg.scala.commons.annotations.commands.AutoRegister
import gg.scala.commons.command.ScalaCommand
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@AutoRegister
@CommandAlias("cosmetics|cosmetic")
object CosmeticsCommand : ScalaCommand()
{
    @Default
    fun onCosmetics(player: Player)
    {
        CategoriesMenu().openMenu(player)
    }

    @Default
    @HelpCommand
    @CommandPermission("op")
    fun onHelp(help: CommandHelp)
    {
        help.showHelp()
    }

    @Subcommand("reload")
    @CommandPermission("op")
    @Description("Reloads the Cosmetics configuration.")
    fun execute(sender: CommandSender)
    {
        CosmeticsPlugin.instance.reloadConfig()
    }

    @Subcommand("list")
    @CommandPermission("op")
    @Description("Lists all cosmetics and their permissions.")
    fun onList(sender: CommandSender)
    {
        val allCosmetics = CosmeticsPlugin.instance.categories.map { it.getCosmetics() }.flatten()
        sender.sendMessage("${ChatColor.GOLD}${ChatColor.BOLD}Cosmetics: ${allCosmetics.joinToString { "${ChatColor.RESET}${it.getName()} ${ChatColor.GRAY}[${ChatColor.WHITE}${it.getPermission()}${ChatColor.GRAY}]" }}")
    }

    @Subcommand("menu")
    @CommandPermission("op")
    @Description("Opens the main cosmetics menu.")
    fun onCosmeticsMenu(player: Player)
    {
        CategoriesMenu().openMenu(player)
    }
}
